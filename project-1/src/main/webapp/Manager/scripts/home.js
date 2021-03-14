let h = document.getElementById("header");

let ses = sessionStorage;
console.log(sessionStorage)
let user = JSON.parse(ses.getItem('currentUser'));
var td;
var uName = user["username"];

window.onload = (() => {
    console.log(ses.getItem('currentUser'));
	
    h.innerHTML+=(" "+ uName);
	AllReimbursements();
}); 

var rowMenu = [
	{
		label: "<i class='fas fa-user'></i> Approve",
		action: function (e, row) {
			    let xhr = new XMLHttpRequest();

			    xhr.onreadystatechange = function () {
			    	if (this.readyState === 4 && this.status === 200) {
			    		var tabledata = this.responseText;
			    		//console.log(tabledata);
			    		table.setData(tabledata);
			    	}
			    }

			    xhr.open("POST", `http://localhost:8090/project-1/${row.getData()["id"]}.ReimbursementApproved`)

			    xhr.send();
		}
	},
	{
		label: "<i class='fas fa-check-square'></i> UnApprove",
		action: function (e, row) {
			    let xhr = new XMLHttpRequest();

			    xhr.onreadystatechange = function () {
			    	if (this.readyState === 4 && this.status === 200) {
			    		var tabledata = this.responseText;
			    		//console.log(tabledata);
			    		table.setData(tabledata);
			    	}
			    }

			    xhr.open("POST", `http://localhost:8090/project-1/${row.getData()["id"]}.ReimbursementUnapporved`)

			    xhr.send();
		}
	}
]



var table = new Tabulator("#example-table",{
		height:205, // set height of table (in CSS or here), this enables the Virtual DOM and improves render speed dramatically (can be any valid css height value)
		layout:"fitColumns", //fit columns to width of table (optional)
		autoColumns: true,
		rowContextMenu:rowMenu,
	rowClick:function(e, row){ //trigger an alert message when the row is clicked
		ses.setItem('currentReimbursementId',row.getData()["id"]);
	},
});

function AllReimbursements(){
	let xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function () {
		if (this.readyState === 4 && this.status === 200) {
			var tabledata = this.responseText;
			//console.log(tabledata);
			table.setData(tabledata);
		}
	}

	xhr.open("GET", "http://localhost:8090/project-1/AllReimbursements")

	xhr.send(JSON.stringify(uName));

}

function AllPendingReimbursements(){
	let xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function () {
		if (this.readyState === 4 && this.status === 200) {
			var tabledata = this.responseText;
			//console.log(tabledata);
			table.setData(tabledata);
		}
	}
	
	xhr.open("GET", "http://localhost:8090/project-1/GetAllReimbursementsOfAGivenType/Pending")

	xhr.send(JSON.stringify(uName));

}

function AllApprovedReimbursements(){
	let xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function () {
		if (this.readyState === 4 && this.status === 200) {
			var tabledata = this.responseText;
			//console.log(tabledata);
			table.setData(tabledata);
		}
	}

	xhr.open("GET", "http://localhost:8090/project-1/GetAllReimbursementsOfAGivenType/Approved")

	xhr.send(JSON.stringify(uName));

}

function AllUnapprovedReimbursements(){
	let xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function () {
		if (this.readyState === 4 && this.status === 200) {
			var tabledata = this.responseText;
			//console.log(tabledata);
			table.setData(tabledata);
		}
	}

	xhr.open("GET", "http://localhost:8090/project-1/GetAllReimbursementsOfAGivenType/Unapproved")

	xhr.send(JSON.stringify(uName));

}

function logout() {
	//  sessionStorage.setItem('currentUser', this.responseText)
	//  sessionStorage.setItem('username', uName);
	// console.log("logging out")
	// window.location = "http://localhost:8090/project-1/logout";


	    let xhr = new XMLHttpRequest();

	    xhr.onreadystatechange = function () {

			    	console.log(this.responseText)


	    }

	    xhr.open("POST", "http://localhost:8090/project-1/logout")
	    xhr.send(); //converts our login in stuff to json


		console.log(sessionStorage.getItem('currentUser'));
		sessionStorage.removeItem('currentUser');
		console.log(sessionStorage.getItem('currentUser'));
		sessionStorage.removeItem('username');
		window.location = "http://localhost:8090/project-1/";
}
