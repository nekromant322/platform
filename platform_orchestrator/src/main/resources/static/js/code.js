let taskTextAndCodeDTO = {};

function sendCode(editor) {
    const url = window.location.href.split("/");
    let codeTry = {};
    codeTry.taskIdentifier = {};
    codeTry.taskIdentifier.chapter = url[url.length - 3];
    codeTry.taskIdentifier.step = url[url.length - 2];
    codeTry.taskIdentifier.lesson = url[url.length - 1];
    codeTry.step = $('#gg').attr("step");
    codeTry.attempt = $('#gg').attr("about");
    //codeTry.theme = url[url.length - 4];
    codeTry.studentsCode = JSON.stringify(editor.getValue());
    $.ajax({
        method: 'POST',
        url: "/codeTry",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(codeTry),
        success: function (result) {
            console.log(result)
            var res = JSON.parse(result)
            console.log(res.submissions)
            if (res.submissions[0].status === "correct"){
                $('#result').text(res.submissions[0].status).attr('style', 'background-color: green; color: white')
            } else {
                $('#result').text(res.submissions[0].status + " : " + res.submissions[0].hint).attr('style', 'background-color: red; color: white')
            }
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function initModal() {
    taskTextAndCodeDTO.taskHTML = document.getElementById("taskText").innerHTML;
    taskTextAndCodeDTO.code = editor.getValue();
    console.log(taskTextAndCodeDTO);
}

function postHelpMeDTO() {
    taskTextAndCodeDTO.question = document.getElementById("message-text").value;
    console.log(taskTextAndCodeDTO);
    $("#questionModal").modal("hide");
    $.ajax({
        method: "POST",
        url: "/helpMe",
        contentType: "application/json",
        async: false,
        data: JSON.stringify(taskTextAndCodeDTO),
        success: function (helpMeHashKey) {
            console.log(helpMeHashKey)
            copyTextToClipboard("localhost:8000/helpMe/" + helpMeHashKey);
        },
    });
}

function copyTextToClipboard(text) {
    var textArea = document.createElement("textarea");
    //
    // *** This styling is an extra step which is likely not required. ***
    //
    // Why is it here? To ensure:
    // 1. the element is able to have focus and selection.
    // 2. if the element was to flash render it has minimal visual impact.
    // 3. less flakyness with selection and copying which **might** occur if
    //    the textarea element is not visible.
    //
    // The likelihood is the element won't even render, not even a
    // flash, so some of these are just precautions. However in
    // Internet Explorer the element is visible whilst the popup
    // box asking the user for permission for the web page to
    // copy to the clipboard.
    //

    // Place in the top-left corner of screen regardless of scroll position.
    textArea.style.position = 'fixed';
    textArea.style.top = 0;
    textArea.style.left = 0;

    // Ensure it has a small width and height. Setting to 1px / 1em
    // doesn't work as this gives a negative w/h on some browsers.
    textArea.style.width = '2em';
    textArea.style.height = '2em';

    // We don't need padding, reducing the size if it does flash render.
    textArea.style.padding = 0;

    // Clean up any borders.
    textArea.style.border = 'none';
    textArea.style.outline = 'none';
    textArea.style.boxShadow = 'none';

    // Avoid flash of the white box if rendered for any reason.
    textArea.style.background = 'transparent';

    textArea.value = text;

    document.body.appendChild(textArea);
    textArea.focus();
    textArea.select();

    try {
        var successful = document.execCommand('copy');
        var msg = successful ? 'successful' : 'unsuccessful';
        console.log('Copying text command was ' + msg);
    } catch (err) {
        console.log('Oops, unable to copy');
    }

    document.body.removeChild(textArea);
}

function getMyCodeTry() {
    const url = window.location.href.split("/");
    let chapter = url[url.length - 3];
    let step = url[url.length - 2];
    let lesson = url[url.length - 1];

    sessionStorage.setItem("lesson", lesson)
    sessionStorage.setItem("step", step)
    sessionStorage.setItem("chapter", chapter)
    sessionStorage.setItem("admin", "no")
    window.location.href = "/codeTryList";
}
