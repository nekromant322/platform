window.onload = function () {
    restartAllQuestions();
    $('.AddBtn').on('click', function (event) {
        let defaultQuestion = {
            question: $("#question").val(),
            chapter: getChapter('chapter'),
            section: getChapter('section')
        }
        $.ajax({
            method: 'PATCH',
            url: "/defaultQuestions",
            contentType: 'application/json',
            data: JSON.stringify(defaultQuestion),
            success: function (response) {
                console.log(response);
                openTabById('nav-home')
                restartAllQuestions();
            },
            error: function (error) {
                console.log(error);
            }
        })
    })
}

function restartAllQuestions() {
    let QuestionTableBody = $("#question_table_body")

    QuestionTableBody.children().remove();
    $.ajax({
        method: 'GET',
        url: '/defaultQuestions',
        contentType: 'application/json',
        success: function (response) {
            console.log(response);
            drawColumns(response);
        },
        error: function (error) {
            console.log(error);
        }
    })
}

function drawColumns(data) {
    for (let i = 0; i < data.length; i++) {
        addColumns(data[i]);
    }
}

function addColumns(data) {
    let table = document.getElementById("questions-table").getElementsByTagName("tbody")[0];
    let tr = table.insertRow(table.rows.length);

    insertTd(data.id, tr);
    insertTd(data.question, tr);
    insertTd(data.chapter, tr);
    insertTd(data.section, tr);

    let updateBtn = document.createElement("button");
    updateBtn.className = "btn btn-success";
    updateBtn.innerHTML = "редактировать";
    updateBtn.type = "submit";
    updateBtn.addEventListener("click", () => {
        $('.editQuestion #exampleModal').modal('show');
        $(".editQuestion #IDedit").val(data.id);
        $(".editQuestion #questionEdit").val(data.question);

        let radio = document.getElementById(data.chapter + "Edit");
        radio.checked = true;

        radio = document.getElementById(data.section + "Edit");
        radio.checked = true;
    });
    let delBtn = document.createElement("button");
    delBtn.className = "btn btn-success";
    delBtn.innerHTML = "удалить";
    delBtn.type = "submit";
    delBtn.addEventListener("click", () => {
        let defaultQuestion = {
            id: data.id,
            question: data.question,
            chapter: data.chapter,
            section: data.section
        }
        delModalButton(defaultQuestion)
        location.reload();
    });
    td = tr.insertCell(4);
    td.insertAdjacentElement("beforeend", updateBtn);
    td = tr.insertCell(5);
    td.insertAdjacentElement("beforeend", delBtn);
}

function insertTd(value, parent) {
    let element = document.createElement("td");
    element.scope = "row";
    element.innerText = value;
    parent.insertAdjacentElement("beforeend", element)
}

function getChapter(address) {
    let chapters = document.getElementsByName(address);
    for (let i = 0; i < chapters.length; i++) {
        if (chapters[i].checked) {
            console.log(chapters[i])
            return chapters[i].value
        }
    }
}

document.addEventListener('click', function (event) {
    if ($(event.target).hasClass('delBtn')) {
        let href = $(event.target).attr("href");
        delModalButton(href)
    }
    if ($(event.target).hasClass('editButton')) {
        let defaultQuestion = {
            id: $("#IDedit").val(),
            question: $("#questionEdit").val(),
            chapter: getChapter('chapterEdit'),
            section: getChapter('sectionEdit')
        }
        editModalButton(defaultQuestion)
        location.reload();
    }
});

function delModalButton(question) {
    $.ajax({
        method: 'DELETE',
        url: "/defaultQuestions",
        contentType: 'application/json',
        data: JSON.stringify(question.id),
        success: function (response) {
            console.log(response);
            restartAllQuestions();
        },
        error: function (error) {
            console.log(error);
        }
    })
}

function editModalButton(question) {
    $.ajax({
        method: 'PUT',
        url: "/defaultQuestions",
        contentType: 'application/json',
        data: JSON.stringify(question),
        success: function (response) {
            $('input').val('');
            $('.editQuestion #exampleModal').modal('hide');
            console.log(response);
            restartAllQuestions();
        },
        error: function (error) {
            console.log(error);
        }
    })
}

function openTabById(tab) {
    $('.nav-tabs a[href="#' + tab + '"]').tab('show');
}
