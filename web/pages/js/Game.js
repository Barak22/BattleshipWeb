setInterval(roomLoader, 2000);

//-------------------------------------------------//
// Draw boards
//-------------------------------------------------//
function roomLoader() {
    var room = getParameterByName('room');
    var params = {
        room: room
    };

    $.ajax({
        type: "GET",
        url: "/getBoards",
        data: params,
        success: function (result) {
            document.getElementById("gameBoards").innerHTML = result;
        },
        error: function (error) {
            alert(error.getText());
        }
    });
}

//-------------------------------------------------//
// Draw boards
//-------------------------------------------------//
function playMove(row, col) {
    var params = {
        row: row,
        col: col
    };

    $.ajax({
        type: "GET",
        url: "/getBoards",
        data: params,
        success: function (result) {
            document.getElementById("gameBoards").innerHTML = result;
        },
        error: function (error) {
            alert(error.getText());
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

// roomLoader();

function drag(ev) {
    ev.dataTransfer.setData("text", ev.target.id);
}

function drop(ev) {
    ev.preventDefault();
    var row = ev.target.getAttribute('row');
    var col = ev.target.getAttribute('col');
    var roomName = getParameterByName('room');
    var params = {
        row: row,
        col: col,
        roomName: roomName
    };

    $.ajax({
        type: "POST",
        url: "/mine",
        data: params,
        statusCode: {
            200: function (response) {
                alert(response);
            },
            201: function (response) {
                alert(response);
            }
        }
    })
}

function allowDrop(ev) {
    ev.preventDefault();
}