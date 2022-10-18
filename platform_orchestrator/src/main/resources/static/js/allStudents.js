getStudents();
btnClickListener();

function getStudents() {
    $.ajax({
        url: '/platformUsers',
        type: 'GET',
        contentType: 'application/json',
        success: function (response) {
            drawColumns(response);
        },
        error: function (error) {
            console.log(error);
        }
    })
}

function drawColumns(data) {
    while (document.getElementById("requests-table").getElementsByTagName("tbody")[0].rows[0])
        document.getElementById("requests-table").getElementsByTagName("tbody")[0].deleteRow(0);
    for (let i = 0; i < data.length; i++) {
        if (data[i].studyStatus === "ACTIVE" && data[i].authorities[0].authority !== "ROLE_GRADUATE") {
            addColumn(data[i]);
        }
    }
    for (let i = 0; i < data.length; i++) {
        if (data[i].authorities[0].authority === "ROLE_GRADUATE" && data[i].studyStatus !== "BAN") {
            addColumn(data[i]);
        }
    }
    for (let i = 0; i < data.length; i++) {
        if (data[i].studyStatus === "BAN") {
            addColumn(data[i]);
        }
    }
}

function addColumn(data) {
    let table = document.getElementById("requests-table").getElementsByTagName("tbody")[0];
    let tr = table.insertRow(table.rows.length);
    let td;

    let color;
    color = 'orange';
    let legend;
    legend = "CORE";

    if (data.coursePart === "WEB") {
        color = 'blue';
        legend = "WEB";
    }

    if (data.coursePart === "PREPROJECT") {
        color = 'green';
        legend = "PREPROJECT";
    }

    insertTd(data.id, tr);
    insertTd(data.login, tr);

    let updateBtn = document.createElement("button");
    updateBtn.className = "btn btn-success";
    updateBtn.innerHTML = "Повысить";
    updateBtn.type = "submit";

    let courseBtn = document.createElement("button");
    courseBtn.className = "btn btn-success";
    courseBtn.innerHTML = legend;
    courseBtn.title = legend;
    courseBtn.style.backgroundColor = color;
    courseBtn.type = "submit";

    if (data.authorities[0].authority === "ROLE_GRADUATE") {
        document.getElementsByClassName("finish-education").disable = true;
    }
    if (data.studyStatus === "BAN") {
        updateBtn.disabled = true;
    }
    updateBtn.addEventListener("click", () => {
        updateUserRole(data.id, "ADMIN");
    });
    td = tr.insertCell(2);
    td.insertAdjacentElement("beforeend", courseBtn);
    td = tr.insertCell(3);
    td.insertAdjacentElement("beforeend", updateBtn);
    td = tr.insertCell(4);

    if (data.studyStatus === "ACTIVE" && data.authorities[0].authority === "ROLE_USER") {

        courseBtn.addEventListener("click", () => {
            if (data.coursePart === "CORE") {
                updateCurrentCoursePart(data.id, "WEB");
            } else {
                updateCurrentCoursePart(data.id, "PREPROJECT");
            }
        });

        td.insertAdjacentHTML("beforeend",
            `
            <button type="button" class="btn btn-primary finish-education" data-toggle="modal" data-target="#aceptModal${data.id}">
                Завершить обучение
            </button>

            <div class="modal fade" id="aceptModal${data.id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
              <div class="modal-dialog" role="document">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Устроился на работу?</h5>
                    <button type="button" class="btn-close" data-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal" data-btn="working" data-id = "${data.id}">Устроился</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal" data-btn="ban" data-id = "${data.id}" >Забанить</button>
                  </div>
                </div>
              </div>
            </div>            
           `
        )
    }
    if (data.authorities[0].authority === "ROLE_GRADUATE") {
        let status = document.createElement("span");
        status.innerHTML = "GRADUATE ";
        td.insertAdjacentElement("beforeend", status);
    }
    if (data.studyStatus === "BAN") {
        let status = document.createElement("span");
        status.innerHTML = data.studyStatus;
        td.insertAdjacentElement("beforeend", status);
    }
    ;
    td = tr.insertCell(4);
    let codeTries = document.createElement("button");
    codeTries.className = "btn btn-success";
    codeTries.innerHTML = "Посмотреть решения";

    td.insertAdjacentElement("beforeend", codeTries);

    codeTries.addEventListener("click", () => {
        sessionStorage.setItem("admin", "yes")
        sessionStorage.setItem("id", data.id)
        window.location.href = "/codeTryList";
    });

    let documents = document.createElement("button");
    documents.className = "btn btn-outline-dark";
    documents.innerHTML = "Посмотреть документы";
    documents.type = "submit";
    documents.addEventListener("click", () => {
        sessionStorage.setItem("id", data.id)
        window.location.href = "/documentList";
    });

    td = tr.insertCell(6);
    td.insertAdjacentElement("beforeend", documents);

    getRequestCheckingPersonalData(data.login, tr);
}

