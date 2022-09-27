let today = new Date();
let tomorrow = new Date(today.getTime() + MILLIS_PER_DAY);
let student;

window.onload = function () {
    student = getCurrentUser().login;
    myReview();
};

function myReview() {
    clearTable();
    let reviewFilterDTO = {};
    reviewFilterDTO.bookedDate = null;
    reviewFilterDTO.mentorLogin = null;
    reviewFilterDTO.studentLogin = student;
    findReviews(reviewFilterDTO);
}

function findReviews(reviewFilterDTO) {
    $.ajax({
        method: 'POST',
        url: '/reviews',
        contentType: 'application/json',
        data: JSON.stringify(reviewFilterDTO),
        success: function (response) {
            console.log(response);
            drawColumns(response);
        },
        error: function (error) {
            console.log(error);
        }
    })
}

function clearTable() {
    while (document.getElementById("review-table").getElementsByTagName("tbody")[0].rows[0])
        document.getElementById("review-table").getElementsByTagName("tbody")[0].deleteRow(0);
}

function drawColumns(data) {
    for (let i = 0; i < data.length; i++) {
        addColumns(data[i]);
    }
    sortByDate();
}

function addColumns(data) {
    let table = document.getElementById("review-table").getElementsByTagName("tbody")[0];
    let tr = table.insertRow(table.rows.length);

    insertTd(data.topic, tr);
    if (data.mentorLogin != "") {
        insertTd(data.mentorLogin, tr);
    } else insertTd("Ожидает подтверждения", tr);
    insertTd(data.bookedDate, tr);
    if (data.bookedTime != null) {
        insertTd(data.bookedTime, tr);
    } else insertTd("Ожидает подтверждения", tr);
    if (data.callLink != "") {
        insertTdLink(data.callLink, tr);
    } else insertTd("Ожидает подтверждения", tr);
}

function insertTdLink(value, parent) {
    let element = document.createElement("td");
    element.scope = "row";

    let link = document.createElement("a")
    link.setAttribute("href", value)
    link.setAttribute("target", "_blank")
    link.innerText = "Ссылка на встречу";

    element.insertAdjacentElement("afterbegin", link);
    parent.insertAdjacentElement("beforeend", element)
}

function insertTd(value, parent) {
    let element = document.createElement("td");
    element.scope = "row";
    element.innerText = value;
    parent.insertAdjacentElement("beforeend", element)
}

function futureReviews() {
    clearTable();

    let reviewFilterDTO = {};
    reviewFilterDTO.mentorLogin = null;
    reviewFilterDTO.studentLogin = student;
    reviewFilterDTO.bookedDate = today;
    findReviews(reviewFilterDTO);

    reviewFilterDTO.mentorLogin = null;
    reviewFilterDTO.studentLogin = student;
    reviewFilterDTO.bookedDate = tomorrow;
    findReviews(reviewFilterDTO);
}

function convertDate(d) {
    let p = d.split("-");
    return +(p[0] + p[1] + p[2]);
}

function sortByDate() {
    let tbody = document.querySelector("#review-table tbody");

    let rows = [].slice.call(tbody.querySelectorAll("tr"));

    rows.sort(function (a, b) {
        return convertDate(b.cells[2].innerHTML) - convertDate(a.cells[2].innerHTML);
    });

    rows.forEach(function (v) {
        tbody.appendChild(v);
    });
}

