var stomp;
jQuery(function ($) {
    function disable(...ids) {
        ids.forEach((id)=> $(id).attr('disabled', true));
    }

    function enable(...ids) {
        ids.forEach((id)=> $(id).attr('disabled', false));
    }

    function writeMessage(frame) {
        message = frame.body;
        $('#messageOutput').append(message + '\n');
    }

    function writeLog(message) {
        $('#messageOutput').append('●' + message + '\n');
    }

    connCallback = () => {
        stomp.subscribe('/chatroom/' + $('#target').val(), writeMessage);
        writeLog('stomp에 연결하고 채팅방을 구독했어요.');
        disable('#target', '#connect');
    };

    errCallback = () => {
        console.error(error.headers.message);
    }

    $('#connect').click(function doConnect() {
            stomp = Stomp.over(new SockJS("/ws"));
            stomp.connect("guest", "guest", connCallback, errCallback);
            enable('#disconnect');
    });

    $('#disconnect').click(function() {
            if (typeof stomp != 'undefined') {
                stomp.disconnect(() => {
                    writeLog('stomp 연결을 해제했습니다.');
                    enable('#target', '#connect');
                    disable('#disconnect');
                });
                stomp = undefined;
            } else {
                writeLog('연결이 없는 상태입니다.');
            }
    });

    $('#send').click(function () {
            if (typeof stomp != 'undefined') {
                target = $('#target').val();
                stomp.send("/inbox/" + target, {}, $('#message').val());
                $('#message').val('')
                $('#message').focus();
            } else {
                alert('연결되지 않았어요.');
            }
    });
});
