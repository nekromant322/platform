let currentUserLogin;

window.onload = function () {
    getCurrentUser();
};

function storeToken() {

    let yooMoneyRequestInfoDTO = {}

    yooMoneyRequestInfoDTO.amount = sessionStorage.getItem("amount")
    yooMoneyRequestInfoDTO.comment = sessionStorage.getItem("comment")
    yooMoneyRequestInfoDTO.login = currentUserLogin;

    sessionStorage.getItem("token")

    $.ajax({
        method: 'POST',
        url: "/yooMoney/authorize",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(yooMoneyRequestInfoDTO),
        success: function (result) {
            sessionStorage.setItem("token", result.confirmation.confirmation_token);
            sendPayRequest(result.amount.value, result.recipient.account_id, result.created_at, result.description, result.status, result.id)
            window.location.replace('/yooMoneyPayment');
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function getToken() {
    return sessionStorage.getItem("token")
}

function getCurrentUser() {
    $.ajax({
        url: '/platformUsers/current',
        type: 'GET',
        contentType: 'application/json',
        cache: false,
        success: function (currentUser) {
            currentUserLogin = currentUser.login;
        }
    })
    return currentUserLogin;
}

function sendPayRequest(amount, accountId, date, comment, status, paymentId) {
    let paymentReport = {};
    paymentReport.sum = amount
    paymentReport.accountNumber = accountId
    paymentReport.date = date
    paymentReport.comment = comment
    paymentReport.status = status
    paymentReport.paymentId = paymentId

    $.ajax({
        method: 'POST',
        url: "/payment",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(paymentReport),
        success: function (result) {
        },
        error: function (error) {
            console.log(error);
        }
    });
}
