const REVIEW_DAYS_OFFSET = 1;

function sendRequest() {
    let review = {};
    review.topic = $("#review-topic").val();
    review.bookedDate = $("#review-date").val();
    review.timeSlots = [];
    for (let i = 0; i <= timeSlotCounter; i++) {
        let sumId = "#review-timeSlots" + i;
        review.timeSlots.push($(sumId).val());
    }

    const differenceDates = new Date(review.bookedDate).getDate() - new Date().getDate();
    const errorDateCondition = differenceDates > REVIEW_DAYS_OFFSET || differenceDates < 0;
    const errorDateMessage = "Дата ревью должна быть не позднее сегодня или завтра и не в прошлом";

    const errorTopicCondition = String(review.topic).length < 3;
    const errorTopicMessage = "Тема не должна быть пустой. Количество символов должно быть от 3-х";

    const errorTimeCondition = String(review.timeSlots).length < 4;
    const errorTimeMessage = "Время не должно быть пустым. Формат записи h:mm с интервалом 30 мин";

    if (checkAlert("review-date", "review-date-alert", errorDateMessage, errorDateCondition)) {
        return;
    }
    if (checkAlert("review-topic", "review-topic-alert", errorTopicMessage, errorTopicCondition)) {
        return;
    }
    if (checkAlert("review-timeSlots0", "review-timeSlots0-alert", errorTimeMessage, errorTimeCondition)) {
        return;
    }

    let confirmation =
        confirm("Запросить ревью по теме: " + review.topic + " на " + review.bookedDate + " " + review.timeSlots + "?");
    if (confirmation === true) {
        $.ajax({
            method: 'PATCH',
            url: "/reviews",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(review),
            success: function (result) {
            },
            error: function (error) {
                console.log(error);
            }
        });
        alert("Ревью запрошено!");
        closeForm();
    } else {
        alert("Запрос отменён!");
        closeForm();
    }
}

$(document).ready(function() {
    $("#review-timeSlots0").timepicker({
        timeFormat: 'h:mm a',
        interval: 30,
        minTime: '0:00 am',
        maxTime: '11:30 pm',
    });
});

let timeSlotCounter = 0;
function renderAdditionalButton() {
    timeSlotCounter++;
    $("#timeContainer")
        .append("<input type=\"text\" class=\"input-group timepicker\" id=\"review-timeSlots" + timeSlotCounter + "\" name=\"timeSlots\" placeholder=\"Время ревью\" th:required=\"required\">")
        .ready(function() {
            let sumId = "#review-timeSlots" + timeSlotCounter;
            $(sumId).timepicker({
                timeFormat: 'h:mm a',
                interval: 30,
                minTime: '0:00 am',
                maxTime: '11:30 pm',
            });
        });
}

function openForm() {
    document.getElementById("formReview").style.display = "block";
}

function closeForm() {
    document.getElementById("formReview").style.display = "none";
}