function getRequestCheckingPersonalData(userLogin, tr) {

    $.ajax({
        url: '/personalData/requestToCheck/' + userLogin,
        method: 'GET',
        contentType: 'application/json',
        async: false,
        cache: false,
        success: function(personalData) {

            let dataId = personalData.id;
            if(dataId === null) { return; }

            let empty = '';

            let actNumber = personalData.actNumber;
            let contractNumber = personalData.contractNumber;
            let contractDate = personalData.contractDate;
            let fullName = personalData.fullName;
            let passportSeries = personalData.passportSeries;
            let passportNumber = personalData.passportNumber;
            let passportIssued = personalData.passportIssued;
            let issueDate = personalData.issueDate;
            let birthDate = personalData.birthDate;
            let registration = personalData.registration;
            let email = personalData.email;
            let phoneNumber = personalData.phoneNumber;

            let requestBtn = document.createElement("button");
            requestBtn.id = "requestBtn-" + dataId;
            requestBtn.className = "btn btn-warning";
            requestBtn.innerHTML = "Запрос на измененние данных";
            requestBtn.type = "button";

            let td = tr.insertCell(6);
            td.insertAdjacentElement("beforeend", requestBtn);

            $('#forms-review').append(
                '<div id="form-div-' + dataId + '">' +
                '<form id="editForm-' + dataId + '">' +
                '<div class="form-group">' +
                '<h5>Номер акта</h5>' +
                '<input class="form-control input-number" id="actNumber-' + dataId + '" ' +
                'placeholder="actNumber" maxlength="255" ' +
                'value="' + (actNumber == null ? empty : actNumber) + '" >' +
                '<br>' +

                '<h5>Номер контракта</h5>' +
                '<input class="form-control" id="contractNumber-' + dataId + '" type="text" ' +
                'placeholder="contractNumber" maxlength="255" ' +
                'value="' + (contractNumber == null ? empty : contractNumber) + '" >' +
                '<br>' +

                '<h5>Дата</h5>' +
                '<input class="form-control" id="contractDate-' + dataId + '" type="text" ' +
                'placeholder="contractDate" ' +
                'value="' + (contractDate == null ? empty : contractDate) + '" >' +
                '<br>' +

                '<h5>ФИО</h5>' +
                '<input class="form-control" id="fullName-' + dataId + '" ' +
                'placeholder="fullName" maxlength="255" ' +
                'value="' + (fullName == null ? empty : fullName) + '" >' +
                '<br>' +

                '<h5>Серия паспорта</h5>' +
                '<input class="form-control input-number" id="passportSeries-' + dataId + '" ' +
                'placeholder="passportSeries" maxlength="4" ' +
                'value="' + (passportSeries == null ? empty : passportSeries) + '" >' +
                '<br>' +

                '<h5>Номер паспорта</h5>' +
                '<input class="form-control input-number" id="passportNumber-' + dataId + '" ' +
                'placeholder="passportNumber" maxlength="6" ' +
                'value="' + (passportSeries == null ? empty : passportNumber) + '" >' +
                '<br>' +

                '<h5>Выдан</h5>' +
                '<input class="form-control" id="passportIssued-' + dataId + '" ' +
                'placeholder="passportIssued" maxlength="255" ' +
                'value="' + (passportIssued == null ? empty : passportIssued) + '" >' +
                '<br>' +

                '<h5>Годен</h5>' +
                '<input class="form-control" id="issueDate-' + dataId + '" type="text" ' +
                'placeholder="issueDate" ' +
                'value="' + (issueDate == null ? empty : issueDate) + '" >' +
                '<br>' +

                '<h5>Дата рождения</h5>' +
                '<input class="form-control" id="birthDate-' + dataId + '" type="text" ' +
                'placeholder="birthDate" ' +
                'value="' + (birthDate == null ? empty : birthDate) + '" >' +
                '<br>' +

                '<h5>Регистрация</h5>' +
                '<input class="form-control" id="registration-' + dataId + '" ' +
                'placeholder="registration" maxlength="255" ' +
                'value="' + (registration == null ? empty : registration) + '" >' +
                '<br>' +

                '<button type="submit" id="addButton-' + dataId + '" ' +
                'class="btn btn-success">Сохранить' +
                '</button>' +

                '</div>' +
                '</form>' +
                '</div>'
            );

            $('#form-div-'+ dataId).hide();

            $('#requestBtn-'+ dataId).click(function () {
                $('#requests-table').hide();
                $('#form-div-'+ dataId).show();
            });

            $('#editForm-'+ dataId).submit(function () {
                save(dataId,
                    $('#actNumber-'+ dataId).val(),
                    $('#contractNumber-'+ dataId).val(),
                    $('#contractDate-'+ dataId).val(),
                    $('#fullName-'+ dataId).val(),
                    $('#passportSeries-'+ dataId).val(),
                    $('#passportNumber-'+ dataId).val(),
                    $('#passportIssued-'+ dataId).val(),
                    $('#issueDate-'+ dataId).val(),
                    $('#birthDate-'+ dataId).val(),
                    $('#registration-'+ dataId).val(),
                    email,
                    phoneNumber,
                    userLogin);
            });

        },
        error: function() {
            console.log(error);
        }
    });

}

