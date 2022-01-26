<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <!DOCTYPE html>
    <html lang="en">

    <head>

      <meta charset="UTF-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <!-- Adding Bootstrap -->
      <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
      <!-- Font Style -->
      <link rel="preconnect" href="https://fonts.googleapis.com">
      <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
      <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;700&display=swap" rel="stylesheet">
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta2/css/all.min.css"
        integrity="sha512-YWzhKL2whUzgiheMoBFwW8CKV4qpHQAEuvilg9FAn5VJUDwKZZxkJNuGM4XkWuk94WCrrwslk8yWNGmY1EduTA=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
      <!-- chart js -->
      <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.6.0/chart.min.js"
        integrity="sha512-GMGzUEevhWh8Tc/njS0bDpwgxdCJLQBWG3Z2Ct+JGOpVnEmjvNx6ts4v6A2XJf1HOrtOsfhv3hBKpK9kE5z8AQ=="
        crossorigin="anonymous" referrerpolicy="no-referrer"></script>
      <!-- Linking CSS File -->
      <style>
        <%@include file="/WEB-INF/jsp/Assets/CSS/style.css"%>
      </style>
      <style>
        <%@include file="/WEB-INF/jsp/Assets/CSS/admin.css"%>
      </style>
      <!-- <link rel="stylesheet" href="Assets/CSS/Patient.css"> -->
      <title>admin</title>
		
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
      <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
      
       <!-- JavaScript Bundle with Popper -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/JS/constants.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous">
    </script>

      <script type="text/javascript">
        window.onload = function () { 
			 
			<% if (session.getAttribute("dataPoint2") != null) { %>
			var chart = new CanvasJS.Chart("barChart1", {
          animationEnabled: true,
          exportEnabled: true,
          title: {
            text: "<% out.print(session.getAttribute("title"));%>" + "<% out.print(session.getAttribute("year"));%>"
          },
          axisX: {
            title: "<% out.print(session.getAttribute("axisX"));%>"
          },
          axisY: {
            title: "<% out.print(session.getAttribute("axisY"));%>",
            includeZero: true
          },

          data: [{
            type: "<% out.print(session.getAttribute("chart"));%>", //change type to bar, line, area, pie, etc

            dataPoints: <% out.print(session.getAttribute("dataPoint2"));%>
				}]
			});
          chart.render();
			<% } %>
			//Reference the DropDownList.
        var ddlYears = document.getElementById("ddlYears");
 
        //Determine the Current Year.
        var currentYear = (new Date()).getFullYear();
 		// see if there is a selected year
        let selected_year = "<% out.print(session.getAttribute("nnyear"));%>"
        //selected_year = selected_year.substring(2, 6);
 		console.log(selected_year);
 		// insert that year first
 		if (selected_year != ""){ 
	 		var option = document.createElement("OPTION");
	        option.innerHTML = selected_year;
	        option.value = selected_year;
	        ddlYears.appendChild(option);
        }
        //Loop and add the Year values to DropDownList.
        for (var i = 2021; i <= currentYear+5; i++) {
        	if (i == selected_year && selected_year != "") continue;
            var option = document.createElement("OPTION");
            option.innerHTML = i;
            option.value = i;
            ddlYears.appendChild(option);
        }
}
</script>

</head>

