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
        if (data[i].studyStatus === "ACTIVE" && data[i].authorities[0].authority !== "ROLE_GRADUATE") {
            addColumn(data[i]);
        }
    }
    for (let i = 0; i < data.length; i++) {
        if (data[i].authorities[0].authority === "ROLE_GRADUATE" && data[i].studyStatus !== "BAN") {
            addColumn(data[i]);
        }
    }
    for (let i = 0; i < data.length; i++) {
        if (data[i].studyStatus === "BAN") {
            addColumn(data[i]);
        }
    }
}

function addColumn(data) {
    let table = document.getElementById("requests-table").getElementsByTagName("tbody")[0];
    let tr = table.insertRow(table.rows.length);
    let td;

    let color;
    color = 'orange';
    let legend;
    legend = "CORE";

    if (data.coursePart === "WEB") {
        color = 'blue';
        legend = "WEB";
    }

    if (data.coursePart === "SPRING") {
        color = 'green';
        legend = "SPRING";
    }

    insertTd(data.id, tr);
    insertTd(data.login, tr);

    let updateBtn = document.createElement("button");
    updateBtn.className = "btn btn-success";
    updateBtn.innerHTML = "Повысить";
    updateBtn.type = "submit";

    let update = document.createElement("button");
    update.className = "btn btn-success";
    update.innerHTML = legend;
    update.title = legend;
    update.style.backgroundColor = color;
    update.type = "submit";
    if (data.authorities[0].authority === "ROLE_GRADUATE") {
        document.getElementsByClassName("finish-education").disable = true;
    }
    if (data.studyStatus === "BAN") {
        updateBtn.disabled = true;
    }
    updateBtn.addEventListener("click", () => {
        updateUserRole(data.id, "ADMIN");
    });
    td = tr.insertCell(2);
    td.insertAdjacentElement("beforeend", update);
    td = tr.insertCell(3);
    td.insertAdjacentElement("beforeend", updateBtn);
    td = tr.insertCell(4);
    // td.insertAdjacentElement("beforeend", update);
    // td = tr.insertCell(4);


    // td = tr.insertCell(3);
    // td.insertAdjacentElement("beforeend", update);
    // td = tr.insertCell(4);

    if (data.studyStatus === "ACTIVE" && data.authorities[0].authority === "ROLE_USER") {


        update.addEventListener("click", () => {
            if(data.coursePart === "CORE") {
                updateCurrentCoursePart(data.id, "WEB");
            }  else {
                updateCurrentCoursePart(data.id, "SPRING");
            }
        });
        td.insertAdjacentHTML("beforeend",
            `
            <button type="button" class="btn btn-primary finish-education" data-toggle="modal" data-target="#aceptModal${data.id}">
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
                    <button type="button" class="btn btn-primary" data-dismiss="modal" data-btn="ban" data-id = "${data.id}" >Забанить</button>
                  </div>
                </div>
              </div>
            </div>            
           `
        )
    }
    // td.insertAdjacentElement("beforeend", update);
    // td = tr.insertCell(4);
    if (data.authorities[0].authority === "ROLE_GRADUATE") {
        let status = document.createElement("span");
        status.innerHTML = "GRADUATE ";
        td.insertAdjacentElement("beforeend", status);
    }
    if (data.studyStatus === "BAN") {
        let status = document.createElement("span");
        status.innerHTML = data.studyStatus;
        td.insertAdjacentElement("beforeend", status);
    }
    ;


}

// function addColumn(data) {
//     let table = document.getElementById("requests-table").getElementsByTagName("tbody")[0];
//     let tr = table.insertRow(table.rows.length);
//     let td;
//
//     insertTd(data.id, tr);
//     insertTd(data.login, tr);
//
//
//     let update = document.createElement("button");
//     update.className = "btn btn-success";
//     update.innerHTML = "Перейти";
//     update.type = "submit";
//
//     update.addEventListener("click", () => {
//         updateCurrentCoursePart(data.id,"Spring");
//     });
//     td = tr.insertCell(3);
//     td.insertAdjacentElement("beforeend", update);
//     td = tr.insertCell(4);
//
//     if (data.studyStatus === "ACTIVE" && data.authorities[0].authority === "ROLE_USER") {
//
//
//
//         // update.addEventListener("click", () => {
//         //     updateCurrentCoursePart(data.id, "Spring");
//         // });
//         td.insertAdjacentHTML("beforeend",
//             `
//             <button type="button" class="btn btn-primary finish-education" data-toggle="modal" data-target="#aceptModal${data.id}">
//                 Завершить обучение
//             </button>
//
//             <div class="modal fade" id="aceptModal${data.id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
//               <div class="modal-dialog" role="document">
//                 <div class="modal-content">
//                   <div class="modal-header">
//                     <h5 class="modal-title" id="exampleModalLabel">Устроился на работу?</h5>
//                     <button type="button" class="btn-close" data-dismiss="modal" aria-label="Close"></button>
//                   </div>
//                   <div class="modal-footer">
//                     <button type="button" class="btn btn-secondary" data-dismiss="modal" data-btn="working" data-id = "${data.id}">Устроился</button>
//                     <button type="button" class="btn btn-primary" data-dismiss="modal" data-btn="ban" data-id = "${data.id}" >Забанить</button>
//                   </div>
//                 </div>
//               </div>
//             </div>
//            `
//         )
//     }

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
        const ban = event.target.dataset.btn === "ban";
        if (working) {
            updateUserRole(event.target.dataset.id, "GRADUATE");
            getStudents();
        }
        if (ban) {
            setWorkStatus(event.target.dataset.id, "BAN");
            getStudents();
        }
    });
}

function updateUserRole(id, status) {
    let confirmation = confirm("Вы уверены, что хотите повысить студента до " + status + " ?");
    if (confirmation === true) {
        let data = {};
        data.id = id;
        $.ajax({
            url: '/promote-student/' + id + '/' + status,
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
}

function updateCurrentCoursePart(id, coursePart) {
    let confirmation = confirm("Вы уверены, что хотите перевести пользователя на этап " + coursePart + " ?");
    if (confirmation === true) {
        let data = {};
        data.id = id;
        $.ajax({
            url: '/levelUp/' + id + '/' + coursePart,
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
}

function insertTd(value, parent) {
    let element = document.createElement("td");
    element.scope = "row";
    element.innerText = value;
    parent.insertAdjacentElement("beforeend", element)
}
