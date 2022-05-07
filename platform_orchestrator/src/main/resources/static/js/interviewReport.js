window.onload = function () {
    findInterviewReport();
};

let currentUserLogin;
let currentUserRole;
var arrayOfCompanies = ["SBER", "TINKOFF", "VK"];

function autocomplete(inp, arr) {
    var currentFocus;

    inp.addEventListener("input", function(e) {
        var a, b, i, val = this.value;

        closeAllLists();
        if (!val) {
            return false;
        }
        currentFocus = -1;

        a = document.createElement("DIV");
        a.setAttribute("id", this.id + "autocomplete-list");
        a.setAttribute("class", "autocomplete-items");

        this.parentNode.appendChild(a);

        for (i = 0; i < arr.length; i++) {
            if (arr[i].substr(0, val.length).toUpperCase() === val.toUpperCase()) {
                b = document.createElement("DIV");
                b.innerHTML = "<strong>" + arr[i].substr(0, val.length) + "</strong>";
                b.innerHTML += arr[i].substr(val.length);
                b.innerHTML += "<input type='hidden' value='" + arr[i] + "'>";
                b.addEventListener("click", function(e) {
                    inp.value = this.getElementsByTagName("input")[0].value;
                    closeAllLists();
                });
                a.appendChild(b);
            }
        }
    });

    inp.addEventListener("keydown", function(e) {
        var x = document.getElementById(this.id + "autocomplete-list");
        if (x) {
            x = x.getElementsByTagName("div");
        }
        if (e.keyCode === 40) {
            currentFocus++;
            addActive(x);
        } else if (e.keyCode === 38) {
            currentFocus--;
            addActive(x);
        } else if (e.keyCode === 13) {
            e.preventDefault();
            if (currentFocus > -1) {
                if (x) {
                    x[currentFocus].click();
                }
            }
        }
    });
    function addActive(x) {
        if (!x) {
            return false;
        }
        removeActive(x);
        if (currentFocus >= x.length) {
            currentFocus = 0;
        }
        if (currentFocus < 0) {
            currentFocus = (x.length - 1);
        }
        x[currentFocus].classList.add("autocomplete-active");
    }
    function removeActive(x) {
        for (var i = 0; i < x.length; i++) {
            x[i].classList.remove("autocomplete-active");
        }
    }
    function closeAllLists(element) {
        var x = document.getElementsByClassName("autocomplete-items");
        for (var i = 0; i < x.length; i++) {
            if (element !== x[i] && element !== inp) {
                x[i].parentNode.removeChild(x[i]);
            }
        }
    }

    document.addEventListener("click", function (e) {
        closeAllLists(e.target);
    });
}

autocomplete(document.getElementById("interviewReport-company"), arrayOfCompanies);

function getCurrentUser() {
    $.ajax({
        url: '/platformUser/current',
        type: 'GET',
        contentType: 'application/json',
        success: function (user) {
            currentUserLogin = user.login;
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
    interviewReport.userLogin = data.userLogin;
    interviewReport.company = data.company;
    interviewReport.project = data.project;
    interviewReport.questions = data.questions;
    interviewReport.impression = data.impression;
    interviewReport.minSalary = data.minSalary;
    interviewReport.maxSalary = data.maxSalary;
    interviewReport.status = data.status;
    interviewReport.level = data.level;

    function isNotDuplicate(company) {
        return company !== data.company;
    }

    if (arrayOfCompanies.every(isNotDuplicate)) {
        arrayOfCompanies.push(data.company);
    }

    let color;
    color = 'white';

    if (data.status === "Offer") {
        color = 'orange';
    }

    if (data.status === "Accepted") {
        color = 'green';
    }

    insertTd(data.date, tr, color);
    insertTd(data.userLogin, tr, color);
    insertTd(data.company, tr, color);
    insertTd(data.project, tr, color);
    insertTd(data.questions, tr, color);
    insertTd(data.impression, tr, color);
    insertTd(data.minSalary, tr, color);
    insertTd(data.maxSalary, tr, color);
    insertTd(data.level, tr, color);

    if ((currentUserRole === "ADMIN" || data.userLogin === currentUserLogin) && data.status === "Passed") {
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

    if ((currentUserRole === "ADMIN" || data.userLogin === currentUserLogin) && data.status === "Offer") {
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
    interviewReport.userLogin = currentUserLogin;
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