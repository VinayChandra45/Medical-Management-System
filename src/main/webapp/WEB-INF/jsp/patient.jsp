<%@ page import="org.springframework.ui.Model"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
	<!-- Linking CSS File -->
	<style>
		<%@include file="/WEB-INF/jsp/Assets/CSS/style.css"%>
	</style>
	<style>
		<%@include file="/WEB-INF/jsp/Assets/CSS/login.css"%>
	</style>
	<style>
		<%@include file="/WEB-INF/jsp/Assets/CSS/Patient.css"%>
	</style>
	<!-- FontAwesome CSS -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta2/css/all.min.css"
		integrity="sha512-YWzhKL2whUzgiheMoBFwW8CKV4qpHQAEuvilg9FAn5VJUDwKZZxkJNuGM4XkWuk94WCrrwslk8yWNGmY1EduTA=="
		crossorigin="anonymous" referrerpolicy="no-referrer" />
	<!-- JavaScript Bundle with Popper -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous">
	</script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
	<title>Patient</title>
</head>

<body>
	<div class="container">
		<nav class="d-flex justify-content-around pt-4">
			<div></div>

			<!-- Header -->
			<div class="d-flex justify-content-center ms-5 w-40">
				<h5 class="rounded_ mhc text-center">Medico Health Care</h5>
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
						<li>
							<button type="button" class="btn dropdown-item" data-toggle="modal"
								data-target="#editProfile">
								Edit Profile
							</button>
						</li>

						<li><a class="dropdown-item" href="<%= request.getContextPath()%>/logout">Logout</a></li>
					</ul>
				</div>
			</div>
		</nav>
		
			<c:if test="${not empty Message}">
				<div class="alert alert-warning alert-dismissible fade show" role="alert">
				  ${Message}
				  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
				    <span aria-hidden="true">&times;</span>
				  </button>
				</div>
			
			</c:if>
		
		<div class="color">
			<H2>Welcome <%= session.getAttribute("patientFname")%></H2>
		</div>
		<form action="<%=request.getContextPath() %>/patient/searchDoctor" method="post">
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
						<label for="role" class="color-blue fs-4">Search By</label>
						<select id="role" class="w-50 form-control shadow" name="searchByOption">
							<%= request.getAttribute("searchByOption") != null ? 
	                        		(request.getAttribute("searchByOption").equals("specialization") ? "<option value='specialization'>Specialization</option><option value='name'>Name</option>":null)
	                        		:"<option value='name'>Name</option>" %>
							<%= request.getAttribute("searchByOption") != null ? 
	                        		(request.getAttribute("searchByOption").equals("name") ? "<option value='name'>Name</option><option value='specialization'>Specialization</option>":null)
	                        		:"<option value='specialization'>Specialization</option>" %>
						</select>
					</div>
				</div>
				<!-- sort by radio part -->
				<div class="col-xl-4 col-sm-12 d-flex justify-content-around p-2">
					<div>
						<label for="role" class="color-blue fs-4">Sort By</label>
					</div>
					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio" name="radioOption" value="fees" id="inlineRadio1"
							<%= request.getAttribute("radioOption") != null?(request.getAttribute("radioOption").equals("fees")?"checked":""):null %>>
						<label class="form-check-label color-blue fs-5" for="inlineRadio1">Fees</label>
					</div>
					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio" name="radioOption" value="upvotes"
							id="inlineRadio2"
							<%= request.getAttribute("radioOption") != null?(request.getAttribute("radioOption").equals("upvotes")?"checked":""):null %>>
						<label class="form-check-label color-blue fs-5" for="inlineRadio1">Upvotes</label>
					</div>
				</div>
			</div>
		</form>
		<div>
			<c:choose>
				<c:when test="${not empty allDoctors}">
					<ul id="doctorul" class="">
						<c:forEach var="doctor" items="${allDoctors}">
							<li class="w-96">
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
											<i onclick="increase_likes(this)" class="far fa-heart my-auto"></i>
											<p id="like_no" class="px-2 my-auto" style="color: #115CCD;">
												${doctor.getUpvotes()}</p>
											<p class="my-auto">
												<span class="fw-400" style="color: #115CCD;">Upvotes!</span>
											</p>
										</div>
									</div>
									<div class="col-4"
										style="display: flex; align-items: center; justify-content: center;">
										<form
											action="<%=request.getContextPath() %>/doctor/viewdoctor/${doctor.getDoctor_id()}"
											method="post">
											<button type="submit" class="btn rounded_ btns text-center p-2">
												View</button>
										</form>
									</div>
								</div>
							</li>
						</c:forEach>
					</ul>
				</c:when>
				<c:otherwise>
					<div class="alert alert-danger" role="alert">
						No Records Found.
					</div>
				</c:otherwise>
			</c:choose>

		</div>
		<footer>
			<div class="d-flex justify-content-end px-5 py-2">
				<a href="<%=request.getContextPath()%>/patient/manage"><button type="submit"
						class="btn rounded_ btns text-center p-2">Manage</button></a>

			</div>
		</footer>
	</div>

	<!-- modal  -->
	<div class="modal fade" id="editProfile" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
		aria-hidden="true">
		<div class="modal-dialog modal-xl" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel" style="color: #115CCD;">Edit Profile</h5>
					<button type="button" class="close btn  " data-dismiss="modal" aria-label="Close">
						<i class="far fa-times-circle"></i>
					</button>
				</div>

				<div class="modal-body">

					<div class="row">
						<div class="pt-2">
							<form action="<%=request.getContextPath() %>/patient/editProfile/name" method="post">
								<div class="form-group">
									<input type="text" class="form-control shadow inputborder" id="name" name="name"
										placeholder="Enter a new name" required>
									<div class="justify-content-end d-flex mt-1 p-1">
										<button type="submit" class="btn btns rounded_ p-1">Update Name</button>
									</div>
								</div>
							</form>
						</div>
						<div class="pt-4">
							<form action="<%=request.getContextPath() %>/patient/editProfile/phone_no" method="post">
								<div class="form-group">
									<input type="number" class="form-control shadow inputborder" id="phone_no"
										name="phone_no" placeholder="Change your phone number" required>
									<div class="justify-content-end d-flex mt-1 p-1">
										<button type="submit" class="btn btns rounded_ p-1">Update Phone Number</button>
									</div>
								</div>
							</form>
						</div>

						<div class="col-12 pt-4">
							<form action="<%=request.getContextPath() %>/patient/editProfile/password" method="post">
								<div>
									<div class="form-group">
										<input type="password" class="form-control shadow inputborder"
											id="current_password" name="current_password"
											placeholder="Enter current password" required>
									</div>
								</div>
								<div class="form-group py-2 d-flex">
									<input type="password" class="form-control shadow inputborder" id="new_password"
										name="new_password" placeholder="New Password" required>
									<input type="password" class="form-control shadow inputborder" id="confirm_password"
										name="confirm_password" placeholder="Re-type New Password" required>
								</div>
								<div class="justify-content-end d-flex mt-1 p-1">
									<button type="submit" class="btn btns rounded_ btn-primary p-1">Update Password</button>
								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btns btn-outline-warning" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<script>
		function increase_likes(x) {
			x.classList.toggle("fas");
			// document.getElementById("like_no").value += 1;
		}
	</script>
	<!-- <script type="text/javascript"> let docList = '<%= request.getAttribute("allDoctors") %>'; </script> -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/JS/doctorFilter.js"></script>
</body>
</html>