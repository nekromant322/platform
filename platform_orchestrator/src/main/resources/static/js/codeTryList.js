window.onload = drawColumns()

function openCode(code) {
    let element = document.getElementById("modalCodeTryBody")
    element.innerText = code;
}

function insertTd(value, parent) {
    let element = document.createElement("td");
    element.scope = "row";
    element.innerText = value;
    parent.insertAdjacentElement("beforeend", element)
}

function addColumn(data) {
    let table = document.getElementById("codeTryTable").getElementsByTagName("tbody")[0];
    let tr = table.insertRow(table.rows.length);
    let td;

    insertTd(data.user.login, tr);
    insertTd(data.id, tr);
    insertTd(data.date.toLocaleString(), tr);
    insertTd(data.codeExecutionStatus, tr)

    let updateBtn = document.createElement("button");

    if (data.codeExecutionStatus == "OK") {
        updateBtn.className = "btn btn-success";
    } else
        updateBtn.className = "btn btn-warning";
    updateBtn.innerHTML = "Open";
    updateBtn.type = "button";
    updateBtn.setAttribute("data-bs-toggle", "modal");
    updateBtn.setAttribute("data-bs-target", "#myCodeTryModal");
    updateBtn.addEventListener("click", () => {
        openCode(data.studentsCode);
    });
    td = tr.insertCell(4);
    td.insertAdjacentElement("beforeend", updateBtn);
}

function drawColumns() {
    let codeTryList;

    if (sessionStorage.getItem("admin") == true) {
        let userID = sessionStorage.getItem("id");
        $.ajax({
            method: 'GET',
            url: "/codeTry/" + userID,
            contentType: "application/json; charset=utf-8",
            async: false,
            success: function (result) {
                codeTryList = result
            },
            error: function (error) {
                console.log(error);
            }
        });
    } else {
        let chapter = sessionStorage.getItem("chapter");
        let step = sessionStorage.getItem("step");
        let lesson = sessionStorage.getItem("lesson");
        $.ajax({
            method: 'GET',
            url: "/codeTry?chapter=" + chapter + "&step=" + step + "&lesson=" + lesson,
            contentType: "application/json; charset=utf-8",
            async: false,
            success: function (result) {
                codeTryList = result
            },
            error: function (error) {
                console.log(error);
            }
        });
    }
    while (document.getElementById("codeTryTable").getElementsByTagName("tbody")[0].rows[0])
        document.getElementById("codeTryTable").getElementsByTagName("tbody")[0].deleteRow(0);
    for (let i = 0; i < codeTryList.length; i++) {
        addColumn(codeTryList[i]);
    }
}