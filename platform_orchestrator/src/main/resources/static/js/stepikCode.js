function sendStepikCode(editor){

    var stepikCodeTryDTO = {};

    var code = editor.getValue();
    var step = $('#gg').attr("step");
    var attempt = $('#gg').attr("about");

    stepikCodeTryDTO.code = JSON.stringify(code);
    stepikCodeTryDTO.step = step;
    stepikCodeTryDTO.attempt = attempt;


    $.ajax({
        url : "http://localhost:8000/stepikCodeTry",
        type : "POST",
        async : false,
        contentType: "application/json",
        data: JSON.stringify(stepikCodeTryDTO),
        success : function (result){
            let json = JSON.stringify(result);
            var res = JSON.parse(result)
            console.log(res.submissions)
            if (res.submissions[0].status === "correct"){
                $('#result').text(res.submissions[0].status).attr('style', 'background-color: green; color: white')
            } else {
                $('#result').text(res.submissions[0].status + " : " + res.submissions[0].hint).attr('style', 'background-color: red; color: white')
            }
        }
    })
}

