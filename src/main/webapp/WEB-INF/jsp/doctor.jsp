<%@ page import="org.springframework.ui.Model"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <%@ page isELIgnored="false" %>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
        <%@include file="/WEB-INF/jsp/Assets/CSS/doctor.css"%>
    </style>
    <!-- JavaScript -->
    <script src='https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.min.js'></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/JS/constants.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"
        integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/JS/monthdropdown.js"></script>
</head>

<body>
    <div class="container">
        <p hidden class="doctorID"><%= session.getAttribute("currentDoctorID") %></p>
        <!-- Header -->
        <div class="d-flex justify-content-around p-2">
            <p class="rounded_ mhc text-center w-50">Medico Health Care</p>
            <button type="submit" class="btn btns rounded_ mhc size-40">
                <a href="<%= request.getContextPath()%>/logout">Logout</a>
            </button>
        </div>
        <!-- Starting welcome -->
        <div class="row">
            <div class="d-flex  pb-5">
                <div class="welcome col-md-10">
                    <p class="display-5 p-3 pt-2"><span class="text-muted">Welcome</span>
                        <%= session.getAttribute("currentDoctorName") %>!</p>
                    <c:if test="${not empty patientDetails}">
                        <p class="px-4">${patientDetails.size()} appointments on
                            <%= request.getAttribute("selectedDate") %></p>
                    </c:if>
                </div>
                <div class="col-md-2  ">
                    <img src="${pageContext.request.contextPath}/docImages/${currentDoctor.getDoctor_id()}/${currentDoctor.getProfile_pic()}" class=" img-fluid">
                </div>
            </div>
        </div>
        <!-- Showing doctor list selecting date from calendar -->
        <div class="row px-2">
            <div class="col-md-6 appoint p-2 mt-4">
                <div class="d-flex justify-content-between">
                    <p><b><u>My Appointments - </u></b></p>
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger" role="alert">
                            ${error}
                        </div>
                    </c:if>
                    <form class="form-container" action="<%=request.getContextPath() %>/doctor/getappt" method="post">
                        <input id="apptdate" type="date" name="apptdate" class="bzero"
                            value="<%=request.getAttribute("selectedDate") %>">
                        <!-- <script>document.getElementById('apptdate').valueAsDate = new Date();</script> -->
                        <button id="apptButton" type="submit" class="btn rounded_ btns px-1">Submit</button>
                    </form>
                </div>
                <div style="overflow: scroll;max-height: 300px;">
                    <table class="table table-striped">
                        <tr>
                            <th class="px-2">
                                Patient Name
                            </th>
                            <th>
                                Appointment time
                            </th>
                        </tr>
                        <c:forEach var="patient" items="${patientDetails}">
                            <tr style="">
                                <td class="border-bottom-0 px-2">
                                    <p>${patient[0]}</p>
                                </td>
                                <td>
                                    <p>${patient[1]}</p>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
            <!-- Bar graph -->
            <div class="col-md-6">
                <canvas id="myChart2" width="400" height="250"></canvas>
            </div>
        </div>

        <!-- Adding generate report buttons -->
        <div class="pt-3 p-2">
            <span>Generate Report by a specific - </span>
            <!-- Modal -->
            <div class="row">
                <div class="col-2 p-1">
                    <button type="button" class="btn btns rounded_ m-1 px-3" data-toggle="modal" data-target="#byDate">
                        Date
                    </button>
                </div>
                <div class="col-2 p-1">
                    <button type="button" class="btn btns rounded_ m-1 px-3" data-toggle="modal" data-target="#byMonth">
                        Month
                    </button>
                </div>
                <div class="col-2 p-1">
                    <button type="button" class="btn btns rounded_ m-1 px-3" data-toggle="modal" data-target="#byRange">
                        Range of dates
                    </button>
                </div>
            </div>
        </div>
        <!-- by date modal  -->
        <div class="modal fade" id="byDate" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle"
            aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <p class="modal-title" id="byDateTitle">Choose a Date to download your report</p>
                    </div>
                    <div class="modal-body">
                        <input id="datePicker" name="datePicker" type="date" class="w-100 mb-2" for="slotDate" />
                        <script>
                            document.getElementById('datePicker').valueAsDate = new Date();
                        </script>
                        <div class="row justify-content-between">
                            <div class="col-6 p-1">
                                <button id="byDatePdf" type="button" class="btn rounded_ btns px-2">Download PDF </button>
                            </div>
                            <div class="col-6 p-1 text-end">
                                <button id="byDateExcel" type="button" class="btn rounded_ btns px-2">Download Excel
                                </button>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btns rounded_" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- by month modal  -->
        <div class="modal fade" id="byMonth" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle"
            aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <p class="modal-title" id="byMonthTitle">Choose a Date to download your report</p>
                    </div>
                    <div class="modal-body">
                        <select id="yearPicker"></select>
						<select id="monthPicker"></select>	
                        <!-- 
                        <script>
                            document.getElementById('monthPicker').value = (new Date()).getMonth() + 1;
                        </script>
                         -->
                        <div class="row justify-content-between">
                            <div class="col-6 p-1">
                                <button id="byMonthPdf" type="button" class="btn rounded_ btns px-2">Download PDF </button>
                            </div>
                            <div class="col-6 p-1 text-end">
                                <button id="byMonthExcel" type="button" class="btn rounded_ btns px-2">Download Excel
                                </button>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btns rounded_" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- by rane of dates modal  -->
        <div class="modal fade" id="byRange" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle"
            aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <p class="modal-title" id="byRangeOfDatesTitle">Choose a range of dates to download your report</p>
                    </div>
                    <div class="modal-body">
                        <input id="rangePicker1" type="date" class="mb-2" for="slotDate" />
                        <script>
                            document.getElementById('rangePicker1').valueAsDate = new Date();
                        </script>
                        <span class="fw-bold">to</span>
                        <input id="rangePicker2" type="date" class="mb-2" for="slotDate" />
                        <script>
                            document.getElementById('rangePicker2').valueAsDate = new Date();
                        </script>
                        <div class="row justify-content-between">
                            <div class="col-6 p-1">
                                <button id="byRangePdf" type="button" class="btn rounded_ btns px-2">Download PDF </button>
                            </div>
                            <div class="col-6 p-1 text-end">
                                <button id="byRangeExcel" type="button" class="btn rounded_ btns px-2">Download Excel
                                </button>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btns rounded_" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <!--
        <div class="pt-2">
            <span>Generate Report</span>
            <button id="pdf" class="btn rounded_ btns text-center  mx-2 p-2">Daily</button>
            <button id="xls" class="btn rounded_ btns text-center mx-2 p-2">Monthly</button>
        </div>
        -->

    </div>
    <script src="${pageContext.request.contextPath}/JS/doctor_bargraph.js"></script>
    <script src="${pageContext.request.contextPath}/JS/pdfreport_generator.js"></script>
    <script src="${pageContext.request.contextPath}/JS/excelreport_generator.js"></script>
</body>

</html>