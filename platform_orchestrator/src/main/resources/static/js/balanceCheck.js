window.onload = initBalance();

function initBalance() {

    $.ajax({
        method: "POST",
        url: "/notification/code",
        dataType: "json",
        async: "false",
        success: function (data) {
            document.getElementById("codeCallSecurity")
                .setAttribute("value", data.codeCallSecurity);
        }
    });

    $.ajax({
        method: "GET",
        url: "/notification/balance",
        dataType: "json",
        async: "false",
        success: function (data) {
            document.getElementById("balanceText")
                .setAttribute("value", data.balance);
            document.getElementById("linkToReplenishBalance")
                .setAttribute("href", data.urlToReplenishBalance);
        }
    });
}