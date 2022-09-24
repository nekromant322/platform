let today = new Date();
let tomorrow = new Date(today.getTime() + MILLIS_PER_DAY);
let btnCase;
let mentor;
const editReviewId = parseUrlParams("reviewId");
const editReviewBookedTime = parseUrlParams("reviewBookedTime");

window.onload = function () {
    parseAnchorIfExist();
    mentor = getCurrentUser().login;
    newReviewRequests();
};

function findReview(reviewFilterDTO, identifier) {
    $.ajax({
        method: 'POST',
        url: '/reviews',
        contentType: 'application/json',
        data: JSON.stringify(reviewFilterDTO),
        success: function (response) {
            console.log(response);
            drawColumns(response, identifier);
        },
        error: function (error) {
            console.log(error);
        }
    })
}

function sortDataByTime(data) {
    //сортировка всего массива по дате ревью
    data.sort(function (a, b) {
        var dateA = new Date(a.bookedDate), dateB = new Date(b.bookedDate)
        return dateA - dateB //sort by date ascending
    });

    if (data[0].bookedTime !== null) {
        //разбиение массива на под массивы по одной дате
        let counter1 = 0;
        let counter2 = 0;
        let i = 0;
        let dataArray = [[]];

        while (i < data.length) {
            dataArray[counter1][counter2] = data[i];
            if (data[i + 1] == null) {
                break;
            }
            if (data[i].bookedDate !== data[i + 1].bookedDate) {
                counter2 = 0;
                counter1++;
                dataArray[counter1] = [];

            } else {
                counter2++;
            }
            i++;
        }

        //сортировка отдельно каждого массива и объединение в один
        let newArrayData = [];
        i = 0;

        while (i < dataArray.length) {
            dataArray[i].sort(
                function (a, b) {
                    return a.bookedTime.localeCompare(b.bookedTime);
                }
            )
            dataArray[i].forEach(function (item) {
                newArrayData.push(item)
            })
            i++;
        }

        return newArrayData;
    } else return data;
}

function separatePastReviews(data) {
    if (data[0].bookedTime !== null) {
        let i = 0;
        let futureDateArray = [];
        let pastDateArray = [];

        while (i < data.length) {
            if (new Date(data[i].bookedDate + " " + data[i].bookedTime) < Date.now()) {
                pastDateArray.push(data[i]);
            } else {
                futureDateArray.push(data[i]);
            }
            i++;
        }
        return [...futureDateArray, ...pastDateArray];
    } else return data;
}

function drawColumns(data, identifier) {
    while (document.getElementById("review-table").getElementsByTagName("tbody")[0].rows[0])
        document.getElementById("review-table").getElementsByTagName("tbody")[0].deleteRow(0);
    if (identifier === "tomorrow" || identifier === "today") {
        for (let i = 0; i < data.length; i++) {
            addColumnForTodayAndTomorrow(data[i]);
        }
    } else {
        data = sortDataByTime(data);
        data = separatePastReviews(data);
        for (let i = 0; i < data.length; i++) {
            addColumn(data[i], identifier);
        }
    }
}

