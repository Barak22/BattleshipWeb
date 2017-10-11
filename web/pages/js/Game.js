setInterval(roomLoader, 1000);

function roomLoader() {
    drawBoards();
    updateMatchDetails();
}

//-------------------------------------------------//
// Draw boards
//-------------------------------------------------//
function drawBoards() {
    var room = getParameterByName('room');
    var username = getParameterByName('username');
    var params = {
        room: room,
        username: username
    };

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
            }
        }
    });
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
        type: type
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

function returnToLobby() {
    window.location.replace("/pages/lobby.html");
}

function quitRoom() {
    var wantsToQuit = confirm("Are you sure you want to quit?");
    if (!wantsToQuit) {
        return;
    }
    var roomName = getParameterByName('room');

    $.ajax({
        type: "POST",
        url: "/quitRoom",
        data: {roomName: roomName},
        success: function (response) {
            window.location.replace("/pages/lobby.html");
        }
    });
}