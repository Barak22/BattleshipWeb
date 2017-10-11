//-------------------------------------------------//
// Refreshes users and board list every 2 seconds
//-------------------------------------------------//
setInterval(ajaxCalls, 2000);

//-------------------------------------------------//
// Displays the online users list
//-------------------------------------------------//
function getOnlineUsers() {

    $.ajax({
        type: "GET",
        url: "/onlineUsers",
        success: function (result) {
            document.getElementById("users-list").innerHTML = result;
        },
        error: function (error) {
            // alert(error);
        }
    });
}

//-------------------------------------------------//
// Displays the games list
//-------------------------------------------------//
function getGames() {
    $.ajax({
        type: "GET",
        url: "/getGames",
        success: function (result) {
            document.getElementById("games-list").innerHTML = result;
        },
        error: function (error) {
            // alert(error);
        }
    });
}

//-------------------------------------------------//
// Ajax calls
//-------------------------------------------------//
function ajaxCalls() {
    getOnlineUsers();
    getGames();
}


function bs_input_file() {
    $(".input-file").before(
        function () {
            if (!$(this).prev().hasClass('input-ghost')) {
                var element = $("<input type='file' class='input-ghost' style='visibility:hidden; height:0'>");
                element.attr("name", $(this).attr("name"));
                element.change(function () {
                    element.next(element).find('input').val((element.val()).split('\\').pop());
                });
                $(this).find("button.btn-choose").click(function () {
                    element.click();
                });
                $(this).find("button.btn-reset").click(function () {
                    element.val(null);
                    $(this).parents(".input-file").find('input').val('');
                });
                $(this).find('input').css("cursor", "pointer");
                $(this).find('input').mousedown(function () {
                    $(this).parents('.input-file').prev().click();
                    return false;
                });
                return element;
            }
        }
    );
}

function checkIfUserLoggedOut() {
    $.ajax({
        type: "GET",
        url: "/pages/login",
        error: function (response) {
            window.location.replace("/index.html");
        }
    })
}

function getCookieValue(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

$(function () {
    bs_input_file();
});

//-------------------------------------------------//
// Send file and file details to servlet
//-------------------------------------------------//


function upload(file, fileName, username) {
    var formData = new FormData();
    formData.append("username", username);
    formData.append("fileName", fileName);
    formData.append("file", file);

    var xhr = new XMLHttpRequest();
    xhr.upload.addEventListener("progress", uploadProgress, false);
    xhr.addEventListener("load", uploadComplete, false);

    xhr.open("POST", "/uploadFile", true);
    xhr.send(formData);
}

function uploadFile() {
    var gameTitle = $("#game-title").val();
    var file = document.getElementById('uploaded-file').files[0];
    if (file !== "" && gameTitle.trim() !== "") {
        upload(file, gameTitle, getCookieValue("battleshipUserName"));
    } else if (file === "") {
        alert("Please select a file to upload");
    } else {
        alert("Please add a game title");
    }
}

function uploadProgress(event) {
    var file = document.getElementById('upload-status').innerHTML = 'Uploading...';
}

function uploadComplete(event) {
    var file = document.getElementById('upload-status').innerHTML = event.target.responseText;
}

$("form").submit(function (e) {
    e.preventDefault();
});

function joinGame(roomName) {
    var playerName = getCookieValue("battleshipUserName");
    $.ajax({
        type: "POST",
        url: "/roomGame",
        data: {roomName: roomName, playerName: playerName},
        statusCode: {
            200: function (response) {
                window.location.replace("/pages/game.html?room=" + roomName + "username=" + playerName);
            },
            201: function (response) {
                alert(response);
            },
            203: function (response) {
                alert(response);
            }
        }
    });
}