function addColumnForTodayAndTomorrow(data) {
    let table = document.getElementById("review-table").getElementsByTagName("tbody")[0];
    let tr = table.insertRow(table.rows.length);
    let td;

    if (data.bookedTime != null) {
        insertTd(data.id, tr);
        insertTd(data.topic, tr);
        insertTd(data.studentLogin, tr);
        insertTd(data.mentorLogin, tr);
        insertTd(data.bookedDate, tr);
        if (data.callLink != "") {
            insertTdLink(data.callLink, tr);
        }else insertTd("", tr);
        if (data.bookedTime != null) {
            insertTd(data.bookedTime.substring(0, 5), tr);
        } else insertTd(data.bookedTime, tr);

        let review = {}
        review.id = data.id;
        review.topic = data.topic;
        review.studentLogin = data.studentLogin;
        review.mentorLogin = data.mentorLogin;
        review.bookedDate = data.bookedDate;
        review.bookedTime = data.bookedTime;
        review.timeSlots = data.timeSlots;

        let times = data.timeSlots.toString().split(",");
        for (i = 0; i < times.length; i++) {
            times[i] = times[i].substring(0, 5);
        }
        times.sort();

        if (btnCase === 1) {
            for (let i = 0; i < times.length; i++) {
                let sumId = "#lastColumn";
                $(sumId).append("<th scope=\"col\" id=\"lastColumn" + i + "\">  </th>");
                let acceptBtn = document.createElement("button");
                acceptBtn.className = "btn btn-success";
                acceptBtn.innerHTML = times[i];
                acceptBtn.type = "submit";
                acceptBtn.addEventListener("click", () => {
                    review.bookedTime = times[i];
                    editReview(review);
                });
                td = tr.insertCell(7);
                td.insertAdjacentElement("beforeend", acceptBtn);
            }
        }
    }
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

function addColumn(data,identifier) {
    let table = document.getElementById("review-table").getElementsByTagName("tbody")[0];
    let tr = table.insertRow(table.rows.length);
    let td;

    insertTd(data.id, tr);
    insertTd(data.topic, tr);
    insertTd(data.studentLogin, tr);
    insertTd(data.mentorLogin, tr);
    insertTd(data.bookedDate, tr);
    if(identifier=="my") {
        if (data.callLink != "") {
            insertTdLink(data.callLink, tr);
        }else insertTd("", tr);
    }
    if (data.bookedTime != null) {
        insertTd(data.bookedTime.substring(0, 5), tr);
    } else insertTd(data.bookedTime, tr);



    let review = {}
    review.id = data.id;
    review.topic = data.topic;
    review.studentLogin = data.studentLogin;
    review.mentorLogin = data.mentorLogin;
    review.bookedDate = data.bookedDate;
    review.bookedTime = data.bookedTime;
    review.timeSlots = data.timeSlots;

    let times = data.timeSlots.toString().split(",");
    for (i = 0; i < times.length; i++) {
        times[i] = times[i].substring(0, 5);
    }
    times.sort();

    if (btnCase === 1) {
         if (editReviewId == review.id) {
             review.bookedTime = editReviewBookedTime;
             editReview(review);
         }

        for (let i = 0; i < times.length; i++) {
            let sumId = "#lastColumn";
            $(sumId).append("<th scope=\"col\" id=\"lastColumn" + i + "\">  </th>");
            let acceptBtn = document.createElement("button");
            acceptBtn.className = "btn btn-success";
            acceptBtn.innerHTML = times[i];
            acceptBtn.type = "submit";
            acceptBtn.addEventListener("click", () => {
                review.bookedTime = times[i];
                initCall(review);
            });
            td = tr.insertCell(6);
            td.insertAdjacentElement("beforeend", acceptBtn);
        }
    }

    if (btnCase === 2) {
        let deleteBtn = document.createElement("button");
        deleteBtn.className = "btn btn-danger";
        deleteBtn.innerHTML = "Отменить";
        deleteBtn.type = "submit";
        if (new Date(data.bookedDate + " " + data.bookedTime) < Date.now()) {
            deleteBtn.disabled = true;
        }
        deleteBtn.addEventListener("click", () => {
            deleteReview(data.id);
        });
        td = tr.insertCell(7);
        td.insertAdjacentElement("beforeend", deleteBtn);
    }
}

function insertTd(value, parent) {
    let element = document.createElement("td");
    element.scope = "row";
    element.innerText = value;
    parent.insertAdjacentElement("beforeend", element)
}


function newReviewRequests() {
    btnCase = 1;
    let reviewFilterDTO = {};
    reviewFilterDTO.bookedDate = null;
    reviewFilterDTO.mentorLogin = null;
    reviewFilterDTO.studentLogin = null;
    findReview(reviewFilterDTO, "new");
}

function myReview() {
    btnCase = 2;
    let reviewFilterDTO = {};
    reviewFilterDTO.bookedDate = null;
    reviewFilterDTO.mentorLogin = mentor;
    reviewFilterDTO.studentLogin = null;
    findReview(reviewFilterDTO, "my");
}

function todayReview() {
    btnCase = 1;
    let reviewFilterDTO = {};
    reviewFilterDTO.mentorLogin = null;
    reviewFilterDTO.studentLogin = null;
    reviewFilterDTO.bookedDate = today;
    findReview(reviewFilterDTO, "today");
}

function tomorrowReview() {
    btnCase = 1;
    let reviewFilterDTO = {};
    reviewFilterDTO.mentorLogin = null;
    reviewFilterDTO.studentLogin = null;
    reviewFilterDTO.bookedDate = tomorrow;
    findReview(reviewFilterDTO, "tomorrow");
}

function deleteReview(id) {
    let confirmation = confirm("Вы уверены, что хотите отменить ревью " + id + "?");
    if (confirmation === true) {
        $.ajax({
            url: '/reviews?id=' + id,
            method: 'DELETE',
            success: function () {
                console.log('deleted')
            },
            error: function (error) {
                console.log(error);
            }
        })
        location.reload();
    }
}

function initCall(reviewDTO) {

    let confirmation = confirm("Вы уверены, что хотите взять ревью " + reviewDTO.id +
        + " и назначить его время на " + reviewDTO.bookedTime + "?");

    let clientVkId = getVkAppId(); //заменить на id созданного standalone-приложения в ВК
    let redirectUri = "http://localhost:8000/reviews?editReview=" + reviewDTO.id + "." + reviewDTO.bookedTime;

    if (confirmation === true) {
        window.location.replace("https://oauth.vk.com/authorize?" +
            "client_id=" + clientVkId + "&" +
            "display=page" + "&" +
            "response_type=token" + "&" +
            "v=5.131" + "&" +
            "redirect_uri=" + redirectUri);
    }
}

function getVkAppId() {

    let vkAppId;

    $.ajax({
        url: '/reviews/vkAppId',
        method: 'GET',
        async: false,
        success: function (id) {
            vkAppId = id;
        }
    });

    return vkAppId;
}

function parseAnchorIfExist() {

    let actorObj = {};
    let anchor = window.location.hash;

    console.log("Review id: " + editReviewId);
    console.log("Review booked time: " + editReviewBookedTime);

    if (anchor === "" || editReviewId === "") {
        console.log("Anchor or reviewId not found");
        return;
    }

    //достаем anchor и парсим в нужный вид
    anchor.substring(1).split("&").forEach(element => {
        let couple = element.split("=");
        actorObj[parseToActorField(couple[0])] = couple[1];
    });

    console.log(JSON.stringify(actorObj));
    console.log("Access token: " + actorObj.accessToken);

    if (actorObj.accessToken != undefined) {
        createCall(actorObj, editReviewId);
    }
}

function parseUrlParams(param) {

    let urlParams = new URLSearchParams(window.location.search);
    let paramsString = urlParams.get("editReview");

    console.log("Params string: " + paramsString);

    if (paramsString == undefined) { return; }

    let paramsArray = paramsString.split(".");

    switch(param) {
        case "reviewId":
            return paramsArray[0];
        case "reviewBookedTime":
            return paramsArray[1];
        default:
            return console.log("Такого параметра не сущетсвует");
    }
}

function parseToActorField(str) {

    let strSubs = str.split("_");

    for(let i = 1; i < strSubs.length; i++) {
        strSubs[i] = strSubs[i][0].toUpperCase() + strSubs[i].substring(1);
    }

    return strSubs.join("");
}

function createCall(actorObj, reviewId) {

    $.ajax({
        url: '/reviews/createVkCall?reviewId=' + reviewId,
        method: 'PATCH',
        contentType: 'application/json',
        data: JSON.stringify(actorObj),
        async: false,
        success: function () {
            console.log('Call created');
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function editReview(reviewDTO) {

        $.ajax({
            url: '/reviews',
            method: 'PATCH',
            contentType: 'application/json',
            data: JSON.stringify(reviewDTO),
            async: false,
            success: function () {
                console.log('accepted')
            },
            error: function (error) {
                console.log(error);
            }
        })
        window.location.replace("/reviews");
}

