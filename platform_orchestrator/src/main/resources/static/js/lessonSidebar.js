let allLessonList = [];

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
        success: function (data) {
            structure = data;
        },
        error: function (error) {
            console.log("Error!");
            console.log(error);
        }
    });

    window.document.getElementById("courseNameDiv").innerText = courseName;
    window.document.getElementById("sideBarBody").innerHTML = gatherText(structure, courseName);
    checkPassedLessons(structure, courseName)

}

function gatherText(structureOfCourse, courseName) {
    let text = "";
    let nameOfLesson = "";
    let fullNameOfCourse = "";
    for (let chapterKey in structureOfCourse) {
        text += ' <li className="active"> ';
        text += ` <a href="#${chapterKey}Submenu" data-toggle="collapse" aria-expanded="false" 
                  id="chapterKey-${courseName}-${chapterKey}" class="dropdown-toggle">${chapterKey}</a> \n`;
        text += ` <ul class="collapse list-unstyled" id="${chapterKey}Submenu"> \n`;
        for (let stepKey in structureOfCourse[chapterKey]) {
            text += `<li> <a href="#${chapterKey}-${stepKey}Submenu" data-toggle="collapse" aria-expanded="false" 
                      id="stepKey-${courseName}-${chapterKey}-${stepKey}" class="dropdown-toggle" >
                       ${chapterKey}.${stepKey}</a> </li>`;
            text += ` <ul class="collapse list-unstyled" id="${chapterKey}-${stepKey}Submenu"> \n`;
            for (let lessonKey in (structureOfCourse[chapterKey])[stepKey]) {
                text += '<li>';
                if (((structureOfCourse[chapterKey])[stepKey])[lessonKey] !== undefined) {
                    nameOfLesson = String(((structureOfCourse[chapterKey])[stepKey])[lessonKey]);
                    nameOfLesson = nameOfLesson.substr(0, nameOfLesson.length - 5);
                    fullNameOfCourse = courseName + "-" + chapterKey + "-" + stepKey + "-" + nameOfLesson;
                    text += `<a href="/lessons/${courseName}/${chapterKey}/${stepKey}/${nameOfLesson}"
                                id="lesson-${courseName}-${chapterKey}-${stepKey}-${nameOfLesson}">
                             ${chapterKey}.${stepKey}.${nameOfLesson}</a> \n`;
                    allLessonList.push(fullNameOfCourse);
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

function checkPassedLessons(structure, courseName) {
    $.ajax({
        method: "GET",
        url: "/lessonProgress/allStat",
        dataType: "json",
        cache: false,
        success: function (passedLessons) {
            $.each(passedLessons, function (i, passedLesson) {
                $.each(allLessonList, function (i, lesson) {
                    if (lesson === passedLesson) {
                        document.getElementById("lesson-" + passedLesson).style.color = "yellowgreen";
                    }
                })
            })
            let lessonCount;
            let passedLessonCount;
            let stepCount;
            let passedStepCount;
            let nameOfLesson;
            for (let chapterKey in structure) {
                stepCount = 0;
                passedStepCount = 0;

                for (let stepKey in structure[chapterKey]) {
                    lessonCount = 0;
                    passedLessonCount = 0;
                    stepCount++;

                    for (let lessonKey in (structure[chapterKey])[stepKey]) {
                        nameOfLesson = String(((structure[chapterKey])[stepKey])[lessonKey]);
                        nameOfLesson = nameOfLesson.substr(0, nameOfLesson.length - 5);
                        lessonCount++;

                        if (document.getElementById("lesson-" + courseName + "-" +
                            chapterKey + "-" + stepKey + "-" + nameOfLesson).style.color === "yellowgreen") {

                            passedLessonCount++;
                        }
                    }

                    if (passedLessonCount === lessonCount) {
                        document.getElementById("stepKey-" + courseName + "-" +
                            chapterKey + "-" + stepKey).style.color = "yellowgreen"
                        passedStepCount++
                    }
                }

                if (passedStepCount === stepCount) {
                    document.getElementById("chapterKey-" + courseName + "-" +
                        chapterKey).style.color = "yellowgreen"
                }
            }
        },
        error: function (error) {
            console.log("Error!");
            console.log(error);
        }
    });
}