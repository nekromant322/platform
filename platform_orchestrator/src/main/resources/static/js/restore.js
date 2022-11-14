$(function (){

    $('#code-form').submit(function (e){
        e.preventDefault();
        var login = $('#login-code').val()
        $.ajax({
            url : '/getCode/'+login,
            type : 'GET',
            async : false
        })
        $('#change-0').val(login)
    })
    $('#change-password').submit(function (e){
        e.preventDefault();

        let passwordForm = {};
        passwordForm.username = $('#change-0').val();
        passwordForm.password = $('#change-1').val()
        passwordForm.passwordRepeated = $('#change-2').val()
        passwordForm.code = $('#change-3').val()
        $.ajax({
            url: '/restore',
            type: 'POST',
            async: false,
            contentType: "application/json; charset=utf-8",
            data : JSON.stringify(passwordForm)
        })

        window.location.replace('/login')

    })

    $('#code-bph-form').submit(function (e){
        e.preventDefault();
        let phone = $('#phone-code').val();
        $.ajax({
            url: '/notification/phone?phone=' + phone,
            dataType: 'json',
            method: 'PATCH',
            contentType: 'application/json'
        });
        $('#change-bph-0').val(phone)
    })

    $('#change-byPhone').submit(function (e){
        e.preventDefault();
        let passwordFormByPhone = {};
        passwordFormByPhone.username = $('#change-bph-0').val();
        passwordFormByPhone.password = $('#change-bph-1').val()
        passwordFormByPhone.passwordRepeated = $('#change-bph-2').val()
        passwordFormByPhone.code = $('#change-bph-3').val()
        $.ajax({
            url: '/codePhone-password',
            type: 'POST',
            async: false,
            contentType: "application/json; charset=utf-8",
            data : JSON.stringify(passwordFormByPhone)
        });
        window.location.replace('/login')
    })


})

function sendTelegramMessage(){
window.location.replace('/restore');
}
