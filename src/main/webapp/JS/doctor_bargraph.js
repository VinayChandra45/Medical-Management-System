/*

add 

const charturl = "http://localhost:portno/medico/chart/yearappt/";

to constants.js

*/



var apptButton = document.getElementById("apptButton");
var id = document.getElementsByClassName("doctorID")[0].innerHTML;
var fetcheddata;
var selectedDate = document.getElementById("apptdate").value;

var url = charturl+id + "/" + selectedDate.substring(0, 4);
if (selectedDate === ""){
	url = charturl+id + "/" + (new Date()).getFullYear();
	//selectedDate = new Date().getFullYear();
}

var display_year = selectedDate.substring(0, 4);
if (selectedDate === ""){
	display_year = (new Date()).getFullYear();	
}

// console.log(selectedDate);
fetch(url).then(
	response => response.json().then(
		response => {
			fetcheddata = response;
			console.log("fetcheddata");
			console.log(fetcheddata);
			
			mydata = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
			
			for(let i = 0; i < fetcheddata.length; i++){
				mydata[fetcheddata[i][0] - 1] = fetcheddata[i][1]; 
			}
			console.log(mydata);
			const ctx2 = document.getElementById('myChart2').getContext('2d');
			Chart.defaults.global.legend.display = false;
			//Chart.defaults.scale.gridLines.drawOnChartArea = false;		
			//Chart.defaults.global.title.display = true;
			//Chart.defaults.global.legend.text = "hi";
			console.log(selectedDate, display_year);
			const myChart2 = new Chart(ctx2, {
			    type: 'bar',
			    data: {
			        labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],			        
					datasets: [{
			            label: 'Appointments',
			            data: mydata,
			            backgroundColor: [
			                'rgba(255, 99, 132, 1)',
			                'rgba(54, 162, 235, 1)',
			                'rgba(255, 206, 86, 1)',
			                'rgba(75, 192, 192, 1)',
			                'rgba(153, 102, 255, 1)',
			                'rgba(255, 159, 64, 1)',
			                'rgba(255, 99, 132, 1)',
			                'rgba(54, 162, 235, 1)',
			                'rgba(255, 206, 86, 1)',
			                'rgba(75, 192, 192, 1)',
			                'rgba(153, 102, 255, 1)',
			                'rgba(255, 159, 64, 1)'
			            ],
			            borderColor: [
			                'rgba(255, 99, 132, 1)',
			                'rgba(54, 162, 235, 1)',
			                'rgba(255, 206, 86, 1)',
			                'rgba(75, 192, 192, 1)',
			                'rgba(153, 102, 255, 1)',
			                'rgba(255, 159, 64, 1)',
			                'rgba(255, 99, 132, 1)',
			                'rgba(54, 162, 235, 1)',
			                'rgba(255, 206, 86, 1)',
			                'rgba(75, 192, 192, 1)',
			                'rgba(153, 102, 255, 1)',
			                'rgba(255, 159, 64, 1)'
			            ],
			            borderWidth: 1
			        }]
			    },
			    options: {
					title: {
						display: true,
						text: "Appointments vs Months for " + display_year,
					},
			        maintainAspectRatio: false,
			        scales: {
			            grid: {
			                borderWidth: 15
			            },
			            y: {
			                beginAtZero: true
			            },
			            x :{

			            }
			        }
				}
			});
		}
	));
/*
async function data() {
	var response = await fetch(url);
	var myJSON = await response.json();
	console.log("here");
	console.log(myJSON);
	fetcheddata = myJSON;
};
data()*/

// console.log(url);
// console.log(id);
// fetcheddata = data();
// fetcheddata = fetcheddata.then(res => res);
