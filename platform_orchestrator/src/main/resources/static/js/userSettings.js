window.onload = function () {
    getCurrentUser();
};

let telegramNotification;
let vkNotification;

function getCurrentUser() {

    $.ajax({
        url: 'userSettings/current',
        type: 'GET',
        contentType: 'application/json',
        cache: false,
        success: function (currentUser) {
            telegramNotification = currentUser.userSettings.telegramNotification;
            vkNotification = currentUser.userSettings.vkNotification;

            $('#settingsBox').append(
                '<div id="settings">' +
                '<h5>Уведомления:</h5>' +
                '<h7>Telegram: ' + (telegramNotification === false ? 'No' : 'Yes') +
                '</h7><br>' +
                '<h7>VK: ' + (vkNotification === false ? 'No' : 'Yes') +
                '</h7><br>' +
                '<br>' +
                '<button type="button" id="editButton" ' +
                'class="btn btn-primary">Изменить</button></div>' +
                '<br>' +
                '<form id="form">' +
                '<div class="form-group">' +
                '<input class="form-check-input" id="telegram" type="checkbox" checked>' +
                '<label class="form-check-label" for="telegram"> Telegram</label>' +
                '<script>\n' +
                'var telegram = document.querySelector(\'#telegram\');\n' +
                'telegramNotification = true;\n' +
                'telegram.onclick = function() {\n' +
                ' if (telegram.checked) {\n' +
                '  telegramNotification = true;\n' +
                ' } else {\n' +
                '  telegramNotification = false;\n' +
                ' }\n' +
                '}\n' +
                '</script>' +
                '<br>' +
                '<input class="form-check-input" id="vk" type="checkbox" checked>' +
                '<label class="form-check-label" for="vk"> VK</label>' +
                '<script>\n' +
                'var vk = document.querySelector(\'#vk\');\n' +
                'vkNotification = true;\n' +
                'vk.onclick = function() {\n' +
                ' if (vk.checked) {\n' +
                '  vkNotification = true;\n' +
                ' } else {\n' +
                '  vkNotification = false;\n' +
                ' }\n' +
                '}\n' +
                '</script>' +
                '<br>' +
                '<br>' +
                '<button type="submit" id="addButton" ' +
                'class="btn btn-success">Сохранить' +
                '</button>' +
                '</div>' +
                '</form>')

            $('#form').hide()

            $('#editButton').click(function () {
                $('#info').hide()
                $('#form').show()
            })
            $('#form').submit(function () {
                save(null, telegramNotification, vkNotification,
                    currentUser.login)
            })
        }
    })
}

function save(id, telegramNotification, vkNotification, login) {
    let settings = {};
    settings.id = id;
    settings.telegramNotification = telegramNotification;
    settings.vkNotification = vkNotification;
    $.ajax({
        url: '/userSettings/' + login,
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