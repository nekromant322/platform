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
            var html = ""
            for (var i = 0; i < navbar.length; i++) {
                html += "<li class=\"nav-item\">\n" + "<a class=\"nav-link\" href=\"" + navbar[i].url + "\">"
                    + navbar[i].text + "</a>\n</li>";
            }
            html += "<li class=\"nav-item\">\n<button class=\"button__logout\" onclick=\"logout()\">Выйти</button>\n</li>";
            document.getElementById("navbar").innerHTML = html
        }
    });
}
