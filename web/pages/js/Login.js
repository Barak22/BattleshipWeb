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
            alert("Success");
            window.location.replace("/pages/welcome");
        },
        error: function (response) {
            alert("Error");
        }
    });
}