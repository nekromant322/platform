window.onload = function () {
    findInterviewReport();
};

let currentUser;
let currentUserRole;

function getCurrentUser() {
    $.ajax({
        url: '/platformUser/current',
        type: 'GET',
        contentType: 'application/json',
        success: function (user) {
            currentUser = user;
        }
    })
}

function getCurrentUserRole() {
    $.ajax({
        url: '/platformUser/role',
        type: 'GET',
        contentType: 'application/json',
        success: function (role) {
            currentUserRole = role;
        }
    })
}

function findInterviewReport() {
    getCurrentUser();
    getCurrentUserRole();
    $.ajax({
        method: 'GET',
        url: '/interviewReport',
        contentType: 'application/json',
        success: function (response) {
            console.log(response);
            drawColumns(response);
        },
        error: function (error) {
            console.log(error);
        }
    })
}

function drawColumns(data) {
    while (document.getElementById("interviewReport-table").getElementsByTagName("tbody")[0].rows[0])
        document.getElementById("interviewReport-table").getElementsByTagName("tbody")[0].deleteRow(0);
    for (let i = 0; i < data.length; i++) {
        addColumn(data[i]);
    }
}

function addColumn(data) {
    let table = document.getElementById("interviewReport-table").getElementsByTagName("tbody")[0];
    let tr = table.insertRow(table.rows.length);
    let td;

    let interviewReport = {}
    interviewReport.id = data.id;
    interviewReport.date = data.date;
    interviewReport.email = data.email;
    interviewReport.company = data.company;
    interviewReport.project = data.project;
    interviewReport.questions = data.questions;
    interviewReport.impression = data.impression;
    interviewReport.minSalary = data.minSalary;
    interviewReport.maxSalary = data.maxSalary;
    interviewReport.status = data.status;
    interviewReport.level = data.level;
    interviewReport.userId = data.userId;

    let color;
    color = 'white';

    if (data.status === "Offer") {
        color = 'orange';
    }

    if (data.status === "Accepted") {
        color = 'green';
    }

    insertTd(data.date, tr, color);
    insertTd(data.email, tr, color);
    insertTd(data.company, tr, color);
    insertTd(data.project, tr, color);
    insertTd(data.questions, tr, color);
    insertTd(data.impression, tr, color);
    insertTd(data.minSalary, tr, color);
    insertTd(data.maxSalary, tr, color);
    insertTd(data.level, tr, color);

    if ((data.userId === currentUser.id || currentUserRole === "ADMIN") && data.status === "Passed") {
        let offerBtn = document.createElement("button");
        offerBtn.className = "btn btn-warning";
        offerBtn.innerHTML = "Получил(а) оффер";
        offerBtn.type = "submit";
        offerBtn.addEventListener("click", () => {
            updateInterviewReport(interviewReport);
        });
        td = tr.insertCell(9);
        td.style.backgroundColor = 'white';
        td.insertAdjacentElement("beforeend", offerBtn);
    }

    if ((data.userId === currentUser.id || currentUserRole === "ADMIN") && data.status === "Offer") {
        let acceptedBtn = document.createElement("button");
        acceptedBtn.className = "btn btn-success";
        acceptedBtn.innerHTML = "Принял(а) оффер";
        acceptedBtn.type = "submit";
        acceptedBtn.addEventListener("click", () => {
            updateInterviewReport(interviewReport);
        });
        td = tr.insertCell(9);
        td.style.backgroundColor = 'white';
        td.insertAdjacentElement("beforeend", acceptedBtn);
    }
}

function insertTd(value, parent, color) {
    let element = document.createElement("td");
    element.scope = "row";
    element.innerText = value;
    element.style.backgroundColor = color;
    parent.insertAdjacentElement("beforeend", element);
}

function saveInterviewReport(interviewReportDTO) {
    $.ajax({
        url: '/interviewReport',
        method: 'PATCH',
        contentType: 'application/json',
        data: JSON.stringify(interviewReportDTO),
        success: function () {
            console.log('saved')
        },
        error: function (error) {
            console.log(error);
        }
    })
    location.reload();
}

function updateInterviewReport(interviewReportDTO) {
    let confirmation =
        confirm("Вы уверены, что хотите изменить статус?");

    if (confirmation === true) {
        interviewReportDTO.maxSalary = prompt("Уточните зарплату на руки", interviewReportDTO.maxSalary);
        interviewReportDTO.minSalary = interviewReportDTO.maxSalary;

        if (interviewReportDTO.status === "Offer") {
            interviewReportDTO.status = "Accepted";
        }
        if (interviewReportDTO.status === "Passed") {
            interviewReportDTO.status = "Offer";
        }
        saveInterviewReport(interviewReportDTO);
    }
}

function sendInterviewReport() {
    let interviewReport = {}
    interviewReport.date = $("#interviewReport-date").val();
    interviewReport.email = $("#interviewReport-email").val();
    interviewReport.company = $("#interviewReport-company").val();
    interviewReport.project = $("#interviewReport-project").val();
    interviewReport.questions = $("#interviewReport-questions").val();
    interviewReport.impression = $("#interviewReport-impression").val();
    interviewReport.minSalary = $("#interviewReport-min").val();
    interviewReport.maxSalary = $("#interviewReport-max").val();
    interviewReport.status = $("#interviewReport-status").val();
    interviewReport.level = $("#interviewReport-level").val();

    function emptyField(field) {
        return String(field).length < 1;
    }
    const errorMessage = "Поле не должно быть пустым";

    if (checkAlert("interviewReport-date", "interviewReport-date-alert", errorMessage, emptyField(interviewReport.date))) {
        return;
    }
    if (checkAlert("interviewReport-email", "interviewReport-email-alert", errorMessage, emptyField(interviewReport.email))) {
        return;
    }
    if (checkAlert("interviewReport-company", "interviewReport-company-alert", errorMessage, emptyField(interviewReport.company))) {
        return;
    }
    if (checkAlert("interviewReport-project", "interviewReport-project-alert", errorMessage, emptyField(interviewReport.project))) {
        return;
    }
    if (checkAlert("interviewReport-questions", "interviewReport-questions-alert", errorMessage, emptyField(interviewReport.questions))) {
        return;
    }
    if (checkAlert("interviewReport-min", "interviewReport-min-alert", errorMessage, emptyField(interviewReport.minSalary))) {
        return;
    }
    if (checkAlert("interviewReport-max", "interviewReport-max-alert", errorMessage, emptyField(interviewReport.maxSalary))) {
        return;
    }

    let confirmation =
        confirm("Отправить отчёт о собеседовании?");

    if (confirmation === true) {
        saveInterviewReport(interviewReport);
    }
}

function openInterviewReportForm() {
    document.getElementById("interviewReportForm").style.display = "block";
}

function closeInterviewReportForm() {
    document.getElementById("interviewReportForm").style.display = "none";
}