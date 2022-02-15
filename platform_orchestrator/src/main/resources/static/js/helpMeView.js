window.onload = initPage();

function initPage() {
    let url = window.location.href;
    let hashKey = url.substring(url.lastIndexOf('/') + 1);
    $.ajax({
        method: "GET",
        url: "/helpMe/cache/" + hashKey, /*,*/
        contentType: "application/json",
        async: false,
        success: function (helpMeDTO) {
            if (helpMeDTO == null) {
                console.log("Recieved null, reloading page");
                window.open("/helpMe" + hashKey,"_self");
            } else {
                document.getElementById("taskText").innerHTML = helpMeDTO.taskHTML;
                editor.setValue(helpMeDTO.code);
            }
        }
    });
}