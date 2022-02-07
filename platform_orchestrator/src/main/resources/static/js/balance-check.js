window.onload = initBalance();

function initBalance() {
    $.ajax({
        method: "GET",
        url: "/admin/rest/balance",
        dataType: "text",
        async: "false",
        success: function (data) {
            document.getElementById("balanceText").setAttribute("value", data);
        }
    });
}