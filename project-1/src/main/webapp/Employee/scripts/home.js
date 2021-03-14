let h = document.getElementById("header");

let ses = sessionStorage;
let user = JSON.parse(ses.getItem('currentUser'));
var td;
var uName = user["username"];

window.onload = (() => {
    console.log(ses.getItem('currentUser'));

        var elements = document.getElementsByClassName("insertNameHere");
        Array.from(elements).forEach((e) => {
            e.textContent = uName;
        });
});

function logout() {
	sessionStorage.clear();
    console.log("logging out")
    window.location = "http://localhost:8090/project-1/logout";
}