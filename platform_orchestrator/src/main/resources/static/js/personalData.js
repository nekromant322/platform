window.onload = function () {
    getCurrentUser();
    getUserPersonalData();
    getUserDoc();
};

var actNumber;
var contractNumber;
var contractDate;
var fullName;
var passportSeries;
var passportNumber;
var passportIssued;
var issueDate;
var birthDate;
var registration;
var email;
var phoneNumber;
let telegramNotification;
let vkNotification;
let currentUserLogin;
let telegramCheck;
let vkCheck;

function getUserPersonalData() {
    var empty = '';
    $.ajax({
        url: '/platformUsers/current',
        type: 'GET',
        contentType: 'application/json',
        cache: false,
        success: function (currentUser) {
            actNumber = currentUser.personalData.actNumber;
            contractNumber = currentUser.personalData.contractNumber;
            contractDate = currentUser.personalData.contractDate;
            fullName = currentUser.personalData.fullName;
            passportSeries = currentUser.personalData.passportSeries;
            passportNumber = currentUser.personalData.passportNumber;
            passportIssued = currentUser.personalData.passportIssued;
            issueDate = currentUser.personalData.issueDate;
            birthDate = currentUser.personalData.birthDate;
            registration = currentUser.personalData.registration;
            email = currentUser.personalData.email;
            phoneNumber = currentUser.personalData.phoneNumber;

            $('#lk').append(currentUser.login);

            $('#cardBody').append(
                '<div id="infoForm">' +
                '<h5>Номер акта : ' + (actNumber == null ? empty : actNumber) +
                '</h5><br>' +
                '<h5>Номер контракта : ' + (contractNumber == null ? empty : contractNumber) +
                '</h5><br>' +
                '<h5>Дата : ' + (contractDate == null ? empty : contractDate) +
                '</h5><br>' +
                '<h5>ФИО : ' + (fullName == null ? empty : fullName) +
                '</h5><br>' +
                '<h5>Серия паспорта : ' + (passportSeries == null ? empty : passportSeries) +
                '</h5><br>' +
                '<h5>Номер паспорта : ' + (passportNumber == null ? empty : passportNumber) +
                '</h5><br>' +
                '<h5>Выдан : ' + (passportIssued == null ? empty : passportIssued) +
                '</h5><br>' +
                '<h5>Годен : ' + (issueDate == null ? empty : issueDate) +
                '</h5><br>' +
                '<h5>Дата рождения : ' + (birthDate == null ? empty : birthDate) +
                '</h5><br>' +
                '<h5>Регистрация : ' + (registration == null ? empty : registration) +
                '</h5><br>' +
                '<h5>Email : ' + (email == null ? empty : email) +
                '</h5><br>' +
                '<h5>Номер телефона : ' + (phoneNumber == null ? empty : phoneNumber) +
                '</h5><br>' +
                '<button type="button" id="editButton" ' +
                'class="btn btn-primary">Изменить</button></div>' +
                '<form id="editForm">' +
                '<div class="form-group">' +
                '<h5>Номер акта</h5>' +
                '<input class="form-control input-number" id="actNumber" ' +
                'placeholder="actNumber" maxlength="255" ' +
                'value="' + (actNumber == null ? empty : actNumber) + '" ' + 
                (actNumber != null ? 'disabled' : '') + '>' +
                '<br>' +
                '<h5>Номер контракта</h5>' +
                '<input class="form-control" id="contractNumber" type="text" ' +
                'placeholder="contractNumber" maxlength="255" ' +
                'value="' + (contractNumber == null ? empty : contractNumber) + '" ' +
                (contractNumber != null ? 'disabled' : '') + '>' +
                '<br>' +
                '<h5>Дата</h5>' +
                '<input class="form-control" id="contractDate" type="text" ' +
                'placeholder="contractDate" ' +
                'value="' + (contractDate == null ? empty : contractDate) + '" ' +
                (contractDate != null ? 'disabled' : '') + '>' +
                '<br>' +
                '<h5>ФИО</h5>' +
                '<input class="form-control" id="fullName" ' +
                'placeholder="fullName" maxlength="255" ' +
                'value="' + (fullName == null ? empty : fullName) + '" ' +
                (fullName != null ? 'disabled' : '') + '>' +
                '<br>' +
                '<h5>Серия паспорта</h5>' +
                '<input class="form-control input-number" id="passportSeries" ' +
                'placeholder="passportSeries" maxlength="4" ' +
                'value="' + (passportSeries == null ? empty : passportSeries) + '" ' +
                (passportSeries != null ? 'disabled' : '') + '>' +
                '<br>' +
                '<h5>Номер паспорта</h5>' +
                '<input class="form-control input-number" id="passportNumber" ' +
                'placeholder="passportNumber" maxlength="6" ' +
                'value="' + (passportSeries == null ? empty : passportNumber) + '" ' +
                (passportSeries != null ? 'disabled' : '') + '>' +
                '<br>' +
                '<h5>Выдан</h5>' +
                '<input class="form-control" id="passportIssued" ' +
                'placeholder="passportIssued" maxlength="255" ' +
                'value="' + (passportIssued == null ? empty : passportIssued) + '" ' +
                (passportIssued != null ? 'disabled' : '') + '>' +
                '<br>' +
                '<h5>Годен</h5>' +
                '<input class="form-control" id="issueDate" type="text" ' +
                'placeholder="issueDate" ' +
                'value="' + (issueDate == null ? empty : issueDate) + '" ' +
                (issueDate != null ? 'disabled' : '') + '>' +
                '<br>' +
                '<h5>Дата рождения</h5>' +
                '<input class="form-control" id="birthDate" type="text" ' +
                'placeholder="birthDate" ' +
                'value="' + (birthDate == null ? empty : birthDate) + '" ' +
                (birthDate != null ? 'disabled' : '') + '>' +
                '<br>' +
                '<h5>Регистрация</h5>' +
                '<input class="form-control" id="registration" ' +
                'placeholder="registration" maxlength="255" ' +
                'value="' + (registration == null ? empty : registration) + '" ' +
                (registration != null ? 'disabled' : '') + '>' +
                '<br>' +
                '<h5>Email</h5>' +
                '<input class="form-control" id="email" type="email" ' +
                'placeholder="email" maxlength="255" ' +
                'value="' + (email == null ? empty : email) + '">' +
                '<br>' +
                '<h5>Номер телефона</h5>' +
                '<input class="form-control input-number" id="phoneNumber" ' +
                'placeholder="phoneNumber" maxlength="11" ' +
                'value="' + (phoneNumber == null ? empty : phoneNumber) + '">' +
                '<br>' +
                '</div>' +
                '  <input class="form-check-input"  type="checkbox" value="" id="flexCheckDefault" required>\n' +
                '    <label>\n' +
                '        <a  href="#" class="link-primary" data-bs-toggle="modal" data-bs-target="#consentFormModal">Согласие на обработку персональных данных</a>\n' +
                '\n' +
                '    </label>\n' +
                '    <br>\n' +
                '    <div class="modal fade " id="consentFormModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">' +
                '        <div class="modal-dialog modal-xl">' +
                '            <div class="modal-content">' +
                '                <div class="modal-header">' +
                '                    <h5 class="modal-title" id="exampleModalLabel">Согласие на обработку персональных данных</h5>' +
                '                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>' +
                '                </div>' +
                '                <div class="modal-body">' +
                '                  <span>' +
                '                    \n' +
                '\n' +
                'Предоставляя свои персональные данные Пользователь даёт согласие на обработку, хранение и использование' +
                ' своих персональных данных на основании ФЗ № 152-ФЗ «О персональных данных» от 27.07.2006 г. в ' +
                'следующих целях:\n' +
                '</span>' +
                '\n' +
                '<ul>\n' +
                '  <li>Осуществление клиентской поддержки</li>\n' +
                '  <li>Получения Пользователем информации о маркетинговых событиях</li>\n' +
                '  <li>Проведения аудита и прочих внутренних исследований с целью повышения качества предоставляемых услуг.</li>\n' +
                '</ul>' +

                '<span>' +
                'Под персональными данными подразумевается любая информация личного характера, позволяющая установить' +
                ' личность Пользователя/Покупателя такая как:\n' +
                '</span>' +
                '<ul>\n' +
                '  <li>Фамилия, Имя, Отчество\n</li>\n' +
                '  <li>Дата рождения\n</li>\n' +
                '  <li>Контактный телефон\n</li>\n' +
                '  <li>Адрес электронной почты\n</li>\n' +
                '  <li> Почтовый адрес\n\n</li>\n' +
                '</ul>' +
                '<span>' +
                'Персональные данные Пользователей хранятся исключительно на электронных носителях и обрабатываются' +
                ' с использованием автоматизированных систем, за исключением случаев, когда неавтоматизированная' +
                ' обработка персональных данных необходима в связи с исполнением требований законодательства.\n' +
                '\n' +
                '</span>' +
                '<span>' +
                'Компания обязуется не передавать полученные персональные данные третьим лицам, за исключением' +
                ' следующих случаев:\n' +
                '</span>' +
                '\n' +
                '    По запросам уполномоченных органов государственной власти РФ только по основаниям и в порядке,' +
                ' установленным законодательством РФ\n' +
                '<ul>\n' +
                '  <li> Стратегическим партнерам, которые работают с Компанией для предоставления продуктов и услуг, или' +
                ' тем из них, которые помогают Компании реализовывать продукты и услуги потребителям. Мы предоставляем' +
                ' третьим лицам минимальный объем персональных данных, необходимый только для оказания требуемой услуги' +
                ' или проведения необходимой транзакции.\n</li>\n' +
                '  <li>Компания оставляет за собой право вносить изменения в одностороннем порядке в настоящие правила,' +
                ' при условии, что изменения не противоречат действующему законодательству РФ. Изменения условий ' +
                'настоящих правил вступают в силу после их публикации на Сайте.</li>\n' +
                '</ul>' +

                '                </span>' +
                '                </div>' +
                '                <div class="modal-footer">' +
                '                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>' +
                '                </div>' +
                '            </div>' +
                '        </div>' +
                '    </div>' +
                '<button type="submit" id="addButton" ' +
                'class="btn btn-success">Сохранить' +
                '</button>' +
                '</div>' +
                '</form>');

            $('#editForm').hide();

            $('#editButton').click(function () {
                $('#infoForm').hide();
                $('#editForm').show();
            });

            $('#editForm').submit(function () {
                save(null, $('#actNumber').val(), $('#contractNumber').val(),
                    $('#contractDate').val(), $('#fullName').val(),
                    $('#passportSeries').val(), $('#passportNumber').val(),
                    $('#passportIssued').val(), $('#issueDate').val(),
                    $('#birthDate').val(), $('#registration').val(),
                    $('#email').val(), $('#phoneNumber').val(),
                    currentUser.login);
                // getCurrentUser()
            });
        }
    });
}

