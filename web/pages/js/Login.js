function login() {
    var username = $("#username").val();
    var params = {
        username: username
    };

    // $.ajax({
    //     type: "POST",
    //     url: "/pages/login",
    //     data: params,
    //     success: function (data) {
    //         alert("barak");
    //         // window.location.replace("/pages/welcome");
    //     },
    //     dataType: "json"
    // });

    $.post("/pages/login", function (data) {
        alert(username);
    });
}