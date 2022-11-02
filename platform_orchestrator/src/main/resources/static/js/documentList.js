window.onload = getUserDoc()

function getUserDoc() {
    let userID = sessionStorage.getItem("id");
    $.ajax({
        method: 'GET',
        url: '/document/documentList/' + userID,
        contentType: 'application/json',
        success: function (response) {
            console.log(response);
            drawColumns(response);
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function drawColumns(data) {
    while (document.getElementById("documentTable").getElementsByTagName("tbody")[0].rows[0])
        document.getElementById("documentTable").getElementsByTagName("tbody")[0].deleteRow(0);
    for (let i = 0; i < data.length; i++) {
            addColumn(data[i]);
    }
}

function addColumn(data) {
    let table = document.getElementById("documentTable").getElementsByTagName("tbody")[0];
    let tr = table.insertRow(table.rows.length);
    let td;


    insertTd(data.name, tr);
    insertTd(data.date.toLocaleString(), tr);

    let delBtn = document.createElement("button");
    delBtn.className = "btn btn-danger";
    delBtn.innerHTML = "Delete";
    delBtn.type = "submit";
    delBtn.style = "float: right";
    delBtn.addEventListener("click", () => {
        remove(data.id);
    });

    td = tr.insertCell(2);
    td.insertAdjacentElement("beforeend", delBtn);

    let downloadBtn = document.createElement("button");
    downloadBtn.className = "btn btn-success";
    downloadBtn.innerHTML = "Download";
    downloadBtn.type = "submit";
    downloadBtn.style = "float: left";
    downloadBtn.addEventListener("click", () => {
        downloadFile(data.id, data.name);
    });
    td = tr.insertCell(3);
    td.insertAdjacentElement("beforeend", downloadBtn);

}

function downloadFile(id, name) {
    $.ajax({
        url: '/document/download/' + id,
        dataType: 'binary',
        xhrFields: {
            'responseType': 'blob'
        },
        success: function (data, status, xhr) {
            var blob = new Blob([data], {type: xhr.getResponseHeader('Content-Type')});
            var link = document.createElement('a');
            link.href = window.URL.createObjectURL(blob);
            link.download = '' + name;
            link.click();
        }
    });
}

function remove(id) {
    $.ajax({
        url: '/document/' + id,
        type: 'DELETE',
        contentType: 'application/json',
        success: function () {
            location.reload()
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
    parent.insertAdjacentElement("beforeend", element);
}