function save(id, actNumber, contractNumber, contractDate, fullName, passportSeries, passportNumber, passportIssued,
              issueDate, birthDate, registration, email, phoneNumber, login) {
    $.ajax({
        url: 'personalData/' + login,
        dataType: 'json',
        method: 'PATCH',
        cache: false,
        contentType: 'application/json',
        data: JSON.stringify({
            id: id,
            actNumber: actNumber,
            contractNumber: contractNumber,
            contractDate: contractDate,
            fullName: fullName,
            passportSeries: passportSeries,
            passportNumber: passportNumber,
            passportIssued: passportIssued,
            issueDate: issueDate,
            birthDate: birthDate,
            registration: registration,
            email: email,
            phoneNumber: phoneNumber,
        }),
        success: function (data) {
            console.log(data);
        }
    });
}

$('body').on('input', '.input-number', function () {
    this.value = this.value.replace(/[^0-9]/g, '');
});

function getUserDoc() {
    $.ajax({
        method: 'GET',
        url: '/document/currentUser',
        contentType: 'application/json',
        success: function (response) {
            console.log(response);
            drawColumns(response);
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function drawColumns(data) {
    while (document.getElementById("userDoc").getElementsByTagName("tbody")[0].rows[0])
        document.getElementById("userDoc").getElementsByTagName("tbody")[0].deleteRow(0);
    for (let i = 0; i < data.length; i++) {
        addColumn(data[i]);
    }
}

function addColumn(data) {
    let table = document.getElementById("userDoc").getElementsByTagName("tbody")[0];
    let tr = table.insertRow(table.rows.length);
    let td;

    insertTd(data.name, tr);
    insertTd(data.type, tr);

    let downloadBtn = document.createElement("button");
    downloadBtn.className = "download btn-success";
    downloadBtn.innerHTML = "Download";
    downloadBtn.type = "submit";
    downloadBtn.addEventListener("click", () => {
        downloadFile(data.id, data.name);
    });
    td = tr.insertCell(2);
    td.insertAdjacentElement("beforeend", downloadBtn);
}

function insertTd(value, parent) {
    let element = document.createElement("td");
    element.scope = "row";
    element.innerText = value;
    parent.insertAdjacentElement("beforeend", element);
}

function downloadFile(id, name) {
    $.ajax({
        url: '/document/download/' + id,
        dataType: 'binary',
        xhrFields: {
            'responseType': 'blob'
        },
        success: function (data, status, xhr) {
            var blob = new Blob([data], {type: xhr.getResponseHeader('Content-Type')});
            var link = document.createElement('a');
            link.href = window.URL.createObjectURL(blob);
            link.download = '' + name;
            link.click();
        }
    });
}


function getCurrentUser() {
    $.ajax({
        url: '/platformUsers/current',
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
            telegram.onclick = function () {
                if (telegram.checked) {
                    telegramCheck = true;
                } else {
                    telegramCheck = false;
                }
            }
            var vk = document.querySelector('#vkCheck');
            vk.onclick = function () {
                if (vk.checked) {
                    vkCheck = true;
                } else {
                    vkCheck = false;
                }
            }

            $('#editButton').click(function () {
                saveChanges(null, telegramCheck, vkCheck, currentUserLogin);
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
            getUserChanges();
        },
        error: function (data) {
            console.log(data)
            getUserChanges();
        }
    })
}

function getUserChanges() {
    $.ajax({
        url: '/platformUsers/current',
        type: 'GET',
        contentType: 'application/json',
        cache: false,
        success: function (currentUser) {
            telegramNotification = currentUser.userSettings.telegramNotification;
            vkNotification = currentUser.userSettings.vkNotification;
            telegramCheck = telegramNotification;
            vkCheck = vkNotification;
            $('#telegram').empty();
            $('#vk').empty();
            $('#telegram').append((telegramNotification === false ? 'Telegram: No' : 'Telegram: Yes') + ' ');
            $('#vk').append((vkNotification === false ? 'VK: No' : 'VK: Yes') + ' ');

            if (telegramNotification === false) {
                $('#telegramCheck').prop('checked', false);
            } else {
                $('#telegramCheck').prop('checked', true);
            }
            if (vkNotification === false) {
                $('#vkCheck').prop('checked', false);
            } else {
                $('#vkCheck').prop('checked', true);
            }
        }
    })
}
