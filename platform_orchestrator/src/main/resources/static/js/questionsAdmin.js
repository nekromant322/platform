window.onload = function () {
    listAllUsers()
    drawDefaultQuestions()


};

function listAllUsers() {
    var navLinkId;
    var vPillId;
    $.ajax({
        url: '/getAllStudents',
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
                    '<button type="button" class="btn btn-primary btn-lg btn-block" data-toggle="collapse" ' +
                    'data-target="#collapseButton' + studentLogin + i + '" aria-expanded="false"' +
                    ' aria-controls="collapseButton' + studentLogin + i + '" ' +
                    'id="button' + studentLogin + i + '">Добавить вопрос</button>' +
                    '<div class="collapse" id="collapseButton' + studentLogin + i + '">' +
                    '<div class="card card-body">' +
                    '<form id="form' + studentLogin + i + '" >' +
                    '<div class="form-group">' +
                    '<textarea class="form-control" id="newQuestion' + studentLogin + i + '" ' +
                    'placeholder="Вопрос" rows="1" maxlength="255" required></textarea><br>' +
                    '<button type="submit" id="addButton' + studentLogin + i + '" ' +
                    'class="btn btn-success">Добавить</button>' +
                    '</div>' +
                    '</form>' +
                    '</div>' +
                    '</div>' +
                    '</div></div></div><br>')

                getQuestionsByChapter(studentLogin, chapter, tableId, tabPane)

                $('#form' + studentLogin + i).submit(function (e) {
                    e.preventDefault();
                    saveQuestion(null, $('#newQuestion' + studentLogin + i).val(), chapter, false,
                        studentLogin)
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
        url: 'questions/' + studentLogin + '/' + chapter,
        type: 'GET',
        contentType: 'application/json',
        success: function (questions) {
            $.each(questions, function (i, question) {
                if (question.answered === true) {
                    tableAnswered.append('<tr><td><div class="container-fluid" id="container'
                        + studentLogin + i + table + '"><div class="row">' +
                        '<div class="form-check form-check-inline col-1" style="max-width: 0">' +
                        '<input class="form-check-input" type="checkbox" ' +
                        'id="checkbox' + studentLogin + i + chapter.id + '" checked/>' +
                        '</div>' +
                        '<div class="col-9" style="word-wrap: break-word; max-width: 1400px"><s>' + question.question +
                        '</s></div>' +
                        '<div class="col-1"><button type="button" id="edit' + studentLogin + i + table + '" ' +
                        'class="btn btn-primary btn-sm" data-toggle="collapse" ' +
                        'data-target="#collapse' + studentLogin + i + table + '" aria-expanded="false" ' +
                        'aria-controls="collapse' + studentLogin + i + table + '">Изменить</button>' +
                        '</div>' +
                        '<div class="col-1"><button type="button" id="del' + studentLogin + i + table + '"' +
                        ' class="btn btn-danger btn-sm">Удалить</button>' +
                        '</div>' +
                        '</div>' +
                        '<div class="collapse" id="collapse' + studentLogin + i + table + '">' +
                        '<div class="card card-body">' +
                        '<form id="form' + studentLogin + i + table + '" >' +
                        '<div class="form-group">' +
                        '<textarea class="form-control" id="editQuestion' + studentLogin + i + table + '" ' +
                        'placeholder="Вопрос" maxlength="255" required>' + question.question + '</textarea><br>' +
                        '<button type="submit" id="editButton' + studentLogin + i + table + '" ' +
                        'class="btn btn-success">Подтвердить</button>' +
                        '</div>' +
                        '</form>' +
                        '</div>' +
                        '</div>' +
                        '</div></td></tr>')

                    $('#checkbox' + studentLogin + i + chapter.id).change(function () {
                        if (this.checked !== true) {
                            saveQuestion(question.id, question.question, question.chapter, false,
                                studentLogin, tabPane)
                            checkLessonStructure(studentLogin, tabPane)
                        }
                    })
                } else {
                    tableId.append('<tr><td><div class="container-fluid"><div class="row">' +
                        '<div class="form-check form-check-inline col-1" style="max-width: 0">' +
                        '<input class="form-check-input" type="checkbox" ' +
                        'id="checkbox' + studentLogin + i + chapter.id + '">' +
                        '</div>' +
                        '<div class="col-9" style="word-wrap: break-word; max-width: 1400px"><p>' + question.question +
                        '</p></div>' +
                        '<div class="col-1"><button type="button" id="edit' + studentLogin + i + table + '" ' +
                        'class="btn btn-primary btn-sm" data-toggle="collapse" ' +
                        'data-target="#collapse' + studentLogin + i + table + '" aria-expanded="false" ' +
                        'aria-controls="collapse' + studentLogin + i + table + '">Изменить</button>' +
                        '</div>' +
                        '<div class="col-1"><button type="button" id="del' + studentLogin + i + table + '" ' +
                        'class="btn btn-danger btn-sm">Удалить</button>' +
                        '</div>' +
                        '</div>' +
                        '<div class="collapse" id="collapse' + studentLogin + i + table + '">' +
                        '<div class="card card-body">' +
                        '<form id="form' + studentLogin + i + table + '" >' +
                        '<div class="form-group">' +
                        '<textarea class="form-control" id="editQuestion' + studentLogin + i + table + '"' +
                        ' placeholder="Вопрос" maxlength="255" required>' + question.question + '</textarea><br>' +
                        '<button type="submit" id="editButton' + studentLogin + i + table + '" ' +
                        'class="btn btn-success">Подтвердить</button>' +
                        '</div>' +
                        '</form>' +
                        '</div>' +
                        '</div>' +
                        '</div></td></tr>')

                    $('#checkbox' + studentLogin + i + chapter.id).change(function () {
                        if (this.checked) {
                            saveQuestion(question.id, question.question, question.chapter,
                                true, studentLogin, tabPane)
                            checkLessonStructure(studentLogin, tabPane)
                        }
                    })
                }
                $('#del' + studentLogin + i + table).click(function () {
                    deleteQuestion(question.id)
                    checkLessonStructure(studentLogin, tabPane)
                })

                $('#form' + studentLogin + i + table).submit(function (e) {
                    e.preventDefault();
                    deleteQuestion(question.id)
                    saveQuestion(null, $('#editQuestion' + studentLogin + i + table).val(), chapter,
                        question.answered, studentLogin)
                    checkLessonStructure(studentLogin, tabPane)
                })
            })
        }
    })
}

function deleteQuestion(id) {
    $.ajax({
        url: 'questionsAdmin?id=' + id,
        method: 'DELETE',
        success: function () {
            console.log('deleted')
        }
    })
}

function saveQuestion(id, question, chapter, answered, login, tabPane) {
    $.ajax({
        url: 'questionsAdmin',
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
            checkLessonStructure(login, tabPane)
        }
    })
}


