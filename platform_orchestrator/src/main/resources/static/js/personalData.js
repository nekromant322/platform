window.onload = function () {
    getCurrentUser();
};

var actNumber
var contractNumber
var date
var fullName
var passportSeries
var passportNumber
var passportIssued
var issueDate
var birthDate
var registration
var email
var phoneNumber

function getCurrentUser() {

    var empty = '';
    $.ajax({
        url: 'personalData/current',
        type: 'GET',
        contentType: 'application/json',
        cache: false,
        success: function (currentUser) {
            actNumber = currentUser.personalData.actNumber
            contractNumber = currentUser.personalData.contractNumber
            date = currentUser.personalData.date
            fullName = currentUser.personalData.fullName
            passportSeries = currentUser.personalData.passportSeries
            passportNumber = currentUser.personalData.passportNumber
            passportIssued = currentUser.personalData.passportIssued
            issueDate = currentUser.personalData.issueDate
            birthDate = currentUser.personalData.birthDate
            registration = currentUser.personalData.registration
            email = currentUser.personalData.email
            phoneNumber = currentUser.personalData.phoneNumber

            $('#lk').append(currentUser.login)
            $('#cardBody').append(
                '<div id="info">' +
                '<h5>Номер акта : ' + (actNumber == null ? empty : actNumber) +
                '</h5><br>' +
                '<h5>Номер контракта : ' + (contractNumber == null ? empty : contractNumber) +
                '</h5><br>' +
                '<h5>Дата : ' + (date == null ? empty : date) +
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
                '<form id="form">' +
                '<div class="form-group">' +
                '<h5>Номер акта</h5>' +
                '<input class="form-control input-number" id="actNumber" ' +
                'placeholder="actNumber" maxlength="255" ' +
                'value="' + (actNumber == null ? empty : actNumber) + '">' +
                '<br>' +
                '<h5>Номер контракта</h5>' +
                '<input class="form-control" id="contractNumber" type="text" ' +
                'placeholder="contractNumber" maxlength="255" ' +
                'value="' + (contractNumber == null ? empty : contractNumber) + '">' +
                '<br>' +
                '<h5>Дата</h5>' +
                '<input class="form-control" id="date" type="date" min="1950-01-01" max="2030-01-01" ' +
                'placeholder="date" ' +
                'value="' + (date == null ? empty : date) + '">' +
                '<br>' +
                '<h5>ФИО</h5>' +
                '<input class="form-control" id="fullName" ' +
                'placeholder="fullName" maxlength="255" ' +
                'value="' + (fullName == null ? empty : fullName) + '">' +
                '<br>' +
                '<h5>Серия паспорта</h5>' +
                '<input class="form-control input-number" id="passportSeries" ' +
                'placeholder="passportSeries" maxlength="4" ' +
                'value="' + (passportSeries == null ? empty : passportSeries) + '">' +
                '<br>' +
                '<h5>Номер паспорта</h5>' +
                '<input class="form-control input-number" id="passportNumber" ' +
                'placeholder="passportNumber" maxlength="6" ' +
                'value="' + (passportNumber == null ? empty : passportNumber) + '">' +
                '<br>' +
                '<h5>Выдан</h5>' +
                '<input class="form-control" id="passportIssued" ' +
                'placeholder="passportIssued" maxlength="255" ' +
                'value="' + (passportIssued == null ? empty : passportIssued) + '">' +
                '<br>' +
                '<h5>Годен</h5>' +
                '<input class="form-control" id="issueDate" type="date" min="1950-01-01" max="2030-01-01" ' +
                'placeholder="issueDate" ' +
                'value="' + (issueDate == null ? empty : issueDate) + '">' +
                '<br>' +
                '<h5>Дата рождения</h5>' +
                '<input class="form-control" id="birthDate" type="date" min="1950-01-01" max="2030-01-01" ' +
                'placeholder="birthDate" ' +
                'value="' + (birthDate == null ? empty : birthDate) + '">' +
                '<br>' +
                '<h5>Регистрация</h5>' +
                '<input class="form-control" id="registration" ' +
                'placeholder="registration" maxlength="255" ' +
                'value="' + (registration == null ? empty : registration) + '">' +
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
                save(null, $('#actNumber').val(), $('#contractNumber').val(),
                    $('#date').val(), $('#fullName').val(),
                    $('#passportSeries').val(), $('#passportNumber').val(),
                    $('#passportIssued').val(), $('#issueDate').val(),
                    $('#birthDate').val(), $('#registration').val(),
                    $('#email').val(), $('#phoneNumber').val(),
                    currentUser.login)
                // getCurrentUser()
            })
        }
    })
}

function save(id, actNumber, contractNumber, date, fullName, passportSeries, passportNumber, passportIssued,
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
            date: date,
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
            console.log(data)
        }
    })
}

$('body').on('input', '.input-number', function () {
    this.value = this.value.replace(/[^0-9]/g, '');
});
