window.onload = function () {
    getCurrentUser();
};

function getCurrentUser() {
    $.ajax({
        url: 'questions/current',
        type: 'GET',
        contentType: 'application/json',
        success: function (currentUser) {
            $('#lk').append(currentUser.login)
            $('#cardBody').append('<form id="form">' +
                '<div class="form-group">' +
                '<h5>Номер акта</h5>' +
                '<input class="form-control" id="actNumber" type="number" ' +
                'placeholder="actNumber" maxlength="255" value="' + currentUser.personalData.actNumber + '" required>' +
                '<br>' +
                '<h5>Номер контракта</h5>' +
                '<input class="form-control" id="contractNumber" type="number" ' +
                'placeholder="contractNumber" maxlength="255" value="' + currentUser.personalData.contractNumber + '" required>' +
                '<br>' +
                '<h5>Дата</h5>' +
                '<input class="form-control" id="date" type="date" ' +
                'placeholder="date" value="' + currentUser.personalData.date + '" required>' +
                '<br>' +
                '<h5>Полное имя</h5>' +
                '<input class="form-control" id="fullName" ' +
                'placeholder="fullName" maxlength="255" value="' + currentUser.personalData.fullName + '" required>' +
                '<br>' +
                '<h5>Серия паспорта</h5>' +
                '<input class="form-control" id="passportSeries" type="number" ' +
                'placeholder="passportSeries" maxlength="4" value="' + currentUser.personalData.passportSeries + '" required>' +
                '<br>' +
                '<h5>Номер паспорта</h5>' +
                '<input class="form-control" id="passportNumber" type="number" ' +
                'placeholder="passportNumber" maxlength="6" value="' + currentUser.personalData.passportNumber + '" required>' +
                '<br>' +
                '<h5>Выдан</h5>' +
                '<input class="form-control" id="passportIssued" ' +
                'placeholder="passportIssued" maxlength="255" value="' + currentUser.personalData.passportIssued + '" required>' +
                '<br>' +
                '<h5>Годен</h5>' +
                '<input class="form-control" id="issueDate" type="date" ' +
                'placeholder="issueDate" value="' + currentUser.personalData.issueDate + '" required>' +
                '<br>' +
                '<h5>Дата рождения</h5>' +
                '<input class="form-control" id="birthDate" type="date" ' +
                'placeholder="birthDate" value="' + currentUser.personalData.birthDate + '" required>' +
                '<br>' +
                '<h5>Регистрация</h5>' +
                '<input class="form-control" id="registration" ' +
                'placeholder="registration" maxlength="255" value="' + currentUser.personalData.registration + '" required>' +
                '<br>' +
                '<h5>Email</h5>' +
                '<input class="form-control" id="email" type="email" ' +
                'placeholder="email" maxlength="255" value="' + currentUser.personalData.email + '" required>' +
                '<br>' +
                '<h5>Номер телефона</h5>' +
                '<input class="form-control" id="phoneNumber" type="number" ' +
                'placeholder="phoneNumber" maxlength="11" value="' + currentUser.personalData.phoneNumber + '" required>' +
                '<br>' +
                '<button type="submit" id="addButton" ' +
                'class="btn btn-success">Сохранить' +
                '</button>' +
                '</div>' +
                '</form>')
            // getStudentQuestions(currentUser.login)
        }
    })
}