window.onload = function () {
    getCurrentUser();
};

function getCurrentUser() {
    $.ajax({
        url: '/platformUsers/current',
        type: 'GET',
        contentType: 'application/json',
        success: function (currentUser) {
            getStudentQuestions(currentUser.login)
        }
    })
}

function getStudentQuestions(studentLogin) {
    let tab = $('#container')
    var tableId;
    var chapterName
    $.ajax({
        url: 'questions/chapters',
        type: 'GET',
        contentType: 'application/json',
        success: function (chapters) {
            tab.empty()
            $.each(chapters, function (i, chapter) {
                chapterName = chapter.replaceAll(' ', '')
                tableId = studentLogin + chapterName + 'table'
                tab.append('<br>' +
                    '<div class="card" style="background-color: #f8f9fa">' +
                    '<div class="card-body">' +
                    '<div class="table-responsive">' +
                    '<table class="table table-hover col">' +
                    '<thead class="table-light" >' +
                    '<tr role="row">' +
                    '<td><h5>' + chapter + '</h5></td>' +
                    '</tr>' +
                    '</thead>' +
                    '<tbody class="table-light" id="' + tableId + '">' +
                    '<tr><td><div id="accordion' + studentLogin + i + '">' +
                    '<div class="card">' +
                    '<div class="card-header" id="heading' + studentLogin + i + '">' +
                    '<h5 class="mb-0">' +
                    '<button class="btn btn-link" data-toggle="collapse" ' +
                    'data-target="#collapse' + studentLogin + i + '" ' +
                    'aria-expanded="true" aria-controls="collapse' + studentLogin + i + '"> Отвеченные вопросы' +
                    '</button>' +
                    '</h5>' +
                    '</div>' +
                    '<div id="collapse' + studentLogin + i + '" class="collapse" ' +
                    'aria-labelledby="heading' + studentLogin + i + '" ' +
                    'data-parent="#accordion' + studentLogin + i + '">' +
                    '<div class="card-body">' +
                    '<div class="table-responsive">' +
                    '<table class="table table-hover col">' +
                    '<tbody class="table" id="' + tableId + 'Answered">' +
                    '</tbody></table>' +
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '</td></tr></tbody>' +
                    '</table>' +
                    '</div>' +
                    '</div>' +
                    '</div><br>')

                getQuestionsForStudent(studentLogin, chapter, tableId)
            })
        }

    })
}

function getQuestionsForStudent(studentLogin, chapter, table) {
    let tableId = $('#' + table)
    let tableAnswered = $('#' + table + 'Answered')
    $.ajax({
        url: 'questions/my/' + chapter,
        type: 'GET',
        contentType: 'application/json',
        success: function (questions) {
            $.each(questions, function (i, question) {
                if (question.answered === true) {
                    tableAnswered.append('<tr><td><s>' + question.question + '</s></td></tr>')
                } else {
                    tableId.append('<tr><td>' + question.question + '</td></tr>')
                }
            })
        }
    })
}