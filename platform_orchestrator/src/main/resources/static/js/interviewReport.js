window.onload = function () {
    findInterviewReport();
};

let currentUserLogin;
let currentUserRole;
var arrayOfCompanies = ["SBER", "TINKOFF", "VK"];

function autocomplete(inp, arr) {
    var currentFocus;

    inp.addEventListener("input", function (e) {
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
                b.addEventListener("click", function (e) {
                    inp.value = this.getElementsByTagName("input")[0].value;
                    closeAllLists();
                });
                a.appendChild(b);
            }
        }
    });

    inp.addEventListener("keydown", function (e) {
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
        url: '/platformUsers/current',
        type: 'GET',
        contentType: 'application/json',
        success: function (user) {
            currentUserLogin = user.login;
        }
    })
}

function getCurrentUserRole() {
    $.ajax({
        url: '/platformUsers/role',
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

    let interviewReportUpdateDTO = {}
    interviewReportUpdateDTO.id = data.id;
    interviewReportUpdateDTO.salary = data.maxSalary;

    function isNotDuplicate(company) {
        return company !== data.company;
    }

    if (arrayOfCompanies.every(isNotDuplicate)) {
        arrayOfCompanies.push(data.company);
    }

    let color;
    color = 'white';
    let legend;
    legend = "Собеседование пройдено";

    if (data.status === "Offer") {
        color = 'orange';
        legend = "Получен оффер";
    }

    if (data.status === "Accepted") {
        color = 'green';
        legend = "Оффер принят";
    }

    insertTd(data.date, tr, color, legend);
    insertTd(data.userLogin, tr, color, legend);
    insertTd(data.company, tr, color, legend);
    insertTd(data.project, tr, color, legend);
    insertTd(data.questions, tr, color, legend);
    insertTd(data.impression, tr, color, legend);
    insertTd(data.minSalary, tr, color, legend);
    insertTd(data.maxSalary, tr, color, legend);
    insertTd(data.currency, tr, color, legend);
    insertTd(data.level, tr, color, legend);

    if ((currentUserRole === "ADMIN" || data.userLogin === currentUserLogin) && data.status != "Passed") {
        getOfferDocument(data.id, tr);
    }

    if ((currentUserRole === "ADMIN" || data.userLogin === currentUserLogin) && data.status === "Passed") {
        let offerBtn = document.createElement("button");
        offerBtn.className = "btn btn-warning";
        offerBtn.innerHTML = "Получил(а) оффер";
        offerBtn.type = "submit";
        offerBtn.addEventListener("click", () => {
            changeStatus(interviewReportUpdateDTO, data, "offer");
        });
        td = tr.insertCell(10);
        td.style.backgroundColor = 'white';
        td.insertAdjacentElement("beforeend", offerBtn);
    }

    if ((currentUserRole === "ADMIN" || data.userLogin === currentUserLogin) && data.status === "Offer") {
        let acceptedBtn = document.createElement("button");
        acceptedBtn.className = "btn btn-success";
        acceptedBtn.innerHTML = "Принял(а) оффер";
        acceptedBtn.type = "submit";
        acceptedBtn.addEventListener("click", () => {
            changeStatus(interviewReportUpdateDTO, data, "accepted");
        });
        td = tr.insertCell(10);
        td.style.backgroundColor = 'white';
        td.insertAdjacentElement("beforeend", acceptedBtn);
    }

}

function getOfferDocument(reportId, tr) {

    $.ajax({
        method: 'GET',
        async: false,
        url: '/offer-document/' + reportId,
        contentType: 'application/json',
        success: function (response) {
            if (response.id === null) {
                createUploadForm(reportId, tr);
            } else {
                console.log(response);
                drawDownloadBtn(response, tr);
            }
        }
    });

}

function createUploadForm(id, tr) {

    let uploadForm = document.createElement("form");
    uploadForm.method = "POST";
    uploadForm.action = "/offer-document/upload/" + id;
    uploadForm.enctype = "multipart/form-data";

    let uploadFormInput = document.createElement("input");
    uploadFormInput.accept = ".pdf";
    uploadFormInput.type = "file";
    uploadFormInput.name = "file";
    uploadFormInput.multiple = true;
    uploadForm.appendChild(uploadFormInput);

    let uploadFormBtn = document.createElement("input");
    uploadFormBtn.type = "submit";
    uploadFormBtn.name = "Загрузить файл";
    uploadForm.appendChild(uploadFormBtn);

    let td = tr.insertCell(10);
    td.style.backgroundColor = 'white';
    td.insertAdjacentElement("beforeend", uploadForm);

}

function drawDownloadBtn(data, tr) {

    let downloadBtn = document.createElement("button");
    downloadBtn.className = "btn btn-success";
    downloadBtn.innerHTML = "Скачать файл";
    downloadBtn.type = "submit";
    downloadBtn.addEventListener("click", () => {
        downloadOfferFile(data.id, data.name);
    });

    let td = tr.insertCell(10);
    td.style.backgroundColor = 'white';
    td.insertAdjacentElement("beforeend", downloadBtn);

}

function downloadOfferFile(id, fileName) {

    $.ajax({
        url: '/offer-document/download/' + id,
        dataType: 'binary',
        xhrFields: {
            'responseType': 'blob'
        },
        success: function (data) {
            var blob = new Blob([data], {type: 'application/pdf'});
            var link = document.createElement('a');
            link.href = window.URL.createObjectURL(blob);
            link.download = 'Offer' + fileName;
            link.click();
        },
        error: function (error) {
            console.log(error);
        }
    });

}

function insertTd(value, parent, color, legend) {
    let element = document.createElement("td");
    element.scope = "row";
    element.innerText = value;
    element.style.backgroundColor = color;
    element.title = legend;
    parent.insertAdjacentElement("beforeend", element);
}

function changeStatus(interviewReportUpdateDTO, data, status) {
    let confirmation = (status === "offer") ? confirm("Вы уверены, что хотите изменить статус на " + status + "?") :
        (status === "accepted") ? confirm("Вы уверены, что принимаете данный оффер и выходите на работу в " + data.company + "?") : false;

    if (confirmation === true) {
        interviewReportUpdateDTO.salary = prompt("Уточните зарплату на руки", interviewReportUpdateDTO.salary);

        $.ajax({
            url: '/interviewReport/' + status,
            method: 'PATCH',
            contentType: 'application/json',
            data: JSON.stringify(interviewReportUpdateDTO),
            success: function () {
                console.log('updated')
            },
            error: function (error) {
                console.log(error);
            }
        });
        location.reload();
    }
}

function saveInterviewReport(interviewReportDTO) {
    $.ajax({
        url: '/interviewReport',
        method: 'PATCH',
        contentType: 'application/json',
        data: JSON.stringify(interviewReportDTO),
        success: function () {
            console.log('saved')
            location.reload();
        },
        error: function (error) {
            alert("Произошла непредвиденная ошибка. Пожалуйста, повторите попытку.");
            console.log(error);
        }
    });
}

function sendInterviewReport() {
    let interviewReportDTO = {}
    interviewReportDTO.date = $("#interviewReport-date").val();
    interviewReportDTO.userLogin = currentUserLogin;
    interviewReportDTO.company = $("#interviewReport-company").val();
    interviewReportDTO.project = $("#interviewReport-project").val();
    interviewReportDTO.questions = $("#interviewReport-questions").val();
    interviewReportDTO.impression = $("#interviewReport-impression").val();
    interviewReportDTO.minSalary = $("#interviewReport-min").val();
    interviewReportDTO.maxSalary = $("#interviewReport-max").val();
    interviewReportDTO.currency = $("#interviewReport-currency").val();
    interviewReportDTO.status = "Passed";
    interviewReportDTO.level = $("#interviewReport-level").val();

    function emptyField(field) {
        return String(field).length < 1;
    }

    const errorMessage = "Поле не должно быть пустым";

    if (checkAlert("interviewReport-date", "interviewReport-date-alert", errorMessage, emptyField(interviewReportDTO.date))) {
        return;
    }
    if (checkAlert("interviewReport-company", "interviewReport-company-alert", errorMessage, emptyField(interviewReportDTO.company))) {
        return;
    }
    if (checkAlert("interviewReport-project", "interviewReport-project-alert", errorMessage, emptyField(interviewReportDTO.project))) {
        return;
    }
    if (checkAlert("interviewReport-questions", "interviewReport-questions-alert", errorMessage, emptyField(interviewReportDTO.questions))) {
        return;
    }
    if (checkAlert("interviewReport-min", "interviewReport-min-alert", errorMessage, emptyField(interviewReportDTO.minSalary))) {
        return;
    }
    if (checkAlert("interviewReport-max", "interviewReport-max-alert", errorMessage, emptyField(interviewReportDTO.maxSalary))) {
        return;
    }

    let confirmation =
        confirm("Отправить отчёт о собеседовании?");

    if (confirmation === true) {
        saveInterviewReport(interviewReportDTO);
    }
}

function openInterviewReportForm() {
    document.getElementById("interviewReportForm").style.display = "block";
}

function closeInterviewReportForm() {
    document.getElementById("interviewReportForm").style.display = "none";
}