window.onload = getAllRequests();

function getAllRequests() {
    $.ajax({
        url: '/join/request',
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
    updateBtn.innerHTML = "Accept";
    updateBtn.type = "submit";
    updateBtn.addEventListener("click", () => {
        acceptRequest(data.id);
    });
    td = tr.insertCell(2);
    td.insertAdjacentElement("beforeend", updateBtn);
}

function acceptRequest(id) {
    let data = {};
    data.id = id;
    $.ajax({
        url: '/join/request/accept/' + id,
        type: 'POST',
        contentType: 'application/json',
        success: function (response) {
            getAllRequests();
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
