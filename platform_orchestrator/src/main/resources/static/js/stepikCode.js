function sendStepikCode(editor){

    var code = editor.getValue();
    console.log(code);

    $.ajax({
        url : "http://localhost:8000/stepikCodeTry",
        type : "POST",
        async : false,
        contentType: "text/html; charset=utf-8",
        data: JSON.stringify(code),
        success : setTimeout(getResult,3000)
    })
}

function getResult(){

    $.ajax({
        url : "http://localhost:8000/stepikCodeTry/result",
        type : "GET",
        async : false,
        contentType: "application/json",
        success : function (result){
            let json = JSON.stringify(result);
            var res = JSON.parse(json)
            if (res.submissions[0].status === "evaluation"){
                setTimeout(getResult,3000)
            } else {
                if (res.submissions[0].status === "correct"){
                    $('#result').text(res.submissions[0].status)
                } else {
                    $('#result').text(res.submissions[0].status + " : " + res.submissions[0].hint)
                }
            }
        }
    })
}
