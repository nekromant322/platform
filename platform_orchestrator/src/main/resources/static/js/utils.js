const MILLIS_PER_DAY = 1000 * 60 * 60 * 24

function checkAlert(itemId, alertId, errorMessage, extraCondition) {
    let item = document.getElementById(itemId);
    try {
        document.getElementById(alertId).remove();
    } catch (e) {
    }
    item.classList.remove('invalid');
    const hasExtraCondition = !!extraCondition;
    if (item.value === undefined || item.value === "" || (hasExtraCondition && extraCondition)) {
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