function save(id, actNumber, contractNumber, contractDate, fullName, passportSeries, passportNumber, passportIssued,
              issueDate, birthDate, registration, email, phoneNumber, login) {
    $.ajax({
        url: '/personalData/requestToCheck/' + login,
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

function setWorkStatus(id, status) {
    let data = {};
    data.id = id;
    $.ajax({
        url: '/platformUsers/' + id + '/' + status,
        type: 'PUT',
        contentType: 'application/json',
        success: function () {
            getStudents();
        },
        error: function (error) {
            console.log(error);
        }
    })
}

function btnClickListener() {
    let table = document.getElementById("requests-table").getElementsByTagName("tbody")[0];
    table.addEventListener("click", event => {
        const working = event.target.dataset.btn === "working";
        const ban = event.target.dataset.btn === "ban";
        if (working) {
            updateUserRole(event.target.dataset.id, "GRADUATE");
            getStudents();
        }
        if (ban) {
            setWorkStatus(event.target.dataset.id, "BAN");
            getStudents();
        }
    });
}

function updateUserRole(id, status) {
    let confirmation = confirm("Вы уверены, что хотите повысить студента до " + status + " ?");
    if (confirmation === true) {
        let data = {};
        data.id = id;
        $.ajax({
            url: '/platformUsers/' + id + '/' + status,
            type: 'POST',
            contentType: 'application/json',
            success: function () {
                getStudents();
            },
            error: function (error) {
                console.log(error);
            }
        })
    }
}

function updateCurrentCoursePart(id, coursePart) {
    let confirmation = confirm("Вы уверены, что хотите перевести пользователя на этап " + coursePart + " ?");
    if (confirmation === true) {
        let data = {};
        data.id = id;
        $.ajax({
            url: '/platformUsers/promoteCoursePart/' + id + '/' + coursePart,
            type: 'POST',
            contentType: 'application/json',
            success: function () {
                getStudents();
            },
            error: function (error) {
                console.log(error);
            }
        })
    }
}

function insertTd(value, parent) {
    let element = document.createElement("td");
    element.scope = "row";
    element.innerText = value;
    parent.insertAdjacentElement("beforeend", element)
}
