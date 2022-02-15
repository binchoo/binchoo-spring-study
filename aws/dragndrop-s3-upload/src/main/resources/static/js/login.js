var login_page = {
    init: function() {
        this.setup_csrf()
    },
    setup_csrf: function() {
        $('#_csrf').val($.cookie('XSRF-TOKEN'))
    },
}

login_page.init()