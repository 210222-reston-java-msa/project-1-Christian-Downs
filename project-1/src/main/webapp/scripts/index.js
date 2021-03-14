function sendLogin(){
    console.log("send login triggered");


    //grabbing the username and password from the html doc
    let uName = document.getElementById('uName').value;
    let pWord = document.getElementById('pWord').value;
    console.log(`Username: ${uName}`);
    console.log(`Password: ${pWord}`);

    // creating an obj literal that will store the credentials
    let loginTemplate = {
        username: uName,
        password: pWord
    };

    //AJAX starts here

    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function(){
        console.log(this.responseText)
        if(this.readyState ===4 && this.status === 200){
            console.log("success");
            sessionStorage.setItem('currentUser', this.responseText)
            sessionStorage.setItem('username',uName);
            window.location.assign( "http://localhost:8090/project-1/home");

            console.log(sessionStorage.getItem('currentUser'));

        }

        if(this.readyState === 4 && this.status === 204){
            console.log("failed to find user");
        }
    }

    xhr.open("POST","http://localhost:8090/project-1/login")
	console.log(JSON.stringify(loginTemplate));
    xhr.send(JSON.stringify(loginTemplate)); //converts our login in stuff to json
}