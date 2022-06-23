getStudents();
btnClickListener();

function getStudents() {
    $.ajax({
        url: '/getAllStudents',
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
    while (document.getElementById("requests-table").getElementsByTagName("tbody")[0].rows[0])
        document.getElementById("requests-table").getElementsByTagName("tbody")[0].deleteRow(0);
    for (let i = 0; i < data.length; i++) {
        if(data[i].studyStatus === "STUDY"){
            addColumn(data[i]);
        }
    }
    for (let i = 0; i < data.length; i++) {
        if(data[i].studyStatus === "WORK"){
            addColumn(data[i]);
        }
    }
    for (let i = 0; i < data.length; i++) {
        if(data[i].studyStatus === "BAN"){
            addColumn(data[i]);
        }
    }
}

function addColumn(data) {
    let table = document.getElementById("requests-table").getElementsByTagName("tbody")[0];
    let tr = table.insertRow(table.rows.length);
    let td;

    insertTd(data.id, tr);
    insertTd(data.login, tr);

    let updateBtn = document.createElement("button");
    updateBtn.className = "btn btn-success";
    updateBtn.innerHTML = "Повысить";
    updateBtn.type = "submit";
    updateBtn.addEventListener("click", () => {
        updateToAdmin(data.id);
    });
    td = tr.insertCell(2);
    td.insertAdjacentElement("beforeend", updateBtn);
    td = tr.insertCell(3);


    if (data.studyStatus === "STUDY") {

        td.insertAdjacentHTML("beforeend",
            `
            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#aceptModal${data.id}">
                Завершить обучение
            </button>

            <div class="modal fade" id="aceptModal${data.id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
              <div class="modal-dialog" role="document">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Устроился на работу?</h5>
                    <button type="button" class="btn-close" data-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal" data-btn="working" data-id = "${data.id}">Устроился</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal" data-btn="notWorking" data-id = "${data.id}" >Не устроился</button>
                  </div>
                </div>
              </div>
            </div>            
           `
        )
    } else {
        let status = document.createElement("span");
        status.innerHTML = data.studyStatus;
        td.insertAdjacentElement("beforeend",status );
    }
    ;


}

function setWorkStatus(id, status) {
    let data = {};
    data.id = id;
    $.ajax({
        url: '/work-student/' + id + '/' + status,
        type: 'PUT',
        contentType: 'application/json',
        success: function () {
            getStudents();
        },
        error: function (error) {
            console.log(error);
        }
    })
}

function btnClickListener() {
    let table = document.getElementById("requests-table").getElementsByTagName("tbody")[0];
    table.addEventListener("click", event => {
        const working = event.target.dataset.btn === "working";
        const notWorking = event.target.dataset.btn === "notWorking";
        if (working) {
            setWorkStatus(event.target.dataset.id, "WORK");
            getStudents();
        }
        if (notWorking) {
            setWorkStatus(event.target.dataset.id, "BAN");
            getStudents();
        }
    });
}

function updateToAdmin(id) {
    let data = {};
    data.id = id;
    $.ajax({
        url: '/promote-student/' + id,
        type: 'POST',
        contentType: 'application/json',
        success: function () {
            getStudents();
        },
        error: function (error) {
            console.log(error);
        }
    })
}

function insertTd(value, parent) {
    let element = document.createElement("td");
    element.scope = "row";
    element.innerText = value;
    parent.insertAdjacentElement("beforeend", element)
}
