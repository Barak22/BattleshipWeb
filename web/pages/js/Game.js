//-------------------------------------------------//
// Draw boards
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