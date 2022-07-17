getAllProjectMessages();

function getAllProjectMessages() {
    let PrLessons = {}
    PrLessons.taskIdentifier = window.location.pathname;
    $.ajax({
        type: 'POST',
        url: '/preProjectLink/current',
        contentType: "application/json",
        data: JSON.stringify(PrLessons),
        success: function (response) {
            drawLines(response)
            console.log(response);
        },
        error: function (err) {
            console.log(err);
        }
    });
}

function drawLines(data) {

    for (let i = 0; i < data.length; i++) {
        if (data[i].viewed == true && data[i].approve == false) {
            drawList(data[i]);
        } else if (data[i].approve == true) {
            let ol = document.getElementById("messages");
            ol.insertAdjacentHTML("beforeend", '<div class="p-3 mb-2 bg-success text-white">Ты справился!</div>')
        } else if (data[i].viewed == false) {
            let ol = document.getElementById("messages");
            ol.insertAdjacentHTML("beforeend", '<div class="p-3 mb-2 bg-secondary text-white">Ментор еще не посмотрел твой код</div>')
        }
    }
}

function drawList(data) {
    let ol = document.getElementById("messages");
    for (i = 0; i < data.comments.length; i++) {
        ol.insertAdjacentHTML("beforeend", ` <li class="list-group-item">"${data.comments[i]}"</li>`)
    }

    let approveBtn = document.createElement("button");
    approveBtn.className = "btn btn-warning";
    approveBtn.innerHTML = "Исправил все правки!";
    approveBtn.type = "submit";
    ol.insertAdjacentElement('beforeend', approveBtn);

    approveBtn.onclick = function () {
        let PrLessons = {}
        PrLessons.id = data.id;
        PrLessons.comments = data.comments;
        PrLessons.approve = data.approve
        PrLessons.viewed = data.view;
        $.ajax({
            type: 'PATCH',
            url: '/preProjectLink',
            contentType: "application/json",
            data: JSON.stringify(PrLessons),
            success: function (response) {
                console.log(response);
                window.location.reload();
            },
            error: function (err) {
                console.log(err);
            }
        });

    };
}

