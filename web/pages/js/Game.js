setInterval(roomLoader, 1000);

function roomLoader() {
    drawBoards();
    updateMatchDetails();
    printMessage();
}

//-------------------------------------------------//
// Draw boards
//-------------------------------------------------//
function drawBoards() {

    if (document.getElementById("final-boards") !== null || document.getElementById("watch-boards-final") !== null) {
        return; // Game is over - render no more
    }

    var room = getParameterByName('room');
    var username = getParameterByName('username');
    var params = {
        room: room,
        username: username
    };

    if (getParameterByName("mode") === null) {
        $.ajax({
            type: "GET",
            url: "/getBoards",
            data: params,
            statusCode: {
                200: function (response) {
                    document.getElementById("gameBoards").innerHTML = response;
                },
                201: function (response) {
                    if (document.getElementById("waiting-other-player") === null) {
                        document.getElementById("gameBoards").innerHTML = response;
                    }
                },
                202: function (response) {
                    if (document.getElementById("waiting-other-player") === null) {
                        document.getElementById("gameBoards").innerHTML = response;
                    }
                },
                203: function (response) {
                    alert('Game Over!');
                    document.getElementById("gameBoards").innerHTML = response;
                }
            }
        });

        if (document.getElementById("chatBody") === null) {
            buildChat();
        }
    } else {
        $.ajax({
            type: "POST",
            url: "/getBoards",
            data: params,
            statusCode: {
                200: function (response) {
                    document.getElementById("gameBoards").innerHTML = response;
                },
                201: function (response) {
                    if (document.getElementById("waiting-other-player") === null) {
                        document.getElementById("gameBoards").innerHTML = response;
                    }
                }
            }
        });
    }


}

//-------------------------------------------------//
// Updates match details
//-------------------------------------------------//
function updateMatchDetails() {
    var room = getParameterByName('room');
    var params = {
        room: room
    };

    $.ajax({
        type: "GET",
        url: "/getMatchDetails",
        data: params,
        statusCode: {
            200: function (response) {
                document.getElementById("gameStats").innerHTML = response;
            },
            201: function (response) {
                // do nothing
            },
            202: function (response) {
                if (document.getElementById("gameOver") === null) {
                    document.getElementById("gameStats").innerHTML = response;
                }
            }
        }
    });
}

//-------------------------------------------------//
// Extracts the room name parameter
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

roomLoader();

function drag(ev) {
    ev.dataTransfer.setData("text", ev.target.id);
}

function drop(ev) {
    ev.preventDefault();
    playMove(ev);
}

function allowDrop(ev) {
    ev.preventDefault();
}

function playMove(ev) {
    var row = ev.target.getAttribute('row');
    var col = ev.target.getAttribute('col');
    var type = ev.target.getAttribute('type');
    var roomName = getParameterByName('room');
    var params = {
        row: row,
        col: col,
        roomName: roomName,
        type: type,
        username: getParameterByName('username')
    };

    $.ajax({
        type: "POST",
        url: "/playMove",
        data: params,
        statusCode: {
            //Regular move.
            200: function (response) {
                // Updated last move in the logic. do nothing here.
            },
            //Player won.
            201: function (response) {
                //Need to unhide the 'return to lobby' button.
            },
            //XML exception
            202: function (response) {
                alert(response);
            },
            501: function (response) {
                alert(response.responseText);
                window.location.replace("/index.html");
            }
        }
    });
}

function returnToLobby(isGameEnded) {
    var roomName = getParameterByName('room');
    var userName = getParameterByName('username');

    if (isGameEnded === "false") {
        $.ajax({
            type: "GET",
            url: "/quitRoom",
            data: {roomName: roomName, userName: userName}
        });
    }
    window.location.replace("/pages/lobby.html?username=" + getParameterByName('username'));
}

function returnToLobbyWatcher(roomName) {
    var playerName = getParameterByName('username');
    $.ajax({
        type: "GET",
        url: "/roomGame",
        data: {roomName: roomName, playerName: playerName, action: 'remove'},
        statusCode: {
            200: function (response) {
                window.location.replace("/pages/lobby.html?username=" + getParameterByName('username'));
            },
            201: function (response) {
            }
        }
    });
}

function quitRoom() {
    var wantsToQuit = confirm("Are you sure you want to quit?");
    if (!wantsToQuit) {
        return;
    }
    var roomName = getParameterByName('room');
    var userName = getParameterByName('username');

    $.ajax({
        type: "POST",
        url: "/quitRoom",
        data: {roomName: roomName, userName: userName},
        statusCode: {
            //Regular move.
            200: function (response) {
            },

            201: function (response) {
            }
        }
    });
}

function buildChat() {
    $.ajax({
        type: "GET",
        url: "/chat",
        statusCode: {
            200: function (response) {
                document.getElementById("chatArea").innerHTML = response;
            }
        }
    });
}

function addMessage() {
    var roomName = getParameterByName('room');
    var userName = getParameterByName('username');
    var message = document.getElementsByClassName("form-control")[0].value;

    var params = {
        room: roomName,
        message: message,
        userName: userName
    };

    if (document.getElementById("chatEnded") !== null) {
        return;
    }

    $.ajax({
        type: "POST",
        url: "/chat",
        data: params,
        statusCode: {
            200: function (response) {
                document.getElementsByClassName("form-control")[0].value = "";
            }
        }
    });
}

function printMessage() {
    var roomName = getParameterByName('room');

    var params = {
        room: roomName
    };

    if (document.getElementById("chatEnded") !== null) {
        return;
    }

    $.ajax({
        type: "POST",
        url: "/getMatchDetails",
        data: params,
        statusCode: {
            200: function (response) {
                if (document.getElementById("chatMessages") !== null) {
                    document.getElementById("chatMessages").innerHTML = response;
                }
            }
        }
    });
}