function sendCode(editor) {
    const url = window.location.href.split("/");
    let codeTry = {};
    codeTry.taskIdentifier = {};
    codeTry.taskIdentifier.chapter = url[url.length - 3];
    codeTry.taskIdentifier.step = url[url.length - 2];
    codeTry.taskIdentifier.lesson = url[url.length - 1];
    //codeTry.theme = url[url.length - 4];
    codeTry.studentsCode = editor.getValue();
    $.ajax({
        method: 'POST',
        url: "/codeTry",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(codeTry),
        success: function (result) {
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function getTaskTextAndCodeDTO() {
    let taskTextAndCodeDTO = {};
    taskTextAndCodeDTO.taskText = document.getElementById("taskText").innerHTML;
    taskTextAndCodeDTO.code = editor.getValue();
    console.log(taskTextAndCodeDTO);
    $.ajax({
        method: "POST",
        url: "/helpMe",
        contentType: "application/json",
        data: JSON.stringify(taskTextAndCodeDTO),
        success: function(data) {
            console.log("posted success");
            console.log(data);
        },
    });

}