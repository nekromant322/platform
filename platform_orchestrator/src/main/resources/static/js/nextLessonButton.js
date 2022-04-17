$('#nextLesson').click(function () {
    let lesson = window.location.href.substring(30).replaceAll("/", "-");
    $.ajax({
        url: '/lessonProgress/' + lesson,
        method: 'POST'
    })
})