function sendCode(editor) {
    const code = editor.getValue();
    $.ajax({
        method: 'POST',
        url: getUrlForSend(),
        contentType: "application/json; charset=utf-8",
        data: code,
        success: function (result) {
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function getUrlForSend() {
    const url = window.location.href.split("/");
    const theme = url[url.length - 4];
    const chapter = url[url.length - 3];
    const step = url[url.length - 2];
    const lesson = url[url.length - 1];
    return "/lessons/" + theme + "/" + chapter + "/" + step + "/" + lesson;
}