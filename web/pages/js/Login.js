function login() {
    var username = $("#username").val();
    var params = {
        username: username
    };

    $.ajax({
        type: "POST",
        url: '/pages/login',
        data: params,
        success: function () {
            //Redirect to the welcome page.
        },
        dataType: 'json'
    });
}