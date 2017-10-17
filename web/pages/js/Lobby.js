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
        url: "../onlineUsers",
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
    var params = {
        username: getParameterByName('username')
    };

    $.ajax({
        type: "GET",
        url: "../getGames",
        data: params,
        success: function (result) {
            document.getElementById("games-list").innerHTML = result;
        },
        error: function (error) {
            // alert(error);
        }
    });
}

//-------------------------------------------------//
// Deletes a selected game
//-------------------------------------------------//
function deleteGame(roomName) {
    var params = {
        username: getParameterByName('username'),
        roomName: roomName
    };

    $.ajax({
        type: "POST",
        url: "../getGames",
        data: params,
        statusCode: {
            200: function (response) {
                alert(response);
            },
            201: function (response) {
                alert(response);
            },
            202: function (response) {
                alert(response);
            },
            203: function (response) {
                alert(response);
            }
        }
    });
}

//-------------------------------------------------//
// Ajax calls
//-------------------------------------------------//
function ajaxCalls() {
    getOnlineUsers();
    getGames();
    checkLoggedIn();
    welcomeUserText();
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

    xhr.open("POST", "../uploadFile", true);
    xhr.send(formData);
}

function uploadFile() {
    var gameTitle = $("#game-title").val();
    var file = document.getElementById('uploaded-file').files[0];
    if (file !== "" && gameTitle.trim() !== "") {
        upload(file, gameTitle, getParameterByName('username'));
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
    var playerName = getParameterByName('username');
    $.ajax({
        type: "POST",
        url: "../roomGame",
        data: {roomName: roomName, playerName: playerName},
        statusCode: {
            200: function (response) {
                window.location.replace("../pages/game.html?room=" + roomName + "&username=" + playerName);
            },
            201: function (response) {
                alert(response);
            },
            203: function (response) {
                alert(response);
            },
            501: function (response) {
                alert(response.responseText);
                window.location.replace("../index.html");
            }
        }
    });
}

function watchGame(roomName) {
    var playerName = getParameterByName('username');
    $.ajax({
        type: "GET",
        url: "../roomGame",
        data: {roomName: roomName, playerName: playerName, action: 'add'},
        statusCode: {
            200: function (response) {
                window.location.replace("../pages/game.html?room=" + roomName + "&username=" + playerName + "&mode=watch");
            },
            201: function (response) {
                alert("You are one of the players of this game - Please play fair!");
            },
            203: function (response) {
                alert("You already registered as a watcher to this game");
            }
        }
    });
}

//-------------------------------------------------//
// Extracts the username parameter
//-------------------------------------------------//
function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}

function welcomeUserText() {
    document.getElementById('hdl-welcome2').innerHTML = getParameterByName('username');
}

function checkLoggedIn() {
    $.ajax({
        type: "GET",
        url: "../pages/login",
        statusCode: {
            200: function (response) {
            },
            201: function (response) {
                alert("The system recognized a logged out action from other tab")
                window.location.replace("../index.html");
            }
        }
    });
}