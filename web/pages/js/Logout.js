function logout() {
    $.ajax({
        type: "POST",
        url: "/pages/logout",
        success: function (data) {
            alert("Logged out");
            window.location.replace("/index.html");
        },
        error: function (response) {
            alert("The server had a problem. Try again.");
        }
    })
}