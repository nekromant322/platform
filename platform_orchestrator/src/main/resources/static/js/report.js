function sendReport() {
    let report = {};
    report.text = $("#report-text").val();
    report.date = $("#report-date").val();
    const differenceDates = new Date().getTime() -  new Date(report.date).getTime();
    const condition = differenceDates > MILLIS_PER_DAY * 3 || differenceDates < 0;
    const errorDateMessage = "Дата отчета должна быть не позднее 3ех дней от сегодня и не в будущем";

    if (checkAlert("report-text", "report-text-alert")) {
        return;
    }
    if (checkAlert("report-date", "report-date-alert", errorDateMessage, condition)) {
        return;
    }

    $.ajax({
        method: 'POST',
        url: "/report",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(report),
        success: function (result) {
        },
        error: function (error) {
            console.log(error);
        }
    });
}
