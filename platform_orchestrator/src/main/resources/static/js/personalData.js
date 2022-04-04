window.onload = function () {
    getCurrentUser();
};

function getCurrentUser() {
    $.ajax({
        url: 'personalData/current',
        type: 'GET',
        contentType: 'application/json',
        success: function (currentUser) {
            $('#lk').append(currentUser.login)
            $('#cardBody').append('<form id="form">' +
                '<div class="form-group">' +
                '<h5>Номер акта</h5>' +
                '<input class="form-control" id="actNumber" type="number" ' +
                'placeholder="actNumber" maxlength="255" value="' + currentUser.personalData.actNumber + '">' +
                '<br>' +
                '<h5>Номер контракта</h5>' +
                '<input class="form-control" id="contractNumber" type="number" ' +
                'placeholder="contractNumber" maxlength="255" value="' + currentUser.personalData.contractNumber + '">' +
                '<br>' +
                '<h5>Дата</h5>' +
                '<input class="form-control" id="date" type="date" ' +
                'placeholder="date" value="' + currentUser.personalData.date + '">' +
                '<br>' +
                '<h5>Полное имя</h5>' +
                '<input class="form-control" id="fullName" ' +
                'placeholder="fullName" maxlength="255" value="' + currentUser.personalData.fullName + '">' +
                '<br>' +
                '<h5>Серия паспорта</h5>' +
                '<input class="form-control" id="passportSeries" type="number" ' +
                'placeholder="passportSeries" maxlength="4" value="' + currentUser.personalData.passportSeries + '">' +
                '<br>' +
                '<h5>Номер паспорта</h5>' +
                '<input class="form-control" id="passportNumber" type="number" ' +
                'placeholder="passportNumber" maxlength="6" value="' + currentUser.personalData.passportNumber + '">' +
                '<br>' +
                '<h5>Выдан</h5>' +
                '<input class="form-control" id="passportIssued" ' +
                'placeholder="passportIssued" maxlength="255" value="' + currentUser.personalData.passportIssued + '">' +
                '<br>' +
                '<h5>Годен</h5>' +
                '<input class="form-control" id="issueDate" type="date" ' +
                'placeholder="issueDate" value="' + currentUser.personalData.issueDate + '">' +
                '<br>' +
                '<h5>Дата рождения</h5>' +
                '<input class="form-control" id="birthDate" type="date" ' +
                'placeholder="birthDate" value="' + currentUser.personalData.birthDate + '">' +
                '<br>' +
                '<h5>Регистрация</h5>' +
                '<input class="form-control" id="registration" ' +
                'placeholder="registration" maxlength="255" value="' + currentUser.personalData.registration + '">' +
                '<br>' +
                '<h5>Email</h5>' +
                '<input class="form-control" id="email" type="email" ' +
                'placeholder="email" maxlength="255" value="' + currentUser.personalData.email + '">' +
                '<br>' +
                '<h5>Номер телефона</h5>' +
                '<input class="form-control" id="phoneNumber" type="number" ' +
                'placeholder="phoneNumber" maxlength="11" value="' + currentUser.personalData.phoneNumber + '">' +
                '<br>' +
                '<button type="submit" id="addButton" ' +
                'class="btn btn-success">Сохранить' +
                '</button>' +
                '</div>' +
                '</form>')

            $('#addButton').submit(function () {
                save($('#actNumber').val(), $('#contractNumber').val(),
                    $('#date').val(), $('#fullName').val(),
                    $('#passportSeries').val(), $('#passportNumber').val(),
                    $('#passportIssued').val(), $('#issueDate').val(),
                    $('#birthDate').val(), $('#registration').val(),
                    $('#email').val(), $('#phoneNumber').val())
            })
            // getStudentQuestions(currentUser.login)
        }
    })
}

function save(actNumber, contractNumber, date, fullName, passportSeries, passportNumber, passportIssued,
              issueDate, birthDate, registration, email, phoneNumber) {
    $.ajax({
        url: 'personalData',
        dataType: 'json',
        method: 'PATCH',
        cache: false,
        contentType: 'application/json',
        data: JSON.stringify({
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
        success: function () {
            console.log('saved')
            getCurrentUser()
        }
    })
}