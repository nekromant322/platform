let currentUserLogin;
getCurrentUser();

$('.buttonAdd').on('click', function () {
    addInterview($("#company").val(), $("#description").val(), $("#contacts").val(), $("#date").val(), $("#time").val(),
        $("#meetingLink").val(), $("#salary").val(), $("#stack").val(), $("#distanceWork").val(), $("#comment").val(), currentUserLogin);
});

$('.editButton').on('click', function () {
    editInterview($("#idEd").val(), $("#companyEd").val(), $("#descriptionEd").val(), $("#contactsEd").val(), $("#dateEd").val(), $("#timeEd").val(),
        $("#meetingLinkEd").val(), $(`#salaryEd`).val(), $("#stackEd").val(), $("#distanceWorkEd").val(), $("#commentEd").val(), currentUserLogin);
});

function editInterview(id, company, description, contacts, date, time, meetingLink, salary, stack, distanceWork, comment, currentUserLogin) {
    $.ajax({
        url: '/interviewData/save',
        dataType: 'json',
        method: 'PATCH',
        cache: false,
        contentType: 'application/json',
        data: JSON.stringify({
            id: id,
            company: company,
            description: description,
            contacts: contacts,
            date: date,
            time: time,
            meetingLink: meetingLink,
            salary: salary,
            stack: stack,
            distanceWork: distanceWork,
            comment: comment,
            userLogin: currentUserLogin

        }),
        success: function () {
            getCurrentUser();
        }
    });
}

function addInterview(company, description, contacts, date, time, meetingLink, salary, stack, distanceWork, comment, currentUserLogin) {
    $.ajax({
        url: '/interviewData/save',
        dataType: 'json',
        method: 'PATCH',
        cache: false,
        contentType: 'application/json',
        data: JSON.stringify({
            company: company,
            description: description,
            contacts: contacts,
            date: date,
            time: time,
            meetingLink: meetingLink,
            salary: salary,
            stack: stack,
            distanceWork: distanceWork,
            comment: comment,
            userLogin: currentUserLogin

        }),
        success: function (data) {
            console.log(data);
        }
    });
}


function getInterview(currentUserLogin) {
    $.ajax({
        url: '/interviewData',
        type: 'GET',
        contentType: 'application/json',
        success: function (response) {
            drawColumns(response, currentUserLogin);
        },
        error: function (error) {
            console.log(error);
        }
    })
}

function drawColumns(data, currentUserLogin) {
    while (document.getElementById("interview-table").getElementsByTagName("tbody")[0].rows[0])
        document.getElementById("interview-table").getElementsByTagName("tbody")[0].deleteRow(0);
    for (let i = 0; i < data.length; i++) {
        if (data[i].userLogin === currentUserLogin) {
            addColumn(data[i]);
        }
    }
}

function addColumn(data) {
    let table = document.getElementById("interview-table").getElementsByTagName("tbody")[0];
    let tr = table.insertRow(table.rows.length);
    let td;

    insertTd(data.company, tr);
    insertTd(data.description, tr);
    insertTd(data.contacts, tr);
    insertTd(data.date, tr);
    insertTd(data.time, tr);
    insertTdLink(data.meetingLink, tr);
    insertTd(data.salary, tr);
    insertTd(data.stack, tr);
    insertTd(data.distanceWork, tr);
    insertTd(data.comment, tr);

    let delBtn = document.createElement("button");
    delBtn.className = "btn btn-danger";
    delBtn.innerHTML = "Delete";
    delBtn.type = "submit";
    delBtn.addEventListener("click", () => {
        remove(data.id);
    });
    let editBtn = document.createElement("button");
    editBtn.className = "btn btn-primary eBtn";
    editBtn.innerHTML = "Edit";
    editBtn.type = "submit";
    editBtn.addEventListener("click", () => {
        edit(data.id)
    });

    td = tr.insertCell(10);
    td.insertAdjacentElement("beforeend", delBtn);
    td.insertAdjacentElement("beforeend", editBtn);

}

function edit(id) {
    $('#myModal').modal('show');
    let href = "/interviewData/getData?id=" + id;
    $.get(href, function (interviewTable) {
        $(".modal #idEd").val(interviewTable.id);
        $(".modal #companyEd").val(interviewTable.company);
        $(".modal #descriptionEd").val(interviewTable.description);
        $(".modal #contactsEd").val(interviewTable.contacts);
        $(".modal #dateEd").val(interviewTable.date);
        $(".modal #timeEd").val(interviewTable.time);
        $(".modal #meetingLinkEd").val(interviewTable.meetingLink);
        $(".modal #salaryEd").val(interviewTable.salary);
        $(".modal #stackEd").val(interviewTable.stack);
        $(".modal #distanceWorkEd").val(interviewTable.distanceWork);
        $(".modal #commentEd").val(interviewTable.comment);

    });
}

function remove(id) {
    $.ajax({
        url: '/interviewData?id=' + id,
        type: 'DELETE',
        contentType: 'application/json',
        success: function () {
            getCurrentUser();
        },
        error: function (error) {
            console.log(error);
        }
    })
}

function insertTd(value, parent) {
    let element = document.createElement("td");
    element.scope = "row";
    element.innerText = value;
    parent.insertAdjacentElement("beforeend", element)

}

function insertTdLink(value, parent) {
    let element = document.createElement("td");
    element.scope = "row";

    let link = document.createElement("a")
    link.setAttribute("href", value)
    link.setAttribute("target", "_blank")
    link.innerText = value;

    element.insertAdjacentElement("afterbegin", link);
    parent.insertAdjacentElement("beforeend", element)
}

function getCurrentUser() {
    $.ajax({
        url: '/platformUsers/current',
        type: 'GET',
        contentType: 'application/json',
        success: function (user) {
            currentUserLogin = user.login;
            getInterview(currentUserLogin);
        }
    })
}