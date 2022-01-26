<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" %>
<jsp:include page="time.jsp" />
<!DOCTYPE html>
<html lang="en">
	<head>
	       <meta charset="UTF-8">
	       <meta http-equiv="X-UA-Compatible" content="IE=edge">
	       <meta name="viewport" content="width=device-width, initial-scale=1.0">
	       <title>Manage Appointment</title>
	       <link rel="preconnect" href="https://fonts.googleapis.com">
	       <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	       <link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@300;400;500&display=swap"
	           rel="stylesheet">
	       <!--stylesheet related plugin-->
	       <link rel="stylesheet"
	           href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />
	       <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" />
	       <style>
	           <%@include file="/WEB-INF/jsp/Assets/CSS/style.css"%>
	       </style>
	       <style>
	           <%@include file="/WEB-INF/jsp/Assets/CSS/manage.css"%>
	       </style>
	               <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
	       integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
	       crossorigin="anonymous"></script>
	</head>
            
	<body>
		<nav class="d-flex justify-content-around pt-4">
			<!-- Header -->
			<div class="d-flex justify-content-center ms-5 w-40">
				<h5 class="rounded_ mhc text-center p-2">Medico Health Care</h5>
			</div>
			<!-- Logo -->
			<div class="d-flex justify-content-end">
				<img src="${pageContext.request.contextPath}/Images/user.png" width="35" height="35" class="rounded_">
				<p class="align-items-center ps-2 pt-2 mb-0"><%= session.getAttribute("patientName") %></p>
				<div class="dropdown">
					<button class="btn dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown"
						aria-expanded="false">
					</button>
					<ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
						<li><a class="dropdown-item" href="<%= request.getContextPath()%>/logout">Logout</a></li>
					</ul>
				</div>
			</div>
		</nav>
             
		<div class="d-flex h-25 ml-50 pl-5 pt-3">
                     <c:if test="${not isCancelled && flag}">
                         <div class="alert alert-danger" role="alert">Cancellation to be done at least before 24
                             hours</div>
                     </c:if>
                     <c:if test="${isCancelled && flag}">
                         <div class="alert alert-success" role="alert">Cancellation successful!</div>
                     </c:if>
                 </div>

                 <div class="d-block bg-box justify-content-center p-4 rounded_ mx-5 mt-4">

                     <h6 class="appt">My Appointments</h6>

                     <!-- Appointment List -->
                     <div class="appt-box p-4 rounded_">
                         <table class="table">
                             <thead>
                                 <tr>
							<th scope="col">Serial No.</th>
                                     <th scope="col">Doctor</th>
                                     <th scope="col">Appointment date<br>(YYYY-MM-DD)</th>
                                     <th scope="col">Time</th>
                                     <th scope="col">Fees</th>
                                 </tr>
                             </thead>
                             <tbody>
						<c:set var="count" value="1" scope="page" />
                                 <c:forEach items="${appts}" var="appt">
                                     <tr>
                                     	<td>
                                             <c:out value="${count }" />
                                         </td>
                                         <td>
                                             <c:out value="${appt.doctor.name}" />
                                         </td>
                                         <td>
                                             <c:out value="${appt.date.substring(0, 10)}" />
                                         </td>
                                         <td>
                                             <c:out value="${appt.slot_no}" />
                                         </td>
                                         <td>
                                             <c:out value="${appt.doctor.fees}" />
                                         </td>
                                         <td>
                                             <form
                                                 action="<%=request.getContextPath() %>/patient/cancelAppt/${appt.doctor.getDoctor_id()}/${appt.getDate()}/${appt.getSlot()}"
                                                 method="post">
                                                 <button type="submit"
                                                     class="btn rounded btns text-center p-2">Cancel</button>
                                             </form>
                                         </td>
                                     </tr>
								 <c:set var="count" value="${count + 1}" scope="page"/>
                                 </c:forEach>
                             </tbody>
                         </table>
                     </div>
                 </div>
                 
                 <footer>          
                     <div class="d-flex justify-content-end px-5 py-2 ">
                         <a href="<%=request.getContextPath()%>/patient/welcome"><button type="submit"
                                 class="btn rounded_ btns text-center w-100 px-4 py-2">Home</button></a>
                     </div>
                 </footer>
                
	</body>

</html>
