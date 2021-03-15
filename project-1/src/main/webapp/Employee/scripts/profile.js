
var user = JSON.parse(sessionStorage.getItem("currentUser"));
window.onload = () => {
    document.getElementById('userName').value=user['username'];
    document.getElementById('firstName').value = user['firstName'];
    document.getElementById('lastName').value = user['lastName'];
    document.getElementById('emailAddress').value = user['email'];
}



function update(){
    let employeeTemplate = {
        email: document.getElementById('emailAddress').value,
        firstName: document.getElementById('firstName').value,
        id: user['id'],
        lastName: document.getElementById('lastName').value,
        password: user['password'],
        role:user['role'],
        username:user['username']
    };

    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function() {
        if(this.readyState ===4 && this.status === 200){
            console.log("success");
            sessionStorage.setItem('currentUser', this.responseText)
            alert("Profile updated reloading page.");
            window.location.reload();
        }

         if (this.readyState === 4 && this.status === 204) {
             console.log("failed to find user");
             var warningText = document.getElementById('warningText');
             warningText.innerHTML = "error occurred";
             warningText.setAttribute('style', " padding-bottom: 7px; color:red;");
         }
    }
    xhr.open('POST','http://localhost:8090/project-1/UpdateProfile');

    xhr.send(JSON.stringify(employeeTemplate));
}