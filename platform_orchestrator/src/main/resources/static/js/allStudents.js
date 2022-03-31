getStudents();

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
        addColumn(data[i]);
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
}

function updateToAdmin(id) {
    let data = {};
    data.id = id;
    $.ajax({
        url: '/promote-student/' + id,
        type: 'POST',
        contentType: 'application/json',
        success: function (response) {
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
