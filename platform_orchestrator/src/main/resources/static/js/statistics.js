const dataOfHardTasks = {
    datasets: [{
        label: 'HardTask',
        backgroundColor: 'rgb(100, 99, 132)',
        borderColor: 'rgb(100, 99, 132)',
        data: getStatForHardTask(20),
    }]
};

const dataOfSteps = {
    datasets: [{
        label: 'Hardest Steps',
        backgroundColor: 'rgb(100,255,255)',
        borderColor: 'rgb(100, 99, 132)',
        data: getStatForSteps(),
    }]
};

const dataOfUsers = {
    datasets: [{
        label: 'Users',
        backgroundColor: 'rgb(255,0,255)',
        borderColor: 'rgb(100, 99, 132)',
        data: getStatForUsers(),
    }]
};

const dataOfStatus = {
    datasets: [{
        label: 'Status',
        backgroundColor: [
            'rgb(255, 99, 132)',
            'rgb(54, 162, 235)',
            'rgb(255, 205, 86)',
            'rgb(255,0,255)'
        ],
        data: getStatForStatus(),
    }]
};

const configOfHardTasks = {
    type: 'bar',
    data: dataOfHardTasks,
    options: {}
};

const configOfSteps = {
    type: 'bar',
    data: dataOfSteps,
    options: {}
};

const configOfUsers = {
    type: 'bar',
    data: dataOfUsers,
    options: {}
};

const configOfStatus = {
    type: 'bar',
    data: dataOfStatus,
    options: {}
};


var hardTaskChart = new Chart(
    document.getElementById('statsHardTaskChart'),
    configOfHardTasks
);

const stepChart = new Chart(
    document.getElementById('statsStepsChart'),
    configOfSteps
);

const usersChart = new Chart(
    document.getElementById('statsUsersChart'),
    configOfUsers
);

const statusChart = new Chart(
    document.getElementById('statsStatusChart'),
    configOfStatus
);


function getStatForHardTask(size) {
    let data;
    $.ajax({
        method: 'GET',
        url: "/codeTry/stats/hardTasks?size=" + size,
        async: false,
        success: function (response) {
            console.log(response)
            data = response;
        },
        error: function (error) {
            console.log(error);
        }
    });
    return data;
}

function getStatForSteps() {
    let data;
    $.ajax({
        method: 'GET',
        url: "/codeTry/stats/tasks",
        async: false,
        success: function (response) {
            console.log(response)
            data = response;
        },
        error: function (error) {
            console.log(error);
        }
    });
    return data;
}

function getStatForUsers() {
    let data;
    $.ajax({
        method: 'GET',
        url: "/codeTry/stats/users",
        async: false,
        success: function (response) {
            console.log(response)
            data = response;
        },
        error: function (error) {
            console.log(error);
        }
    });
    return data;
}

function getStatForStatus() {
    let data;
    $.ajax({
        method: 'GET',
        url: "/codeTry/stats/status",
        async: false,
        success: function (response) {
            console.log(response)
            data = response;
        },
        error: function (error) {
            console.log(error);
        }
    });
    return data;
}

function updateHardTaskChart(size){
    let dataset = {
        datasets: [{
            label: 'HardTask',
            backgroundColor: 'rgb(100, 99, 132)',
            borderColor: 'rgb(100, 99, 132)',
            data: getStatForHardTask(size),
        }]
    };
    hardTaskChart.data = dataset;
    hardTaskChart.update();
}