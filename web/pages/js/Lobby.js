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
    checkIfUserLoogedOut();
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

function checkIfUserLoogedOut() {
    $.ajax({
        type: "GET",
        url: "/pages/login",
        error: function (response) {
            window.location.replace("/index.html");

        }
    })
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
    var file = $("#uploaded-file").val();
    var params = {
        gameTitle: gameTitle,
        file: file
    };
    if (file != "") {
        alert(file);
    } else {
        alert("Please select a file to upload")
    }

    // $.ajax({
    //     type: "POST",
    //     url: "/uploadFile",
    //     data: params,
    //     success: function (result) {
    //         alert("success");
    //     },
    //     error: function (error) {
    //         alert(error);
    //     }
    // });
}