<%@page import="org.springframework.ui.Model"%>
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
    <style><%@include file="/WEB-INF/jsp/Assets/CSS/style.css"%></style>
    <style><%@include file="/WEB-INF/jsp/Assets/CSS/login.css"%></style>
    
    <title>Login</title>
</head>

<body class="mx-4 pt-2 ">
    <div class="body-container">

        <nav class="d-flex justify-content-end px-4">
            <p class="pt-1 px-4 fs-6 color-blue">New User?</p>
            <!-- Register Button -->
            <a href="<%=request.getContextPath() %>/register"><button class="btn rounded_ btns px-2 ">Register</button></a>
        </nav>
        <div class="row justify-content-center">
            <!-- Header row -->
            <div class=" col-sm-12 col-md-6 justify-content-center left-container">
                <div class="d-flex justify-content-center">
                    <h5 class="rounded_ mhc text-center p-2">Medico Health Care</h5>
                </div>
                <div class="d-flex justify-content-center text-center d-desktop">
                    <img src="${pageContext.request.contextPath}/Images/login_doctor_image.png" alt="avatar" width="525" height="540">
                </div>
            </div>
            <!-- Right Container - right screen -->
            <div class=" col-sm-12 col-md-6 right-container">
                <div class="login-form m-5 p-5">
                    <form class="form-container" action="<%=request.getContextPath() %>/doLogin" method="post">
                        <div class="form-group row">
                        	<c:if test="${not empty error}">
	                        	<div class="alert alert-danger" role="alert">
								  ${error}
								</div>
                        	</c:if>
                            <div class="d-flex py-5 w-100 justify-content-start">
                                <label for="role" class="color-blue fs-4">Select Role&nbsp;&nbsp;&nbsp;&nbsp;</label>
                                <select id="role" class="w-50 form-control shadow" name="type">
                                    <option value="admin">Admin</option>
                                    <option value="doctor">Doctor</option>
                                    <option value="patient">Patient</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="d-flex py-5 w-100 justify-content-start">
                                <label for="email" class="color-blue fs-4">Email
                                    ID&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                                <input type="email" id="email" class="w-50 form-control color-blue shadow" name="username" required>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="d-flex py-5 w-100 justify-content-start">
                                <label for="password"
                                    class="color-blue fs-4">Password&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                                <input type="password" id="password" class="w-50 form-control color-blue shadow" name="password" required>
                            </div>
                        </div>
                        <!-- Sign In Button -->
                        <div class="d-flex justify-content-center p-2">
                        	<button type="submit" class="btn rounded_ btns text-center ">Sign In</button>
                        </div>
                    </form>
                </div>
            </div>

        </div>
        <footer>
            <br>
            <div class="d-flex justify-content-end px-5"><a href="mailto:rudrajitc@trainee.nrifintech.com">Contact Us @
                    Medico</a></div>
        </footer>
    </div>
</body>

</html>