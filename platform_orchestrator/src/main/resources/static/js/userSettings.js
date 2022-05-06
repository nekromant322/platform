window.onload = function () {
    getCurrentUser();
};

let telegramNotification;
let vkNotification;
let currentUserLogin;
let telegramCheck;
let vkCheck;

function getCurrentUser() {

    $.ajax({
        url: '/platformUser/current',
        type: 'GET',
        contentType: 'application/json',
        cache: false,
        success: function (currentUser) {
            telegramNotification = currentUser.userSettings.telegramNotification;
            vkNotification = currentUser.userSettings.vkNotification;
            currentUserLogin = currentUser.login;
            telegramCheck = telegramNotification;
            vkCheck = vkNotification;

            $('#telegram').append((telegramNotification === false ? 'No' : 'Yes') + ' ');
            $('#vk').append((vkNotification === false ? 'No' : 'Yes') + ' ');

            if (telegramNotification === true) {
                $('#telegramCheck').prop('checked', true);
            }
            if (vkNotification === true) {
                $('#vkCheck').prop('checked', true);
            }

            var telegram = document.querySelector('#telegramCheck');
            telegram.onclick = function() {
                if (telegram.checked) {
                    telegramCheck = true;
                } else {
                    telegramCheck = false;
                }
            }
            var vk = document.querySelector('#vkCheck');
            vk.onclick = function() {
                if (vk.checked) {
                    vkCheck = true;
                } else {
                    vkCheck = false;
                }
            }

            $('#editButton').click(function () {
                saveChanges(null, telegramCheck, vkCheck, currentUserLogin);
                location.reload();
            })
        }
    })
}

function saveChanges(id, telegramCheck, vkCheck, currentUserLogin) {
    let settings = {};
    settings.id = id;
    settings.telegramNotification = telegramCheck;
    settings.vkNotification = vkCheck;
    $.ajax({
        url: '/userSettings/' + currentUserLogin,
        dataType: 'json',
        method: 'PATCH',
        cache: false,
        contentType: 'application/json',
        data: JSON.stringify(settings),
        success: function (data) {
            console.log(data)
        }
    })
}