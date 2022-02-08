window.onload = initBalance();

function initBalance() {
    $.ajax({
        method: "GET",
        url: "/notification/balance",
        dataType: "text",
        async: "false",
        success: function (data) {
            document.getElementById("balanceText").setAttribute("value", data);
        }
    });

    $.ajax({
        method: "GET",
        url: "/notification/url/replenish",
        dataType: "text",
        async: false,
        success: function (data) {
            document.getElementById("linkToReplenishBalance").setAttribute("href", data);
        }
    });
}