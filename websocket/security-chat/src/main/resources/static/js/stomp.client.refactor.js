var stomp = undefined

var display = {
    disable: function(...ids) {
        ids.forEach((id)=> $(id).attr('disabled', true));
    },
    enable: function(...ids) {
        ids.forEach((id)=> $(id).attr('disabled', false));
    },
    show_frame: function(frame) {
        message = frame.body;
        $('#messageOutput').append(message + '\n');
    },
    log: function(message) {
        $('#messageOutput').append('●' + message + '\n');
    }
}

var app = {
    init: function() {
        var _this = this
        $('#logout').click(_this.logout)
        $('#connect').click(_this.connect)
        $('#disconnect').click(_this.disconnect)
        $('#send').click(_this.send)
        _this.config_csrf()
    },
    config_csrf: function() {
        $.ajax('/csrf', {
            type: 'GET',
            contentType: 'application/json',
            dataType: 'json',
            success: function(data) {
                $.ajaxSetup({
                    beforeSend: function(xhr) {
                        xhr.setRequestHeader("X-CSRF-TOKEN", data.token);
                    }
                })
                $('#_csrf').val(data.token)
            }
        })
    },
    logout: function() {
        $.ajax('/logout', {
            type: 'POST',
            complete: function(xhr) {
                if (200 == xhr.status) {
                    window.location.href = "/"
                }
            }
        })
    },
    connect: function() {
        stomp = Stomp.over(new SockJS("/ws"))
        stomp.connect("guest", "guest", () => {
            stomp.subscribe('/chatroom/' + $('#target').val(), display.show_frame)
            display.log('stomp에 연결하고 채팅방을 구독했어요.')
            display.enable('#disconnect', '#message')
            display.disable('#target', '#connect')
        }, (error) => {
            if (typeof error != undefined)
                display.log(error.headers.message)
            display.enable('#target', '#connect')
            display.disable('#disconnect')
            stomp = undefined
        })
    },
    disconnect: function() {
        if (typeof stomp != 'undefined') {
            stomp.disconnect(() => {
                display.log('stomp 연결을 해제했습니다.');
                display.enable('#target', '#connect');
                display.disable('#disconnect', '#message');
            });
            stomp = undefined;
        } else {
            display.log('연결이 없는 상태입니다.');
        }
    },
    send: function() {
        if (typeof stomp != 'undefined') {
            target = $('#target').val();
            stomp.send("/inbox/" + target, {}, $('#message').val());
            $('#message').val('')
            $('#message').focus();
        } else {
            alert('연결되지 않았어요.');
        }
    }
}

app.init()