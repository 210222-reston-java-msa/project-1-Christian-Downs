/**
 * 
 */
let h = document.getElementById("header");

let ses = sessionStorage;
console.log(sessionStorage)
let user = JSON.parse(ses.getItem('currentUser'));

window.onload = (() => {
    fetch('http://localhost:8090/project-1/GetAllEmployees')
    .then((response) => response.json())
    .then( (data) => {
        
        for(var d =0;d<data.length;d++){
            delete data[d].password;
        }
        table.setData(data);
    })
});


// var rowMenu = [{
//         label: "<i class='fas fa-user'></i> Approve",
//         action: function (e, row) {
//             let xhr = new XMLHttpRequest();

//             xhr.onreadystatechange = function () {
//                 if (this.readyState === 4 && this.status === 200) {
//                     var tabledata = this.responseText;
//                     //console.log(tabledata);
//                     table.setData(tabledata);
//                 }
//             }

//             xhr.open("POST", `http://localhost:8090/project-1/${row.getData()["id"]}.ReimbursementApproved`)

//             xhr.send();
//         }
//     },
//     {
//         label: "<i class='fas fa-check-square'></i> UnApprove",
//         action: function (e, row) {
//             let xhr = new XMLHttpRequest();

//             xhr.onreadystatechange = function () {
//                 if (this.readyState === 4 && this.status === 200) {
//                     var tabledata = this.responseText;
//                     //console.log(tabledata);
//                     table.setData(tabledata);
//                 }
//             }

//             xhr.open("POST", `http://localhost:8090/project-1/${row.getData()["id"]}.ReimbursementUnapporved`)

//             xhr.send();
//         }
//     },
//     {
//         label: "<i class='fas fa-check-squere'></i>Info",
//         action: function (e, row) {
//             var rowData = row.getData();
//             let holder = document.getElementById("infoSpot")
//             holder.innerHTML = "";
//             for (var key in rowData) {
//                 var row = document.createElement("div");
//                 row.className = 'row';
//                 holder.appendChild(row);
//                 var firstDiv = document.createElement("div");
//                 firstDiv.className = "col-sm-4";
//                 // firstDiv.style = "background-color:beige;";
//                 row.appendChild(firstDiv);
//                 var firstSpan = document.createElement("span");
//                 firstSpan.className = "float-right";
//                 firstDiv.appendChild(firstSpan);
//                 firstSpan.innerHTML = `${key}:`;
//                 var secondDiv = document.createElement('div');
//                 secondDiv.className = "col-sm-8";
//                 // secondDiv.style = "background-color:aqua;";
//                 row.appendChild(secondDiv);
//                 if (key == 'resolverId') {
//                     var resolverIdSpan = document.createElement("span");
//                     resolverIdSpan.className = 'float-left';
//                     secondDiv.appendChild(resolverIdSpan);
//                     console.log(rowData[key])
//                     if (rowData[key] != 0) {
//                         console.log(rowData[key])
//                         fetch(`http://localhost:8090/project-1/GetEmployeeById/${rowData[key]}`)
//                             .then((response) => response.json())
//                             .then((data) => {
//                                 console.log(data);
//                                 resolverIdSpan.innerHTML = data;
//                             })
//                     } else {
//                         resolverIdSpan.innerHTML = `Unresolved`;
//                     }
//                 } else {
//                     var secondSpan = document.createElement("span");
//                     secondSpan.className = 'float-left';
//                     secondDiv.appendChild(secondSpan);
//                     secondSpan.innerHTML = `${rowData[key]}`;
//                 }


//             };
//         }
//     }
// ]
var tableHolder = document.getElementById("tableHolder");
var table = new Tabulator("#example-table", {
    height: 205, // set height of table (in CSS or here), this enables the Virtual DOM and improves render speed dramatically (can be any valid css height value)
    layout: "fitColumns", //fit columns to width of table (optional)
    autoColumns: true,
    selectable:1,
    rowClick: function (e, row) { //trigger an alert message when the row is clicked
        console.log(row.getData().id)
		fetch(`http://localhost:8090/project-1/GetAllTheReimbursementsOfEmployeeWithId/${row.getData().id}`)
        .then((response)=>response.json())
        .then((data) => {
            tableHolder.innerHTML = "";
			if(data.length == 0) {
				tableHolder.innerHTML = "They don't have any reimbursements";
			} else {
			let table2 = document.createElement("table");
            table2.className = ("table table-striped table-bordered");
            tableHolder.appendChild(table2);
            let thead = document.createElement("thead");
            table2.appendChild(thead);
            let tr = document.createElement('tr');
            thead.appendChild(tr);
            let th = document.createElement('th');
            th.setAttribute("scope","col");
            tr.appendChild(th);
            th.innerHTML = "Date";
            th = document.createElement("th");
            th.setAttribute("scope","col");
            tr.appendChild(th);
            th.innerHTML = "Amount";
            th = document.createElement("th");
            th.setAttribute("scope", "col");
            tr.appendChild(th);
            th.innerHTML = "Description";
            th = document.createElement("th");
            th.setAttribute("scope", "col");
            tr.appendChild(th);
            th.innerHTML = "Image";
            th = document.createElement("th");
            th.setAttribute("scope", "col");
            tr.appendChild(th);
            th.innerHTML = "Type";
            th = document.createElement("th");
            th.setAttribute("scope", "col");
            tr.appendChild(th);
            th.innerHTML = "Status";
            th = document.createElement("th");
            th.setAttribute("scope", "col");
            tr.appendChild(th);
            th.innerHTML = "Actions";
           
            let tBody = document.createElement("tbody")
            table2.appendChild(tBody);
            data.forEach(e => {
 				tr = document.createElement("tr");
                tBody.appendChild(tr);

                let td = document.createElement("td");
                // date submitted
                tr.appendChild(td);
                td.setAttribute("scope", "row");
                var submitted = new Date(e['submitted']).toString();
                td.innerHTML = submitted;

                //amount
                td = document.createElement("td");
                tr.appendChild(td);
                td.innerHTML = `$${e['amount']}`;

                // Description
                td = document.createElement("td");
                tr.appendChild(td);
                td.innerHTML = e['description'];

                //image
                td = document.createElement("td");
                tr.appendChild(td);
                td.innerHTML = e['receipt'];

                //type
                td = document.createElement("td");
                tr.appendChild(td);
                td.innerHTML = e['type'];

                td = document.createElement("td");
                tr.appendChild(td);
                td.innerHTML = e['status'];

                td = document.createElement("td");
                tr.appendChild(td);
                td.innerHTML = `<button type="button" class="btn btn-warning btn-sm" onclick = "deleteReimbursement(${e['id']})">Delete</button>`;

            });
			}
        });
    },
});


function logout() {

    console.log("logging out")
    window.location = "http://localhost:8090/project-1/";
}