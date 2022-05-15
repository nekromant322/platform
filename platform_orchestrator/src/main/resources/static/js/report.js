const REPORT_DAYS_OFFSET = 3;

function sendReport() {
    let report = {};
    report.text = $("#report-text").val();
    report.date = $("#report-date").val();
    report.hours = $("#report-hours").val();
    const differenceDates = new Date().getTime() - new Date(report.date).getTime();

    const errorDateCondition = differenceDates > MILLIS_PER_DAY * REPORT_DAYS_OFFSET || differenceDates < 0;
    const errorHoursCondition = report.hours < 0 || report.hours > 23;

    const errorDateMessage = "Дата отчета должна быть не позднее 3ех дней от сегодня и не в будущем";
    const errorHoursMessage = "Кол-во часов должно быть больше 0 и меньше 24";

    if (checkAlert("report-text", "report-text-alert")) {
        return;
    }
    if (checkAlert("report-date", "report-date-alert", errorDateMessage, errorDateCondition)) {
        return;
    }
    if (checkAlert("report-hours", "alert-report-hours", errorHoursMessage, errorHoursCondition)) {
        return;
    }

    $.ajax({
        method: 'POST',
        url: "/report",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(report),
        success: function (result) {
            successHandle(result);
        },
        error: function (error) {
            errorHandle(error);
        }
    });
}


function errorHandle(error) {
    if (error.status === 409) {
        let item = document.getElementById("report-date");
        try {
            document.getElementById("report-date-alert").remove();
        } catch (e) {
        }
        item.classList.remove('invalid');
        let alert = document.createElement("p");
        alert.innerText = "Отчет на эу дату уже существует";
        alert.className = "alert_logo";
        alert.role = "alert";
        alert.id = "report-date-alert"
        item.insertAdjacentElement('beforebegin', alert);
    }
}

function successHandle(response) {
    console.log(response);
}

document.getElementById('datePicker').valueAsDate = new Date();