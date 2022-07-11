getAllPayments();

//new Tablesort(document.getElementById('allPayments'));


function getAllPayments() {
    $.ajax({
        url: '/payment',
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
    while (document.getElementById("allPayments").getElementsByTagName("tbody")[0].rows[0])
        document.getElementById("allPayments").getElementsByTagName("tbody")[0].deleteRow(0);
    for (let i = 0; i < data.length; i++) {
        addColumn(data[i]);
    }
}

function addColumn(data) {
    let table = document.getElementById("allPayments").getElementsByTagName("tbody")[0];
    let tr = table.insertRow(table.rows.length);
    let td;

    insertTd(data.id, tr);
    insertTd(getFullName(data.studentName), tr);
    insertTd(data.date, tr);
    insertTd(data.studentName, tr);
    insertTd(data.sum, tr);
    insertTd(data.accountNumber, tr);
    insertTd(data.message, tr);

}

function insertTd(value, parent) {
    let element = document.createElement("td");
    element.scope = "row";
    element.innerText = value;
    parent.insertAdjacentElement("beforeend", element)
}

function getFullName(login) {

    let fullName;

    $.ajax({
        url: '/platformUser/' + login,
        type: 'GET',
        contentType: 'application/json',
        async: false,
        cache: true,

        success: function (currentUser) {
            console.log(currentUser);
            fullName = currentUser.personalData.fullName;
        }
        , error: function (error) {
            console.log(error);
        }
    });
    return fullName
}