<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registration</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@300;400;500&display=swap" rel="stylesheet">
    <!--stylesheet related plugin-->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" />

    <!-- Linking CSS File -->
    <style>
        <%@include file="/WEB-INF/jsp/Assets/CSS/style.css"%>
    </style>
    <style>
        <%@include file="/WEB-INF/jsp/Assets/CSS/register.css"%>
    </style>
</head>

<body>
    <div class="container">
        <nav class="px-4">
            <!-- Header -->
            <div class="p-2 w-100 d-flex justify-content-center">
                <h5 class="rounded_ mhc text-center p-2 border_rd_10">Medico Health Care</h5>
            </div>
        </nav>
    </div>
    <div class="container pt-2">
        <!-- Logo -->
        <div class="d-flex justify-content-around pt-4">
            <div class="d-flex align-items-center">
                <h2 class="color-blue">Create account</h2>
            </div>
            <img src="${pageContext.request.contextPath}/Images/reg_logo.png" alt="picture" width="350" height="200"
                class="rounded_">
        </div>
        <div class="container pb-0 pt-3 mb-0" style="width: 79%">
            <c:if test="${not empty error}">
                <div class="alert alert-danger" role="alert">${error}</div>
            </c:if>
            <c:if test="${not empty registered}">
                <div class="alert alert-success" role="alert">${registered}</div>
            </c:if>
        </div>
        <!-- Form -->

        <form action="<%=request.getContextPath() %>/doRegister" method="post" class="p-2">

            <div class="d-flex pt-2 w-100 justify-content-center">
                <input type="text" class="form-control w-77 p-2 color-blue shadow" id="name" name="name"
                    placeholder="Name" required>
            </div>
            <div class="d-flex pt-3 w-100 justify-content-center">
                <input type="email" class="form-control w-77 p-2 color-blue shadow" id="email" name="username"
                    placeholder="Email ID" required>
            </div>
            <div class="d-flex pt-3 w-100 justify-content-center">
                <input type="text" class="form-control w-77 p-2 color-blue shadow" id="phone" name="phone_no"
                    placeholder="Phone" required>
            </div>
            <div class="d-flex pt-3 w-100 justify-content-center">
                <input type="password" class="form-control w-77 p-2 color-blue shadow" id="password" name="password"
                    placeholder="Password" required>
            </div>
            <div class="d-flex py-3 w-100 justify-content-center">
                <input type="password" class="form-control w-77 p-2 color-blue shadow" id="confirm_password"
                    name="confirm_password" placeholder="Confirm Password" required>
            </div>
            <div class="d-flex justify-content-center pt-3">
                <button type="submit" class="btn rounded_ btns p-2 text-center">Register</button>
                <!-- <button class="btn rounded_ btns p-2 text-center"  type="submit" name="camper" formaction="<%=request.getContextPath() %>/login"> Login</button>  -->
            </div>
        </form>
        <div class="d-flex justify-content-center pt-1">
        <span>Existing user? &nbsp;</span>
		<a href="<%=request.getContextPath() %>/login">Login</a>
		</div>
    </div>
</body>

</html>