function findQuestions(chapter, section, table) {
    $.ajax({
        method: 'POST',
        url: '/defaultQuestions',
        contentType: 'application/json',
        async: false,
        data: JSON.stringify({
            chapter: chapter,
            section: section,
        }),
        success: function (response) {
            console.log(response);
            buildColumns(response, table);
        },
        error: function (error) {
            console.log(error);
        }
    })
}


function buildColumns(data, table) {

    for (let i = 0; i < data.length; i++) {
        addColumns(data[i], table);
    }
}

function addColumns(data, t) {
    let table = document.getElementById(t).getElementsByTagName("tbody")[0];
    let tr = table.insertRow(table.rows.length);
    insertTd(data.question, tr);

}

function insertTd(value, parent) {
    let element = document.createElement("td");
    element.scope = "row";
    element.innerText = value;
    parent.insertAdjacentElement("beforeend", element)
}

function insertTh(value, parent) {
    let element = document.createElement("th");
    element.scope = "row";
    element.innerText = value;
    parent.insertAdjacentElement("beforeend", element)
}

function clearQuestions(t) {
    while (document.getElementById(t).getElementsByTagName("tbody")[0].rows[0])
        document.getElementById(t).getElementsByTagName("tbody")[0].deleteRow(0);
}

function drawDefaultQuestions() {
    clearQuestions("core-table");

    clearQuestions("web-table");

    clearQuestions("spring-table");


    let table = document.getElementById("core-table").getElementsByTagName("tbody")[0];
    let tr = table.insertRow(table.rows.length);
    insertTh("Core 1+2", tr);
    findQuestions("core", 12, "core-table");

    tr = table.insertRow(table.rows.length);
    insertTh("Core 3", tr);
    findQuestions("core", 3, "core-table");

    tr = table.insertRow(table.rows.length);
    insertTh("Core 4", tr);
    findQuestions("core", 4, "core-table");

    tr = table.insertRow(table.rows.length);
    insertTh("Core 5", tr);
    findQuestions("core", 5, "core-table");

    tr = table.insertRow(table.rows.length);
    insertTh("Core 6", tr);
    findQuestions("core", 6, "core-table");


    table = document.getElementById("web-table").getElementsByTagName("tbody")[0];
    tr = table.insertRow(table.rows.length);
    insertTh("Web 1", tr);
    findQuestions("web", 1, "web-table");

    tr = table.insertRow(table.rows.length);
    insertTh("Web 2", tr);
    findQuestions("web", 2, "web-table");

    tr = table.insertRow(table.rows.length);
    insertTh("Web 3", tr);
    findQuestions("web", 3, "web-table");

    table = document.getElementById("spring-table").getElementsByTagName("tbody")[0];
    tr = table.insertRow(table.rows.length);
    insertTh("Spring 1", tr);
    findQuestions("spring", 1, "spring-table");

    tr = table.insertRow(table.rows.length);
    insertTh("Spring 2", tr);
    findQuestions("spring", 2, "spring-table");

    tr = table.insertRow(table.rows.length);
    insertTh("Spring 3", tr);
    findQuestions("spring", 3, "spring-table");
}

$('#core-table').on('click', 'td', function (e) {
    tableText($(this).html())
});
$('#web-table').on('click', 'td', function (e) {
    tableText($(this).html())
});
$('#spring-table').on('click', 'td', function (e) {
    tableText($(this).html())
});

function tableText(tableRow) {
    let myJSON = JSON.stringify(tableRow);
    navigator.clipboard.writeText(tableRow).then(function () {
        console.log('Async: Copying to clipboard was successful!');
    }, function (err) {
        console.error('Async: Could not copy text: ', err);
    });
    console.log(myJSON);
}
