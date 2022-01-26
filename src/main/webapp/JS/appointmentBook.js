// const baseUrl = "http://localhost:8080/medico/appointment";
//global date variable 
// constants file to be created and the urls must be added as follows:
//const baseUrl = "http://localhost:8080/medico/appointment";

//const base = "http://localhost:8080/medico/";

//const charturl = "http://localhost:8080/medico/chart/yearappt/";


var date = document.getElementById("datePicker").value;//new Date();
//date = date.getFullYear() + "-" + (date.getMonth()+1) + "-" + date.getDay();

//global doctor_id and patient_id  
var doctor_id = 0, patient_id = 0;

doctor_id = parseFloat(document.getElementById("doctor_id").innerHTML);
patient_id = parseFloat(document.getElementById("patient_id").innerHTML);
console.log(doctor_id);
console.log(patient_id);

//listening to the date choosen
var alreadyChosen = false;
var slotAlreadyChosen = 0;
var slotsBookedAlready = [0];
var selectedDate;


document.getElementById("datePicker").addEventListener("change", function () {

    alreadyChosen = false;
    slotAlreadyChosen = 0;
    slotsBookedAlready = [0];
    selectedDate = this.value;
    date = this.value // setting the global date
    var selectedDateInMillis = Date.parse(selectedDate)
    var parsedDate = new Date((selectedDateInMillis) * 1000);
    // console.log("Current" + parsedDate.getDay());
    let currentDate = getCurrentDate();
    // Only taking the YYYY-MM-DD part of today's date
    // Beware that the month in new Date() is starting from 0
    // so to normalise we add 1 

    // console.log(currentDate, selectedDate);
    var currentDateInMillis = Date.parse(currentDate);
    var selectedDateInMillis = Date.parse(selectedDate);
    console.log(currentDateInMillis, selectedDateInMillis)

    if (selectedDateInMillis < currentDateInMillis) {
        alert("Please pick a valid date");
        location.reload();
        return;
    }

    if (parsedDate.getDay() === 1) {
        alert("Doctors are unavailable on Sunday!");
        location.reload();
        return;
    }

    var container = document.getElementById("slotRow");
    container.innerHTML = "";
    fetchApointments(selectedDate)
});

//fetching apointments of current doctor on that date
const fetchApointments = async function (date) {
    await axios.get(baseUrl + "/byDoctor/" + doctor_id + "/" + date)
        .then(function (response) {
            // handle success
            //console.log(response);
            for (var i = 0; i < response.data.length; i++) {
                slotsBookedAlready.push(response.data[i].slot_no);
            }
            renderSlots();
            //console.log(slotsBookedAlready);

        })
        .catch(function (error) {
            // handle error
            console.log(error);
        });
}
fetchApointments(date)


//static timeSlots values
var timeSlots = ["10:00 AM", "10:15 AM", "10:30 AM", "10:45 AM", "11:00 AM", "11:15 AM", "11:30 AM", "11:45 AM", "12:00 PM", "12:15 PM", "12:30 PM", "12:45 PM", "02:00 PM", "02:15 PM", "02:30 PM", "02:45 PM", "03:00 PM", "03:15 PM", "03:30 PM", "03:45 PM", "04:00 PM", "04:15 PM", "04:30 PM", "04:45 PM"]

// function to conver 12 hour format to 24 hour format
function convert12to24(timeSlot) {
    var timeSlot = timeSlot.split(" ");
    var hhmm = timeSlot[0].split(":");
    var hh = parseInt(hhmm[0]);
    var mm = hhmm[1];
    //console.log(hh, mm)
    var ampm = timeSlot[1];
    if (ampm === "PM" && hh !== 12) hh += 12;
    return hh + ":" + mm + ":" + "00";
}

// comaparing time
function compareTime(time1, time2) {
    var s1 =
        time1.split(":")[0] * 3600 + time1.split(":")[1] * 60 + time1.split(":")[2];
    var s2 =
        time2.split(":")[0] * 3600 + time2.split(":")[1] * 60 + time1.split(":")[2];
    //console.log(s1, s2, s1 - s2)
    return s1 - s2; // Gets difference in seconds
}

