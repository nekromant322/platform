window.onload = function () {
    getStudents();
};

function getStudents() {
    $.ajax({
        url: 'platformUsers/coursePart',
        type: 'GET',
        contentType: 'application/json',
        success: function (response) {

            if (response === "CORE") {
                document.querySelector('#coreButton').disabled = false;
            }
            if (response === "WEB") {
                document.querySelector('#coreButton').disabled = false;
                document.querySelector('#webButton').disabled = false;
            }
            if (response === "PREPROJECT") {
                document.querySelector('#coreButton').disabled = false;
                document.querySelector('#webButton').disabled = false;
                document.querySelector('#preProjectButton').disabled = false;
            }
            if (response === "PROJECT") {
                document.querySelector('#coreButton').disabled = false;
                document.querySelector('#webButton').disabled = false;
                document.querySelector('#preProjectButton').disabled = false;
            }
            if (response === "INTERVIEW") {
                document.querySelector('#coreButton').disabled = false;
                document.querySelector('#webButton').disabled = false;
                document.querySelector('#preProjectButton').disabled = false;
            }
        },
        error: function (error) {
            console.log(error);
        }
    })
}