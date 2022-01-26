const getCurrentDate = function () {
    let currentDate = new Date();
    let currentYear = currentDate.getFullYear();
    let currentMonth = addZero(currentDate.getMonth() + 1);
    let currentDay = addZero(currentDate.getDate());
    currentDate = currentYear + "-" + currentMonth + "-" + currentDay;
    return currentDate;
}
const getCurrentTime = function () {
    let currentDate = new Date();
    let currentTime = addZero(currentDate.getHours()) + ":" + addZero(currentDate.getMinutes()) + ":" + addZero(currentDate.getSeconds());
    return currentTime;
}

const addZero = function (val) {
    if (String(val).length === 1) {
        val = "0" + val;
    }
    return val;
}

const fetchApointmentsByDoctorAndPatient = function () {
    curPatientsAppointmentsWithDoctor = [];
    datee = document.getElementById('datePicker').value;
    fetchPromis(datee).then(function (response) {
        // handle success
        //console.log(response);
        for (var i = 0; i < response.data.length; i++) {
            curPatientsAppointmentsWithDoctor.push(response.data[i].slot_no);
        }
        bookAppointment();
    })
    .catch(function (error) {
        // handle error
        console.log(error);
    });
    return curPatientsAppointmentsWithDoctor;
}

const fetchPromis = async (datee) => {
    return axios.get(baseUrl + "/byDoctorPatient/" + doctor_id + "/" + patient_id + "/" + datee);
}