/*TODO как в javascript сделать сюда инжект названия курса? Вообще идей нет, как считывать название выбранного  курса */
window.onload = init("core");

function init(courseName) {
    console.log("Hello world");
    let structure = {};
    $.ajax({
        method: "GET",
        url: "/lessons/structureOf/" + courseName,
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
    document.getElementById("sideBarBody").innerHTML = " ";
    document.getElementById("sideBarBody").innerHTML = gatherText(structure, courseName);
}

function showStructure(structureOfCourse) {
    for (let chapterKey in structureOfCourse) {
        console.log(chapterKey);
        for (let stepKey in structureOfCourse[chapterKey]) {
            console.log('\t' + stepKey);
            for (let lessonKey in (structureOfCourse[chapterKey])[stepKey]) {
                console.log('\t\t' + ((structureOfCourse[chapterKey])[stepKey])[lessonKey]);
            }
        }
    }
}

function gatherText(structureOfCourse, courseName) {
    let text = "";
    let nameOfLesson = "";
    for (let chapterKey in structureOfCourse) {
        text += ' <li className="active"> ';
        text += ` <a href="#${chapterKey}Submenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">${chapterKey}</a> \n`;
        text += ` <ul class="collapse list-unstyled" id="${chapterKey}Submenu"> \n`;
        for (let stepKey in structureOfCourse[chapterKey]) {
            text += `<li> <a href="#${stepKey}Submenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">${stepKey}</a> </li>`;
            text += ` <ul class="collapse list-unstyled" id="${stepKey}Submenu"> \n`;
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