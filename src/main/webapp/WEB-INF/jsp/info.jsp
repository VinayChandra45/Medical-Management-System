<%@page import="java.io.Writer"%>
<%@ page import="org.springframework.ui.Model"%>
<%@ page import = "java.util.Date, com.nrifintech.medico.entity.Doctor, java.util.concurrent.TimeUnit" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Doctor Info</title>
    <!-- Adding Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <!-- Font Style -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;700&display=swap" rel="stylesheet">
    <!-- Linking CSS File -->
    <style>
        <%@include file="/WEB-INF/jsp/Assets/CSS/style.css"%>
    </style>
    <style>
        <%@include file="/WEB-INF/jsp/Assets/CSS/info.css"%>
    </style>
    <!-- FontAwesome CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta2/css/all.min.css"
        integrity="sha512-YWzhKL2whUzgiheMoBFwW8CKV4qpHQAEuvilg9FAn5VJUDwKZZxkJNuGM4XkWuk94WCrrwslk8yWNGmY1EduTA=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <!-- JavaScript Bundle with Popper -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/JS/constants.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous">
    </script>
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/JS/utilityFunctions.js"></script>
</head>
<body>
    <!-- hidden logic -->
    <p id="doctor_id" hidden>
        ${currDoctor.getDoctor_id()}
    </p>
    <p id="patient_id" hidden>
        <%= session.getAttribute("currentPatientID")%>
    </p>
    <div class="container">
        <nav class="d-flex justify-content-around pt-4">
            <div></div>
            <!-- Header -->
            <div class="d-flex justify-content-center ms-5 w-40">
                <h5 class="rounded_ mhc text-center">Medico Health Care</h5>
            </div>
            <!-- Logo -->
            <div class="d-flex justify-content-end">
                <img src="${pageContext.request.contextPath}/Images/user.png" width="35" height="35" class="border_rd_10">
                <p class="align-items-center ps-2 pt-2 mb-0"><%= session.getAttribute("patientName")%></p>
                <div class="dropdown">
                    <button class="btn dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown"
                        aria-expanded="false">
                    </button>
                    <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
<!--                         <li><a class="dropdown-item" href="#">Edit Profile</a></li> -->
                        <li><a class="dropdown-item" href="<%= request.getContextPath()%>/logout">Logout</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="row mx-2 my-4">
            <div class="col-md-3">
                <div class="d-flex justify-content-center">
                    <img 
                    src="${pageContext.request.contextPath}/docImages/${currDoctor.getDoctor_id()}/${currDoctor.getProfile_pic()}" alt="Doctor's Profile Picture" 
                        width="220" height="220" class="cmn_bg border_rd_10 p-3">
                </div>
                <div class="d-flex justify-content-center">
                    <i onclick="increase_likes(this)" class="far fa-heart my-auto"></i>
                    <p id="like_no" class="px-2 my-auto" style="color: #115CCD;">${currDoctor.getUpvotes()}</p>
                    <p class="my-auto"><span class="fw-400" style="color: #115CCD;">Upvotes!</span></p>
                </div>
            </div>
            <div class="col-md-6 my-auto">
                <div class="fw-700">
                    <p>${currDoctor.getName()}</p>
                </div>
                <div class="fw-400">
                    <p>Specialization: <span style="color:#115CCD">${currDoctor.getSpecialization()}</span></p>
                    <p>Degeree: <span style="color:#115CCD">${currDoctor.getDegree()}</span></p>
                </div>
                <div class="d-flex fw-400">
                    <p>Experience: <span style="color:#115CCD">${yoe} years</span></p>
                    <p class="px-4">Fees: <span style="color:#115CCD">${currDoctor.getFees()}</span></p>
                </div>
                <div class="fw-400" style="text-align: justify;">
                    <p>
                        ${currDoctor.getDescr()}
                    </p>
                </div>
            </div>
        </div>
        <div class="fw-500 text-center pb-3 "><u>Book an Appointment with ${currDoctor.getName()}</u></div>
        <div class="row mx-2">
            <div class="col-md-2 d-flex justify-content-start">
                <p class="fw-500" style="color:#115CCD">Select a date - </p>
            </div>
            <div class="col-md-3">
                <input id="datePicker" type="date" for="slotDate" />
                <script>
                    document.getElementById('datePicker').valueAsDate = new Date();
                </script>
            </div>
        </div>
        <input id="slotTime" name="slotTime" hidden />
        <div class="row mx-2">
            <p class="fw-500 my-3" style="color:#115CCD">Select your slot - </p>
        </div>
        <div style="display:inline; text-align: center;" class="mb-2 d-flex justify-content-center">
        	<ul style="list-style-type:none;  display: inline-block;">
			    <li class="myli"><i class="Blue fas fa-square"></i> - Free Slots</li>
			    <li class="myli"><i class="Green fas fa-square"></i> - Selected Slots</li>
			    <li class="myli"><i class="Red fas fa-square"></i> - Unavailable Slots</li>
			</ul>    	
        </div>
    </div>
    <div class="container mt-3">
        <div id="slotRow" class="row mb-3">
        </div>
    </div>
    <div class="d-flex justify-content-end pt-2 pl-5 w-94 pb-2 mr-1">   
    	<div class="col-md-2 mb-2 pl-1 d-inline justify-content-start">
    		<a href="<%=request.getContextPath()%>/patient/welcome"><button type="submit"
                    class="btn rounded_ btns p-2">Go To Dashboard</button></a> 
    	</div>
    	<div class="col-md-2 mb-2 pl-1 d-inline justify-content-start">
    		<button type='submit' onclick="fetchApointmentsByDoctorAndPatient()" class="btn rounded_ btns p-2">Book
            Appointment</button>
    	</div>         
        
    </div>
    <script>
        function increase_likes(x) {
            x.classList.toggle("fas");
            // document.getElementById("like_no").value += 1;
        }
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/JS/appointmentBook.js"></script>
</body>
</html>