$(function () {

    $('#getCodeByTelegram').click(function (e) {
        e.preventDefault();

        const login = $('#login-code').val();
        const type = {"type" : "telegram"}

        $.ajax({
            url: '/restore/getCode/' + login,
            type: 'GET',
            async: false,
            data: type
        })

        $('#change-password').removeClass("invisible")
        $('#change-0').val(login)
    })

    $('#getCodeBySMS').click(function (e) {
        e.preventDefault();

        const login = $('#login-code').val();
        const type = {"type" : "sms"}

        $.ajax({
            url: '/restore/getCode/' + login,
            type: 'GET',
            async: false,
            data: type
        })

        $('#change-password').removeClass("invisible")
        $('#change-0').val(login)
    })

    $('#change-password').submit(function (e) {
        e.preventDefault();

        let passwordForm = {};
        passwordForm.username = $('#change-0').val();
        passwordForm.password = $('#change-1').val()
        passwordForm.code = $('#change-3').val()

        let pass = $('#change-1').val()
        let passRepeated = $('#change-2').val()

        if (pass === passRepeated) {
            $.ajax({
                url: '/restore/changePassword',
                type: 'POST',
                async: false,
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(passwordForm)
            })

            window.location.replace('/login')
        } else {
            alert('Пароли не совпадают')
        }
    })

})

function sendTelegramMessage() {
    window.location.replace('/restore');
}
