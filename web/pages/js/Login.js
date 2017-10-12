// function login() {
//     var username = $("#username").val();
//     var params = {
//         username: username
//     };
//
//     $.ajax({
//         type: "POST",
//         url: "/pages/login",
//         data: params,
//         statusCode: {
//             200: function (response) {
//                 window.location.replace("/pages/lobby.html?username=" + params.username);
//             },
//             400: function (response) {
//                 alert("The name is already in use");
//             },
//             202: function (response) {
//                 alert("Invalid name");
//                 window.location.replace("");
//             }
//         }
//     });
// }

$(document).on('submit', function (e) {
    var username = $("#username").val();
    var params = {
        username: username
    };

    $.ajax({
        type: "POST",
        url: "/pages/login",
        data: params,
        statusCode: {
            200: function (response) {
                window.location.replace("/pages/lobby.html?username=" + params.username);
            },
            400: function (response) {
                alert("The name is already in use");
            },
            202: function (response) {
                alert("Invalid name");
            }
        }
    });
    e.preventDefault();
});

function checkLoggedIn() {
    $.ajax({
        type: "GET",
        url: "/pages/login",
        statusCode: {
            200: function (response) {
                alert("Welcome Back " + response)
                window.location.replace("/pages/lobby.html?username=" + response);
            },
            201: function (response) {

            }
        }
    });
}

checkLoggedIn();