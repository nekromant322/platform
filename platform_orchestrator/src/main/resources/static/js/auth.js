function sendLogin() {
    let request = {};
    request.login = $("#login").val();
    request.password = $("#password").val();
    if (checkAlert("login", "login-alert")) {
        return;
    }
    if (checkAlert("password", "password-alert")) {
        return;
    }


    $.ajax({
        method: 'POST',
        url: "/login",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(request),
        success: function (result) {
            setCookie("token", result);
            window.location = "/"
        },
        error: function (error) {

        }
    });
}