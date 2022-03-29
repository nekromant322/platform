window.onload = function () {
    getCurrentUser();
};

function getCurrentUser() {
    $.ajax({
        url: 'questions/get/current',
        type: 'GET',
        contentType: 'application/json',
        success: function (currentUser) {
            try {
                if (currentUser.authorities[0].authority === 'ROLE_ADMIN' ||
                    currentUser.authorities[1].authority === 'ROLE_ADMIN') {
                    listAllUsers();
                }
            } catch (e) {
                $("#allUserList").hide()
                $("#tabContent").hide()
                getStudentQuestions(currentUser.login)
            }
        }
    })
}

function getStudentQuestions(studentLogin) {
    let tab = $('#forSingleUser')
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
                    '<button class="btn btn-link" data-toggle="collapse" data-target="#collapse' + studentLogin + i + '" ' +
                    'aria-expanded="true" aria-controls="collapse' + studentLogin + i + '"> Отвеченные вопросы' +
                    '</button>' +
                    '</h5>' +
                    '</div>' +
                    '<div id="collapse' + studentLogin + i + '" class="collapse" ' +
                    'aria-labelledby="heading' + studentLogin + i + '" data-parent="#accordion' + studentLogin + i + '">' +
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
        url: 'questions/users/' + studentLogin + '/' + chapter,
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


function listAllUsers() {
    var navLinkId;
    var vPillId;
    $.ajax({
        url: '/admin/getAllStudents',
        type: 'GET',
        contentType: 'application/json',
        success: function (users) {
            $("#allUserList").empty()
            $("#tabContent").empty()

            $.each(users, function (i, user) {
                navLinkId = i + 'navLink'
                vPillId = 'v-pills-' + i
                $("#allUserList").append('<a class="nav-link" id="' + navLinkId + '" data-toggle="pill" role="tab" ' +
                    'aria-controls="v-pills-' + i + '"  aria-selected="false">' + user.login + '</a>')
                $("#tabContent").append('<div class="tab-pane" id="' + vPillId + '" role="tabpanel" ' +
                    '></div>')

                $("#" + navLinkId).attr("href", "#" + vPillId)
                checkLessonStructure(user.login, vPillId)

            })
            $("#v-pills-0").addClass('active')
            $("#0navLink").attr("aria-selected", true)
            $("#0navLink").addClass('active')
        }
    })
}

function checkLessonStructure(studentLogin, tabPane) {
    let tab = $('#' + tabPane)
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
                    '<div class="card-body" >' +
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
                    '<button class="btn btn-link" data-toggle="collapse" data-target="#collapse' + studentLogin + i + '" ' +
                    'aria-expanded="true" aria-controls="collapse' + studentLogin + i + '"> Отвеченные вопросы' +
                    '</button>' +
                    '</h5>' +
                    '</div>' +
                    '<div id="collapse' + studentLogin + i + '" class="collapse" ' +
                    'aria-labelledby="heading' + studentLogin + i + '" data-parent="#accordion' + studentLogin + i + '">' +
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
                    '<button type="button" class="btn btn-primary btn-lg btn-block" data-toggle="collapse" ' +
                    'data-target="#collapseButton' + studentLogin + i + '" aria-expanded="false" aria-controls="collapseButton' + studentLogin + i + '" ' +
                    'id="button' + studentLogin + i + '">Добавить вопрос</button>' +
                    '<div class="collapse" id="collapseButton' + studentLogin + i + '">' +
                    '<div class="card card-body">' +
                    '<form id="form' + studentLogin + i + '" >' +
                    '<div class="form-group">' +
                    '<input class="form-control" id="newQuestion' + studentLogin + i + '" placeholder="Вопрос" required><br>' +
                    '<button type="submit" id="addButton' + studentLogin + i + '" class="btn btn-success">Добавить</button>' +
                    '</div>' +
                    '</form>' +
                    '</div>' +
                    '</div>' +
                    '</div></div></div><br>')

                getQuestionsByChapter(studentLogin, chapter, tableId, tabPane)

                $('#form' + studentLogin + i).submit(function () {
                    saveQuestion(null, $('#newQuestion' + studentLogin + i).val(), chapter, false, studentLogin)
                    checkLessonStructure(studentLogin, tabPane)
                })
            })
        }
    })
}

function getQuestionsByChapter(studentLogin, chapter, table, tabPane) {
    let tableId = $('#' + table)
    let tableAnswered = $('#' + table + 'Answered')
    $.ajax({
        url: 'questions/users/' + studentLogin + '/' + chapter,
        type: 'GET',
        contentType: 'application/json',
        success: function (questions) {
            $.each(questions, function (i, question) {
                if (question.answered === true) {
                    tableAnswered.append('<tr><td><div class="container-fluid"><div class="row">' +
                        '<div class="col-11"><div class="form-check form-check-inline">' +
                        '<input class="form-check-input" type="checkbox" ' +
                        'id="checkbox' + studentLogin + i + chapter.id + '" checked/>' +
                        '</div><s>'
                        + question.question +
                        '</s></div>' +
                        '<div class="col"><button type="button" id="del' + studentLogin + i + table + '"' +
                        ' class="btn btn-danger btn-sm">Удалить</button>' +
                        '</div></div></div></td></tr>')

                    $('#checkbox' + studentLogin + i + chapter.id).change(function () {
                        if (this.checked !== true) {
                            saveQuestion(question.id, question.question, question.chapter, false, studentLogin, tabPane)
                            checkLessonStructure(studentLogin, tabPane)
                        }
                    })
                } else {
                    tableId.append('<tr><td><div class="container-fluid"><div class="row">' +
                        '<div class="col-11"><div class="form-check form-check-inline">' +
                        '<input class="form-check-input" type="checkbox" id="checkbox' + studentLogin + i + chapter.id + '">' +
                        '</div>'
                        + question.question +
                        '</div>' +
                        '<div class="col"><button type="button" id="del' + studentLogin + i + table + '" ' +
                        'class="btn btn-danger btn-sm">Удалить</button>' +
                        '</div></div></div></td></tr>')

                    $('#checkbox' + studentLogin + i + chapter.id).change(function () {
                        if (this.checked) {
                            saveQuestion(question.id, question.question, question.chapter, true, studentLogin, tabPane)
                            checkLessonStructure(studentLogin, tabPane)
                        }
                    })
                }
                $('#del' + studentLogin + i + table).click(function () {
                    deleteQuestion(question.id)
                    checkLessonStructure(studentLogin, tabPane)
                })
            })
        }
    })
}

function deleteQuestion(id) {
    $.ajax({
        url: 'questions?id=' + id,
        method: 'DELETE',
        success: function () {
            console.log('deleted')
        }
    })
}

function saveQuestion(id, question, chapter, answered, login) {
    $.ajax({
        url: 'questions',
        dataType: 'json',
        method: 'PATCH',
        cache: false,
        contentType: 'application/json',
        data: JSON.stringify({
            id: id,
            question: question,
            chapter: chapter,
            answered: answered,
            login: login,
        }),
        success: function () {
            console.log('saved')
        }
    })
}