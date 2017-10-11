function login() {
    var username = $("#username").val();
    var params = {
        username: username
    };

    $.ajax({
        type: "POST",
        url: "/pages/login",
        data: params,
        success: function (data) {
            window.location.replace("/pages/lobby.html?username=" + params.username);
        },
        error: function (response) {
            if (response.status === 500) {
                alert("Invalid name");
            } else {
                alert("The name is already in use");
            }
            window.location.replace("");
        }
    });
}

function checkCookie() {
    $.ajax({
        type: "GET",
        url: "/pages/login",
        success: function (data) {
            window.location.replace("/pages/lobby.html");
        }
    });
}

checkCookie();