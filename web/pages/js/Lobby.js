ajaxCalls();

function getOnlineUsers() {

    $.ajax({
        type: "GET",
        url: "/onlineUsers",
        success: function (result) {
            document.getElementById("users-list").innerHTML = result;
        },
        error: function (error) {
            alert(error);
        }
    });
}

function ajaxCalls() {
    getOnlineUsers();
}