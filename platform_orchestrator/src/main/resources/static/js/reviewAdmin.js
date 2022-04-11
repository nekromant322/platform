let mentorLogin;
let today = new Date().getDate();
let tomorrow = new Date().getDate() + MILLIS_PER_DAY;

// confirm() // подтверждение действия
// window.onload = newReviewRequests();

function findReview(reviewDTO) {
    alert(reviewDTO);
    $.ajax({
        method: 'GET',
        url: '/reviews',
        contentType: 'application/json',
        data: JSON.stringify(reviewDTO),
        success: function (response) {
            drawColumns(response);
        },
        error: function (error) {
            console.log(error);
        }
    })
}

function drawColumns(data) {
    while (document.getElementById("review-table").getElementsByTagName("tbody")[0].rows[0])
        document.getElementById("review-table").getElementsByTagName("tbody")[0].deleteRow(0);
    for (let i = 0; i < data.length; i++) {
        addColumn(data[i]);
    }
}

function addColumn(data) {
    let table = document.getElementById("review-table").getElementsByTagName("tbody")[0];
    let tr = table.insertRow(table.rows.length);

    insertTd(data.id, tr);
    insertTd(data.topic, tr);
    insertTd(data.studentLogin, tr);
    insertTd(data.mentorLogin, tr);
    insertTd(data.bookedDate, tr);
    insertTd(data.bookedTime, tr);
    insertTd(data.timeSlots, tr);
}

function insertTd(value, parent) {
    let element = document.createElement("td");
    element.scope = "row";
    element.innerText = value;
    parent.insertAdjacentElement("beforeend", element)
}

function newReviewRequests() {
    let reviewDTO = {};
    reviewDTO.bookedDate = null;
    reviewDTO.bookedTime = null;
    reviewDTO.mentorLogin = null;
    reviewDTO.studentLogin = null;
    reviewDTO.json();
    findReview(reviewDTO);
}

function myReview() {
    let reviewDTO = {};
    reviewDTO.bookedDate = null;
    reviewDTO.bookedTime = null;
    reviewDTO.mentorLogin = mentorLogin;
    reviewDTO.studentLogin = null;
    reviewDTO.json();
    findReview(reviewDTO);
}

function todayReview() {
    let reviewDTO = {};
    reviewDTO.mentorLogin = null;
    reviewDTO.studentLogin = null;
    reviewDTO.bookedTime = null;
    reviewDTO.bookedDate = today;
    reviewDTO.json();
    findReview(reviewDTO);
}

function tomorrowReview() {
    let reviewDTO = {};
    reviewDTO.mentorLogin = null;
    reviewDTO.studentLogin = null;
    reviewDTO.bookedTime = null;
    reviewDTO.bookedDate = tomorrow;
    reviewDTO.json();
    findReview(reviewDTO);
}

function searchReview() {
    let reviewDTO = {};
    reviewDTO.json();
    findReview(reviewDTO);
}

function acceptReview(reviewDTO) {
    $.ajax({
        url: '/reviews',
        method: 'PATCH',
        contentType: 'application/json',
        data: JSON.stringify(reviewDTO),
        success: function () {
            console.log('accepted')
        },
        error: function (error) {
            console.log(error);
        }
    })
}

function editReview(reviewDTO) {
    $.ajax({
        url: '/reviews',
        method: 'PATCH',
        contentType: 'application/json',
        data: JSON.stringify(reviewDTO),
        success: function () {
            console.log('edited')
        },
        error: function (error) {
            console.log(error);
        }
    })
}

function deleteReview(reviewDTO) {
    $.ajax({
        url: '/reviews',
        method: 'DELETE',
        contentType: 'application/json',
        data: JSON.stringify(reviewDTO),
        success: function () {
            console.log('deleted')
        },
        error: function (error) {
            console.log(error);
        }
    })
}