<body class="pb-3">
	<div class="container-fluid">
		<!-- head -->
		
		<nav class="d-flex justify-content-around pt-4">
            <!-- Header -->
            <div>
		    </div>
            <div class="d-flex justify-content-center ms-5 w-40">
                <h5 class="rounded_ mhc text-center" style="width: 120%;height: 35px;line-height: 35px;">Medico Health Care</h5>

            </div>
            <!-- Logo -->
            <div class="d-flex justify-content-end">
					<a href="<%= request.getContextPath()%>/logout"><button type="button" class="btn btns rounded_ btn-primary p-2" style="width: 100%;height: 35px;line-height: 5px;}">Logout</button></a>
            </div>
        </nav>
        
        
        
        <div class="container pb-0 pt-3 mb-0" style="width: 30%">
		<c:choose>
			<c:when test="${not empty error}">
				<div class="alert alert-danger" role="alert">${error}</div>
			</c:when>
			<c:when test="${not empty addition}">
				<div class="alert alert-success" role="alert">
					<p>Doctor Details Inclusion Successful</p>
				</div>
			</c:when>
			<c:when test="${not empty downloadSuccess}">
				<div class="alert alert-success" role="alert">${downloadSuccess}</div>
			</c:when>			
		</c:choose>
	</div>
	
        
        
        
		<div class="row m-3 ">


			<!-- bar graph  -->
			<div class="col-sm-12 col-md-12 p-2 pb-5">
				<div id="barChart1" class="d-flex justify-content-center" style="height: 360px; width: 50%;"></div>
				<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
				<form class="form-container" action="<%=request.getContextPath() %>/admin/welcome" method="post">
				<div class="d-flex justify-content-around">
				<div class="form-check form-check-inline">
					<input class="form-check-input" type="radio"
						name="inlineRadioOptions" id="docPerspec-analysis" value="docPerspec" 
						<%=request.getParameter("inlineRadioOptions")
                    !=null?(request.getParameter("inlineRadioOptions").equals("docPerspec")?"checked":""):"checked" %>
						>
					<label class="form-check-label" for="docPerspec-analysis">Doctors Per Specialization
						Analysis</label>
				</div>
				
				<div class="form-check form-check-inline">
					<input class="form-check-input" type="radio"
						name="inlineRadioOptions" id="monthly-analysis" value="month" 
						<%=request.getParameter("inlineRadioOptions")
                    !=null?(request.getParameter("inlineRadioOptions").equals("month")?"checked":""):null %>
						>
					<label class="form-check-label" for="monthly-analysis">Monthly
						Analysis</label>
				</div>
				
				<div class="form-check form-check-inline">
					<input class="form-check-input" type="radio"
						name="inlineRadioOptions" id="specialist-analysis"
						value="specialist"
						<%=request.getParameter("inlineRadioOptions")
                    !=null?(request.getParameter("inlineRadioOptions").equals("specialist")?"checked":""):null %>
						> 
						<label
						class="form-check-label" for="specialist-analysis">Specialist
						Analysis</label>
				</div>
				<div>
				<label for="ddlYears"> Choose Year: </label>
 				<select id="ddlYears" value="yearChosen" name="year"></select>
 				</div>
 				</div>
				<button type="submit" class="btn btn-primary d-none" id="hidden-button">RadioButtonControl</button>
				</form>
			</div>
		</div>
		<div class="container">
			<div class="row d-flex justify-content-center">
				
				<div class="col-sm-12 col-md-7 pb-0 d-flex align-content-center "
					style="max-height: 350px; overflow: scroll;">
					<table id="barTable" class="table table-primary text-center">
						<thead class="thead-dark">
							<tr>
								<th scope="col"><% out.print(session.getAttribute("axisX"));%></th>
								<th scope="col"><% out.print(session.getAttribute("axisY"));%><% out.print(session.getAttribute("year"));%></th>
							</tr>
						</thead>
						<tbody id="barTableBody">
							<c:forEach items="${data2}" var="d2">
								<tr>
									<td><c:out value="${d2.getLabel()}" /></td>
									<td><c:out value="${d2.getY()}" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			<div class="col-12 justify-content-center d-flex pb-5">
					<button class="btn rounded_ btns btn-primary text-center p-2 mt-3" data-toggle="modal" data-target="#byMonth">Download
						Report</button>
			</div>
		</div>
		
		
		

		<div class="container pt-4">
			<form action="<%=request.getContextPath() %>/admin/searchDoctor"
				method="post">
				<div class="row">
					<!-- search bar -->
					<div class="d-inline col-xl-4 col-sm-12">
						<input type="text" name="searchParam" placeholder="Search Doctor"
							class="search" style="width:66%"/>
						<button type="submit" id="searchButton"
							class="btn rounded_ btns btn-primary p-2 mx-2">Search</button>
					</div>
					<!-- search by button  -->
					<div class="col-xl-4 col-sm-12 p-2">
						<div class="d-flex w-100 justify-content-around">
							<label for="role" class="color-blue fs-4">Search By</label> <select
								id="role" class="w-50 form-control shadow" name="searchByOption">
								<%= request.getParameter("searchByOption") !=null ?
                      (request.getParameter("searchByOption").equals("specialization")
                      ? "<option value='specialization'>Specialization</option><option value='name'>Name</option>"
                      :null) :"<option value='name'>Name</option>" %>
								<%= request.getParameter("searchByOption") !=null ?
                        (request.getParameter("searchByOption").equals("name")
                        ? "<option value='name'>Name</option><option value='specialization'>Specialization</option>"
                        :null) :"<option value='specialization'>Specialization</option>" %>
							</select>
						</div>
					</div>
					<!-- sort by radio part -->
					<div class="col-xl-4 col-sm-12 d-flex justify-content-around p-2">
						<div>
							<label for="role" class="color-blue fs-4">Sort By</label>
						</div>
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" name="radioOption"
								value="fees" id="inlineRadio1"
								<%=request.getParameter("radioOption")
                    !=null?(request.getParameter("radioOption").equals("fees")?"checked":""):null %>>
							<label class="form-check-label color-blue fs-5"
								for="inlineRadio1">Fees</label>
						</div>
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" name="radioOption"
								value="upvotes" id="inlineRadio2"
								<%=request.getParameter("radioOption")
                    !=null?(request.getParameter("radioOption").equals("upvotes")?"checked":""):null %>>
							<label class="form-check-label color-blue fs-5"
								for="inlineRadio1">Upvotes</label>
						</div>
					</div>
				</div>
			</form>


		</div>
		<div style="max-height: 460px; overflow: scroll;">
			<c:choose>
				<c:when test="${not empty allDoctors}">
					<ul id="doctorul" class="">
						<c:forEach var="doctor" items="${allDoctors}">
							<li class="w-96" style="list-style: none">
								<div class="row" id="box"
									style="display: flex; justify-content: space-around; margin-top: 20px;">
									<div class="col-4" style="display: flex; align-items: center;">
										<div class="col-4">
											<img
												src="${pageContext.request.contextPath}/docImages/${doctor.getDoctor_id()}/${doctor.getProfile_pic()}"
												alt="Doctor's Profile Picture"
												width="100" height="100" class="rounded_">
										</div>
										<div class="col-8">
											<p>
												<b>${doctor.getName()}</b>
											</p>
											<p>${doctor.getSpecialization()}</p>
											<p>fees: ${doctor.getFees()}</p>
										</div>
									</div>
									<div class="col-4"
										style="display: flex; align-items: center; justify-content: center">
										<div class="d-flex justify-content-center">
											<i onclick="increase_likes(this)"
												class="far fa-heart my-auto"></i>
											<p id="like_no" class="px-2 my-auto" style="color: #115CCD;">
												${doctor.getUpvotes()}</p>
											<p class="my-auto">
												<span class="fw-400" style="color: #115CCD;">Upvotes!</span>
											</p>
										</div>
									</div>
									<div class="col-4"
										style="display: flex; align-items: center; justify-content: center;">
										<!-- add doctor modal button trigger  -->
										<button type="button"
											class="btn btm-primary rounded_ btns w-auto p-2"
											data-toggle="modal" data-target="#exampleModal">
											Edit</button>
									</div>
								</div>
							</li>
						</c:forEach>
					</ul>
				</c:when>
				<c:otherwise>
					<div class="alert alert-danger" role="alert">No Records
						Found.</div>
				</c:otherwise>
			</c:choose>


			<!-- modal  -->
			<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
				aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog modal-xl" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalLabel"
								style="color: #115CCD;">Edit</h5>
							<button type="button" class="close btn btns rounded_"
								data-dismiss="modal" aria-label="Close">
								<i class="far fa-times-circle"></i>
							</button>
						</div>
						<div class="modal-body">
							<div class="row">
								<!--  <div class="col-3">
									<img style="max-height: 100%; max-width: 100%"
										src="${pageContext.request.contextPath}/Images/empty profile.png">
								</div> -->
								<div class="col-9">
									<form action="<%=request.getContextPath() %>/admin/edit"
									method="post" >
									<div class="form-group">
										<input type="text" class="form-control shadow inputborder"
											id="Name" name="name" placeholder="Name" required>
									</div>
									<div class="form-group py-2 d-flex">
										<input type="number"
											class="form-control shadow inputborder rightmargin2"
											id="Fees" name="fees" placeholder="Fees"> <input
											type="date" class="form-control shadow inputborder"
											id="Pstart" name="practice_started"
											placeholder="practice started" required>
									</div>
									<div class="form-group py-2 d-flex">
										<input type="text"
											class="form-control shadow inputborder rightmargin2"
											id="Degree" name="degree" placeholder="Degree"> <input
											type="text" class="form-control shadow inputborder"
											id="Specialization" name="specialization"
											placeholder="Specialization" required>
									</div>
									<div class="form-group py-2 d-flex">
										<input type="email"
											class="form-control shadow inputborder rightmargin2"
											id="Email" name="username" placeholder="Email"> <input
											type="password" class="form-control shadow inputborder"
											id="Password" name="password" placeholder="Password" required>
									</div>
									<div class="form-group" style="height: 200px;">
										<input type="text" class="form-control shadow inputborder"
											style="line-height: 200px;" id="Description" name="descr"
											placeholder="Description" required>
									</div>
									<div class="justify-content-center d-flex mt-4 p-2">
										<button type="submit" class="btn btns rounded_ btn-primary">Edit</button>
									</div>
								</form>								
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- report generation modal  -->
        <div class="modal fade" id="byMonth" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle"
            aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <p class="modal-title" id="byMonthTitle">Choose the report format</p>
                    </div>
                    <div class="modal-body">
                        <div class="row justify-content-between">
                            <div class="col-6 p-1">
                                <button id="admin_pdf" type="button" class="btn rounded_ btns px-2">Download PDF </button>
                            </div>
                            <div class="col-6 p-1 text-end">
                            	<!-- <a href="<%=request.getContextPath() %>/admin/downloadExcel"> -->
	                                <button id="admin_excel" type="button" class="btn rounded_ btns px-2">Download Excel
	                                </button>
                                <!-- </a> -->
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btns rounded_" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>

		</div>
	</div>


	
	
	<div class="d-flex justify-content-end pt-2">
		<!-- add doctor modal button trigger  -->
		<button type="button" class="btn btm-primary rounded_ btns w-auto p-2"
			data-toggle="modal" data-target="#addModal">
			<i class="fas fa-plus rounded_ w-100 d-inline"></i> Add a Doctor
		</button>
		<!-- Adding Doctor modal  -->
		<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-xl" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLabel"
							style="color: #115CCD;">Add a Doctor</h5>
						<button type="button" class="close btn btns rounded_" data-dismiss="modal" aria-label="Close">
							<i class="far fa-times-circle"></i>
						</button>
					</div>
					<div class="modal-body">
						<div class="row">
							<form action="<%=request.getContextPath() %>/admin/add" method="post" enctype="multipart/form-data">
								<div class="col-9 d-flex ">
									<!-- <img style="max-height: 100%; max-width: 100%"
										src="${pageContext.request.contextPath}/Images/empty profile.png">
										 -->
										
										 <p>Upload Image</p>
									<input type="file" name="file" class="mx-3" />
								</div>
								<div class="col-9">
									<div class="form-group">
										<input type="text" class="form-control shadow inputborder"
											id="Name" name="name" placeholder="Name" required>
									</div>
									<div class="form-group py-2 d-flex">
										<input type="number"
											class="form-control shadow inputborder rightmargin2"
											id="Fees" name="fees" placeholder="Fees"> <input
											type="date" class="form-control shadow inputborder"
											id="PracticeStarted" name="practice_started"
											placeholder="practice started" required>
									</div>
									<div class="form-group py-2 d-flex">
										<input type="text"
											class="form-control shadow inputborder rightmargin2"
											id="Degree" name="degree" placeholder="Degree"> <input
											type="text" class="form-control shadow inputborder"
											id="Specialization" name="specialization"
											placeholder="Specialization" required>
									</div>
									<div class="form-group py-2 d-flex">
										<input type="email"
											class="form-control shadow inputborder rightmargin2"
											id="Email" name="username" placeholder="Email"> <input
											type="password" class="form-control shadow inputborder"
											id="Password" name="password" placeholder="Password" required>
									</div>
									<div class="form-group" style="height: 200px;">
										<input type="text" class="form-control shadow inputborder" style="line-height: 200px;" id="Description" name="descr" placeholder="Description" required>
									</div>
									<div class="justify-content-center d-flex mt-4 p-2">
										<button type="submit" class="btn btns rounded_ btn-primary">Add</button>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript" src="${pageContext.request.contextPath}/JS/datecheck.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/JS/doctorFilter.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/JS/ChartChanger.js"></script>
	<script src="${pageContext.request.contextPath}/JS/admin_report_generation.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/feather-icons/dist/feather.min.js"></script>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"
		integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>
	<script>
        function increase_likes(x) {
          x.classList.toggle("fas");
          // document.getElementById("like_no").value += 1;
        }
      </script>

</body>

</html>