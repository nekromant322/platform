getAllProjectLinks();
btnClickListener();

//new Tablesort(document.getElementById('allPayments'));


function getAllProjectLinks() {
    $.ajax({
        url: '/preProjectLink',
        type: 'GET',
        contentType: 'application/json',
        success: function (response) {
            drawColumns(response);
        },
        error: function (error) {
            console.log(error);
        }
    })
}

function drawColumns(data) {
    while (document.getElementById("allPreProjectLinks").getElementsByTagName("tbody")[0].rows[0])
        document.getElementById("allPreProjectLinks").getElementsByTagName("tbody")[0].deleteRow(0);
    for (let i = 0; i < data.length; i++) {
        if (data[i].viewed == false && data[i].approve == false) {
            addColumn(data[i]);
        }
    }
}

function addColumn(data) {
    let table = document.getElementById("allPreProjectLinks").getElementsByTagName("tbody")[0];
    let tr = table.insertRow(table.rows.length);
    let td;

    insertTd(data.id, tr);
    insertTd(data.login, tr);
    insertTdLink(data.link, tr);

    let approveBtn = document.createElement("button");
    approveBtn.className = "btn btn-success";
    approveBtn.innerHTML = "Approve";
    approveBtn.type = "submit";
    approveBtn.setAttribute("data-btn", "Approve");
    approveBtn.setAttribute("data-id", data.id);

    td = tr.insertCell(3);
    td.insertAdjacentElement("beforeend", approveBtn);
    td = tr.insertCell(4);


    td.insertAdjacentHTML("beforeend",
        `
            <button type="button" class="btn btn-danger finish-education" data-toggle="modal" data-target="#editModal${data.id}">
                Нужны правки
            </button>

            <div class="modal fade" id="editModal${data.id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
              <div class="modal-dialog modal-dialog-scrollable" role="document">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Что именно не так?</h5>
                    <button type="button" class="btn-close" data-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div class="modal-body">
                     <ol class="list-group list-group-numbered" id="lastComments${data.id}" data-comments ="${data.comments}">
                     <label class="form-label">Проверьте старые правки</label>
                     </ol>
                     
                    <label for="editTextArea${data.id}" class="form-label">Новые правки</label>
                    <textarea class="form-control" id="editTextArea${data.id}" rows="3"></textarea>
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal" aria-label="Close">Закрыть</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal" data-btn="edit" data-id ="${data.id}" id="sendEdit${data.id}">Отправить правки</button>                       
                  </div>
                </div>
              </div>
            </div>            
           `
    )
    if (data.comments.length != 0) {
        let ol = document.getElementById("lastComments" + data.id);
        for (i = 0; i < data.comments.length; i++) {
            ol.insertAdjacentHTML("beforeend", ` <li class="list-group-item">"${data.comments[i]}"</li>`)
        }
    } else {
        let ol = document.getElementById("lastComments" + data.id);
        ol.style.visibility = "hidden";
    }

}

function insertTd(value, parent) {
    let element = document.createElement("td");
    element.scope = "row";
    element.innerText = value;
    parent.insertAdjacentElement("beforeend", element)
}

function insertTdLink(value, parent) {
    let element = document.createElement("td");
    element.scope = "row";

    let link = document.createElement("a")
    link.setAttribute("href", value)
    link.setAttribute("target", "_blank")
    link.innerText = value;

    element.insertAdjacentElement("afterbegin", link);
    parent.insertAdjacentElement("beforeend", element)
}

function btnClickListener() {
    let table = document.getElementById("allPreProjectLinks").getElementsByTagName("tbody")[0];
    table.addEventListener("click", event => {
        const edit = event.target.dataset.btn === "edit";
        const approve = event.target.dataset.btn === "Approve";
        let id = (event.target.dataset.id)
        if (edit) {
            let comments = getAllUlValues(document.getElementById("lastComments" + id));
            sendEdit(id, document.getElementById("editTextArea" + id).value,
                comments, true, false
            )
            ;
        }
        if (approve) {
            let comments = getAllUlValues(document.getElementById("lastComments" + id));
            sendEdit(id, "", comments, true, true);
        }
    })
}

function getAllUlValues(elem) {
    let listLi = elem.querySelectorAll('li');
    let comments = new Array();
    for (i = 0; i < listLi.length; i++) {
        comments[i] = listLi[i].innerText.replace(/^.|.$/g, "");
    }
    return comments;
}

function sendEdit(id, newComments, comments, view, approve) {
    let PrLessons = {}
    PrLessons.id = id;
    PrLessons.comments = comments;
    PrLessons.comments[comments.length] = newComments.replace(" ", "");
    PrLessons.approve = approve;
    PrLessons.viewed = view;
    $.ajax({
        type: 'PATCH',
        url: '/preProjectLink',
        contentType: "application/json",
        data: JSON.stringify(PrLessons),
        success: function (response) {
            console.log(response);
            getAllProjectLinks();
        },
        error: function (err) {
            console.log(err);
        }
    });
}