let ctxHard = document.getElementById('statsHardTaskChart').getContext('2d');

let statForHardTask = getStatForHardTask()//написать метод
let myHardTasksChart = new Chart(ctxHard, {
    type: 'bar',
    data: {
        labels: statForHardTask.labels,//подумать как маркировать
        datasets: statForHardTask.userStats//переделать
    },
    options: {
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: true
                }
            }]
        },
        title: {
            display: true,
            text: 'Топ самых сложных задач'
        },
        legend: {
            onClick: function (event, elem) {
                if (myHardTasksChart.data.datasets.length > 1) {
                    myHardTasksChart.data.datasets = myHardTasksChart.data.datasets.filter(x => x.label === elem.text)
                    myHardTasksChart.update();
                } else {
                    myHardTasksChart.data.datasets = statForHardTask.userStats
                    myHardTasksChart.update();
                }
            }
        }
    }
});

function getStatForHardTask() {
    let data;
    $.ajax({
        method: 'GET',
        url: "/codeTry/stats",
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
