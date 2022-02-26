window.onload = init("/core");

function init(courseName) {
    console.log("Hello world");
    let structure = {};
    $.ajax({
        method: "GET",
        url: "/lessons/structureOf" + courseName, /*TODO добавить параметр для курса*/
        async: false,
        dataType: "json",
        success: function(data) {
            console.log(data);
            structure = data;
        },
        error: function (error) {
            console.log("Error!");
            console.log(error);
        }
    });
    showStructure(structure);
    // document.getElementById("sideBarBody").innerHTML = gatherText(structure);
}

function showStructure(structureOfCourse) {
    for (let chapterKey in structureOfCourse) {
        console.log(chapterKey);
        for (let stepKey in structureOfCourse[chapterKey]) {
            console.log('\t' + stepKey);
        }
    }
}

//TODO разобраться c тем, как инжектить строки в строку ( ${something}})
function gatherText(structureOfCourse) {
    let text = "";
    for (let chapterKey in structureOfCourse) {
        text += ' <li className="active"> ';
        text += ' <a href="#${chapterKey}Submenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">${chapterKey}</a> \n';
        text += ' <ul class="collapse list-unstyled" id="${chapterKey}Submenu"> \n';
        for (let stepKey in structureOfCourse[chapterKey]) {
            text += ' <li> <a href="#">${stepKey}</a> </li> '
        }
        text += ' </li> ';
    }
    return text;
}