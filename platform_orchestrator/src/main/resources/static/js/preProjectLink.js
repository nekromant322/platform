$('#linkButton').on('click', function upload() {
    let PrLessons = {}
    PrLessons.link = $("#link").val();
    PrLessons.taskIdentifier = window.location.pathname;
    $.ajax({
        type: 'POST',
        url: '/preProjectLink',
        contentType: "application/json",
        data: JSON.stringify(PrLessons),
        success: function (response) {
            console.log(response);
        },
        error: function (err) {
            console.log(err);
        }
    });
});