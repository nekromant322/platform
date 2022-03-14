function logout() {
    deleteCookie("token");
    window.location.href = "/login";
}