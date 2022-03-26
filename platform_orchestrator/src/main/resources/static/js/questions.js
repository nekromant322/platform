window.onload = checkUser()

function checkUser() {
    $.ajax({
        url: 'chapters',
        type: 'GET',
        contentType: 'application/json',
        success: function (chapters) {
            $("#lessonStructure").empty()
            $.each(chapters, function (i, chapter) {
                $("#lessonStructure").append('<td>' + chapter + '</td>')
            })
        }
    })
}