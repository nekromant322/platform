window.onload = init(findCourseName());

function findCourseName() {
    let locationString = window.location.href;
    console.log(locationString.split('/')[4]);
    return locationString.split('/')[4];
}

function init(courseName) {
    let structure = {};
    $.ajax({
        method: "GET",
        url: "/lessons/structureOf/" + courseName,
        async: false,
        dataType: "json",
        success: function(data) {
            structure = data;
        },
        error: function (error) {
            console.log("Error!");
            console.log(error);
        }
    });
    document.getElementById("sideBarBody").innerHTML = " ";
    document.getElementById("sideBarBody").innerHTML = gatherText(structure, courseName);
}

function gatherText(structureOfCourse, courseName) {
    let text = "";
    let nameOfLesson = "";
    for (let chapterKey in structureOfCourse) {
        text += ' <li className="active"> ';
        text += ` <a href="#${chapterKey}Submenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">${chapterKey}</a> \n`;
        text += ` <ul class="collapse list-unstyled" id="${chapterKey}Submenu"> \n`;
        for (let stepKey in structureOfCourse[chapterKey]) {
            text += `<li> <a href="#${chapterKey}-${stepKey}Submenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">${stepKey}</a> </li>`;
            text += ` <ul class="collapse list-unstyled" id="${chapterKey}-${stepKey}Submenu"> \n`;
            for (let lessonKey in stepKey) {
                text += '<li>';
                if (((structureOfCourse[chapterKey])[stepKey])[lessonKey] !== undefined) {
                    nameOfLesson = String(((structureOfCourse[chapterKey])[stepKey])[lessonKey]);
                    nameOfLesson = nameOfLesson.substr(0, nameOfLesson.length - 5);
                    text += `<a href="/lessons/${courseName}/${chapterKey}/${stepKey}/${nameOfLesson}">${nameOfLesson}</a> \n`; /*TODO почему-то /core/*/
                }
                text += '</li>';
            }
            text += '</ul>';
        }
        text += '</ul>';
        text += ' </li> ';
    }
    return text;
}