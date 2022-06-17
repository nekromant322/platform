const statisticsData = getStatistics(20)
const  salaryData = getStatForSalary();
//window.onload(getStatForSalary());
var ctxSalary = document.getElementById('salaryChart').getContext('2d');
//var ctxSalary = document.getElementById('salaryChart').getContext('2d');
const dataOfHardTasks = {
    datasets: [{
        label: 'Самые сложные задачи',
        backgroundColor: 'rgb(100, 99, 132)',
        borderColor: 'rgb(100, 99, 132)',
        data: statisticsData.hardTasks,
    }]
};

const dataOfSteps = {
    datasets: [{
        label: 'Самые сложные главы',
        backgroundColor: 'rgb(100,255,255)',
        borderColor: 'rgb(100, 99, 132)',
        data: statisticsData.hardStepRatio,
    }]
};

const dataOfUsers = {
    datasets: [{
        label: 'Ученики',
        backgroundColor: 'rgb(255,0,255)',
        borderColor: 'rgb(100, 99, 132)',
        data: statisticsData.studentsCodeTry,
    }]
};

const dataOfStatus = {
    datasets: [{
        label: 'Статус решений',
        backgroundColor: [
            'rgb(255, 99, 132)',
            'rgb(54, 162, 235)',
            'rgb(255, 205, 86)',
            'rgb(255,0,255)'
        ],
        data: statisticsData.codeTryStatus,
    }]
};
const dataOfSalary = {
        labels: salaryData.labels,
        datasets: salaryData.userStats
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

const configOfSalary = {
    type: 'line',
    label: 'Зарплаты',
    data: dataOfSalary,
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

const salaryChart = new Chart(
    document.getElementById('salaryChart').getContext('2d'),
    configOfSalary
);


function getStatForHardTask(size) {
    let data;
    $.ajax({
        method: 'GET',
        url: "/statistics/hardTasks?size=" + size,
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

function getStatistics(size) {
    let data;
    $.ajax({
        method: 'GET',
        url: "/statistics/data?size=" + size,
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
// var statSalary = getStatForSalary();
// var salaryChart = new Chart(ctxSalary, {
//     type: 'line',
//     data: {
//         labels: statSalary.labels,
//         datasets: statSalary.userStats
//     },
//     options: {
//         scales: {
//             yAxes: [{
//                 ticks: {
//                     beginAtZero: true
//                 }
//             }]
//         },
//         responsive: true,
//         interaction: {
//             intersect: false,
//             axis: 'x'
//         },
//         plugins: {
//             title: {
//                 display: true,
//                 text: "Зарплаты"
//             }
//         },
//         scales: {
//             myScale: {
//                 type: 'logarithmic',
//                 position: 'right', // `axis` is determined by the position as `'y'`
//             }
//         }
//     }
// });

function updateHardTaskChart(size){
    hardTaskChart.data = {
        datasets: [{
            label: 'Самые сложные задачи',
            backgroundColor: 'rgb(100, 99, 132)',
            borderColor: 'rgb(100, 99, 132)',
            data: getStatForHardTask(size),
        }]
    };
    hardTaskChart.update();
}

function getStatForSalary() {
    let data;
    $.ajax({
        method: 'GET',
        url: "/statistics/salaryStat",
        async: false,
        success: function (response) {
            console.log(response)
            data = response;
        },

        error: function (error) {
            console.log(error);
        }
    });
    for (let i = 0; i < data.userStats.length; i++) {
        for (let j = 0; j < data.userStats[i].data.length; j++) {
            if (data.userStats[i].data[j] === 0) {
                data.userStats[i].data[j] = "N/A";
            }
        }
    }
    console.log(data);
    return data;
}

// var statSalary = getStatForSalary();
// var salaryChart = new Chart(ctxSalary, {
//     type: 'line',
//     data: {
//         labels: statSalary.labels,
//         datasets: statSalary.userStats
//     },
//     options: {
//         scales: {
//             yAxes: [{
//                 ticks: {
//                     beginAtZero: true
//                 }
//             }]
//         },
//         responsive: true,
//         interaction: {
//             intersect: false,
//             axis: 'x'
//         },
//         plugins: {
//             title: {
//                 display: true,
//                 text: "Зарплаты"
//             }
//         },
//         scales: {
//             myScale: {
//                 type: 'logarithmic',
//                 position: 'right', // `axis` is determined by the position as `'y'`
//             }
//         }
//     }
// });
