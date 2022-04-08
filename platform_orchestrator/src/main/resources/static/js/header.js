window.onload = getNavbar();

function logout() {
    deleteCookie("token");
    window.location.href = "/login";

}

function getNavbar() {
    $.ajax({
        url: '/navbar',
        type: 'GET',
        contentType: 'application/json',
        async: false,
        success: function (navbar) {
            document.getElementById("navbar").innerHTML = navbar
        }
    });
}
