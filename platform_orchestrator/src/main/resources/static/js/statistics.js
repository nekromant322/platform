const statisticsData = getStatistics(20)
const salaryData = getStatForSalary();
const incomeFromUsers = getIncomeFromUsers();
const generalIncome = getGeneralIncome();

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
        label: 'Количество дней',
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
    datasets: salaryData.salaryStats
};

const dataOfIncomeFromUsers = {
    labels: incomeFromUsers.studentName,
    datasets: [{
        label: 'Доходы от каждого студента',
        backgroundColor: 'rgb(67,0,255)',
        borderColor: 'rgb(100, 100, 130)',
        data: incomeFromUsers.income,
    }]
};

const dataOfGeneralIncome = {
    labels: generalIncome.dataMonth,
    datasets: [{
        label: 'Доходы от менторинга по месяцам',
        backgroundColor: 'rgb(100,200,0)',
        borderColor: 'rgb(100, 100, 130)',
        data: generalIncome.income,
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

const configOfSalary = {
    type: 'line',
    label: 'Зарплаты',
    data: dataOfSalary,
    options: {}
};

const configOfIncomeFromUsers = {
    type: 'bar',
    data: dataOfIncomeFromUsers,
    options: {}
};

const configOfGeneralIncome = {
    type: 'line',
    data: dataOfGeneralIncome,
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
    document.getElementById('salaryChart'),
    configOfSalary
);

const incomeFromUsersChart = new Chart(
    document.getElementById('incomeFromUsersChart'),
    configOfIncomeFromUsers
);

const generalIncomeChart = new Chart(
    document.getElementById('generalIncomeChart'),
    configOfGeneralIncome
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

function updateHardTaskChart(size) {
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
        url: "/statistics/salary",
        async: false,
        success: function (response) {
            console.log(response)
            data = response;
        },

        error: function (error) {
            console.log(error);
        }
    });
    for (let i = 0; i < data.salaryStats.length; i++) {
        for (let j = 0; j < data.salaryStats[i].data.length; j++) {
            if (data.salaryStats[i].data[j] === 0) {
                data.salaryStats[i].data[j] = "N/A";
            }
        }
    }
    console.log(data);
    return data;
}

function getIncomeFromUsers() {
    let data;
    $.ajax({
        method: 'GET',
        url: "/statistics/allPayment",
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

function getGeneralIncome() {
    let data;
    $.ajax({
        method: 'GET',
        url: "/statistics/generalIncome",
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