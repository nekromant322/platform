const MILLIS_PER_DAY = 1000 * 60 * 60 * 24

function checkAlert(itemId, alertId, errorMessage, extraErrorCondition) {
    let item = document.getElementById(itemId);
    try {
        document.getElementById(alertId).remove();
    } catch (e) {
    }
    item.classList.remove('invalid');
    const hasExtraCondition = !!extraErrorCondition;
    if (item.value === undefined || item.value === "" || (hasExtraCondition && extraErrorCondition)) {
        let alert = document.createElement("p");
        alert.innerText = errorMessage ? errorMessage : "Пожалуйста заполните поле!";
        alert.className = "alert_logo";
        alert.role = "alert";
        alert.id = alertId
        item.insertAdjacentElement('beforebegin', alert);
        return true;
    }
    return false;
}

function getCurrentUser() {
    let login;
    $.ajax({
        url: '/platformUser/current',
        type: 'GET',
        contentType: 'application/json',
        async: false,
        success: function (currentUser) {
            login = currentUser.login;
        }
    })
    return login;
}

//на будущее
function formatDate(date) {
    var dd = date.getDate();
    if (dd < 10) dd = '0' + dd;

    var mm = date.getMonth() + 1;
    if (mm < 10) mm = '0' + mm;

    var yy = date.getFullYear();
    if (yy < 10) yy = '0' + yy;

    return dd + '-' + mm + '-' + yy;
}

function getCookie(name) {
    let matches = document.cookie.match(new RegExp(
        "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
    ));
    return matches ? decodeURIComponent(matches[1]) : undefined;
}

function setCookie(name, value, options = {}) {

    options = {
        path: '/',
        // при необходимости добавьте другие значения по умолчанию
        ...options
    };

    if (options.expires instanceof Date) {
        options.expires = options.expires.toUTCString();
    }

    let updatedCookie = encodeURIComponent(name) + "=" + encodeURIComponent(value);

    for (let optionKey in options) {
        updatedCookie += "; " + optionKey;
        let optionValue = options[optionKey];
        if (optionValue !== true) {
            updatedCookie += "=" + optionValue;
        }
    }
    document.cookie = updatedCookie;
}

function deleteCookie(name) {
    setCookie(name, "", {
        'max-age': -1
    })
}
