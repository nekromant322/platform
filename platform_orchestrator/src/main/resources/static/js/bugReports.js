// $('#exampleModal').on('show.bs.modal', function (event) {
//     var button = $(event.relatedTarget) // Button that triggered the modal
//     var recipient = button.data('whatever') // Extract info from data-* attributes
//     // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
//     // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
//     var modal = $(this)
//     modal.find('.modal-title').text('New message to ' + recipient)
//     modal.find('.modal-body input').val(recipient)
// })
// window.onload = function () {
//     getUserDoc();
// };

window.onload = function () {
    getUserDoc();
    restartAllBugs();
    openModal();
};


$('body').on('input', '.input-number', function () {
    this.value = this.value.replace(/[^0-9]/g, '');
});


function createTableRow(u) {
    return `<tr id="user_table_row">
            <td>${u.id}</td>
            <td>${u.text}</td>
            <td>${u.type}</td>
            <td>
            <a  href="/bugReports/download/${u.id}" class="btn btn-info downloadBtn" >download</a>
            </td>
        </tr>`;
}

function getUserDoc() {
    $.ajax({
        method: 'GET',
        url: '/bugReports/current',
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
    while (document.getElementById("userDoc").getElementsByTagName("tbody")[0].rows[0])
        document.getElementById("userDoc").getElementsByTagName("tbody")[0].deleteRow(0);
    for (let i = 0; i < data.length; i++) {
        addColumn(data[i]);
    }
}

function addColumn(data) {
    let table = document.getElementById("userDoc").getElementsByTagName("tbody")[0];
    let tr = table.insertRow(table.rows.length);
    let td;

    insertTd(data.name, tr);
    insertTd(data.type, tr);
    insertTd(data.id, tr)

    let downloadBtn = document.createElement("button");
    downloadBtn.className = "download btn-success";
    downloadBtn.innerHTML = "Download";
    downloadBtn.type = "submit";
    downloadBtn.addEventListener("click", () => {
        downloadFile(data.id, data.name);
    });
    td = tr.insertCell(2);
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
        url: '/bugReports/download/42' + id + name,
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

// function openTabById(tab) {
//     $('.nav-tabs a[href="#' + tab + '"]').tab('show');
// }

function openModal() {
    $.ajax({
        method: 'GET',
        url: '/bugReports',
        contentType: 'application/json',
        success: function (response) {
            $('#exampleModal').modal('show')
            console.log(response);

        },
        error: function (error) {
            console.log(error);
        }
    });
}

function restartAllBugs() {
    let BugTableBody = $("#user_table_body")

    BugTableBody.children().remove();

    fetch("bugReports/current")
        .then((response) => {
            response.json().then(data => data.forEach(function (item) {
                let TableRow = createTableRow(item);
                BugTableBody.append(TableRow);
            }));
        }).catch(error => {
        console.log(error);
    });
}