//making view for the appointment date
const renderSlots = function () {
    var container = document.getElementById("slotRow");
    for (var i = 0; i < timeSlots.length; i++) {
        let disableIt = "";
        let booked = "";
        //if (i === 0) console.log(i, slotsBookedAlready);
        let currentSlotTime = convert12to24(timeSlots[i]);
        // console.log(slotsBookedAlready);
        let currentTime = getCurrentTime();
        let currentDate = getCurrentDate();
        selectedDate = document.getElementById("datePicker").value;
        //var currentTime = "11:00:00";
        //var currentTime = currentDate.getHours() + ":" + currentDate.getMinutes() + ":" + currentDate.getSeconds();
        console.log(currentTime, currentSlotTime);
        console.log(currentDate, selectedDate);
        if (slotsBookedAlready.includes(i + 1) === true || (currentDate == selectedDate && compareTime(currentSlotTime, currentTime) <= 0)) {
            // if (slotsBookedAlready.indexOf(i + 1) == -1) {
            // console.log(i + 1);
            disableIt = "disabled";
            booked = "rounded_booked"
        }

        container.innerHTML += `<div class="col-md-2 mb-2 p-1 d-flex justify-content-start">
                <button id="slot${i + 1}" class="btn btns rounded_ ${booked} " onclick="timeSlotHandler('${timeSlots[i]}', '${i + 1}')" ${disableIt}>${timeSlots[i]}</button>
            </div>`;
    }
}

//listening to the slot choosen
var globalTime, globalSlotNO;
// document.addEventListener('DOMContentLoaded', () => {
function timeSlotHandler(time, slotNo) {
    console.log(time, slotNo)
    if (alreadyChosen) {
        //class add
        var curBut = document.querySelector(`#slot${slotAlreadyChosen}`);
        curBut.classList.toggle('rounded_selected');
        curBut.classList.toggle('rounded_');
    }
    alreadyChosen = true;
    slotAlreadyChosen = slotNo;
    var curBut = document.querySelector(`#slot${slotAlreadyChosen}`);
    curBut.classList.toggle('rounded_');
    curBut.classList.toggle('rounded_selected');
    globalTime = time;
    globalSlotNO = slotNo;
}
// fetching appointments by current doctor and patient
var curPatientsAppointmentsWithDoctor = [];

//sending a create new appointment request
function bookAppointment() {
    if (doctor_id == 0 || patient_id == 0 || globalSlotNO == undefined || globalSlotNO <= 0 || globalSlotNO > 24) {
        alert("Pick a valid date and available slot");
        return;
    }
    let currentDate = getCurrentDate();
    var currentSlotTime = convert12to24(timeSlots[globalSlotNO - 1]);
    console.log(currentSlotTime);
    selectedDate = document.getElementById("datePicker").value;

    const params = {
        doctor_id: doctor_id,
        patient_id: patient_id,
        // date: (date + "-" + globalTime),
        date: currentDate,
        slot: globalSlotNO
    };
    if (confirm(`Confirm your appointment for ${selectedDate}? `) === false) {
        return;
    }
    // console.log("docPat: " + date + "   " + curPatientsAppointmentsWithDoctor);
    if (curPatientsAppointmentsWithDoctor.length >= 1) {
        alert("Can't book multiple appointments on same day with same doctor");
        curPatientsAppointmentsWithDoctor = [];
        slotsBookedAlready = [0];
        location.reload();
        return;
    }
    const options = {
        method: 'POST',
        // body: JSON.stringify( params )  
    };
    console.log(params, options);
    console.log(baseUrl);
    var curBut = document.querySelector(`#slot${globalSlotNO}`);

    fetch(baseUrl + "/byDoctor/" + patient_id + "/" + doctor_id + "/" + selectedDate + "-" + currentSlotTime + "/" + globalSlotNO, options)
        .then(response => {
            console.log(response, response.ok, response.status);
            if (!response.ok)
                throw Error("Can't book multiple appointments on same day with same doctor");
            else {
                alert("Appointment Booked Successfully !");
                curBut.disbled = true;
                location.reload();
            }


        })
        .then(response => { console.log(response) })
        .catch(function (error) {
            // handle error
            console.log(error);
            alert("Can't book multiple appointments on same day with same doctor");
            location.reload();
        });
}