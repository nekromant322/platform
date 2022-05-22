window.onload = function () {
    getAllBugs();
};

function getAllBugs() {
    $.ajax({
        method: 'GET',
        url: '/bugReports/allBugs',
        contentType: 'application/json',
        success: function (response) {
            console.log(response);
            drawColumn(response);
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function drawColumn(data) {
    while (document.getElementById("userBug").getElementsByTagName("tbody")[0].rows[0])
        document.getElementById("userBug").getElementsByTagName("tbody")[0].deleteRow(0);
    for (let i = 0; i < data.length; i++) {
        addColumn(data[i]);
    }
}

function addColumn(data) {
    let table = document.getElementById("userBug").getElementsByTagName("tbody")[0];
    let tr = table.insertRow(table.rows.length);
    let td;

    insertTd(data.id, tr);
    insertTd(data.name, tr);
    insertTd(data.type, tr);
    insertTd(data.user, tr);
    insertTd(data.text, tr)

    let downloadBtn = document.createElement("button");
    downloadBtn.className = "btn btn-success";
    downloadBtn.innerHTML = "Download";
    downloadBtn.type = "submit";
    downloadBtn.addEventListener("click", () => {
        downloadFile(data.id, data.name);
    });
    td = tr.insertCell(5);
    td.insertAdjacentElement("beforeend", downloadBtn);
}

function insertTd(value, parent) {
    let element = document.createElement("td");
    element.scope = "row";
    element.innerText = value;
    parent.insertAdjacentElement("beforeend", element);
}

function downloadFile(id, name) {
    $.ajax({
        url: '/bugReports/download/' + id,
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