<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Error</title>
</head>
<body>
Error in accessing <c:out value="${url}"/> <hr/>
<c:choose>
	<c:when test="${not empty exception}">
		${exception.localizedMessage}
	</c:when>
	<c:otherwise>
		<ol>
			<c:forEach var="error" items="${errors}">
				<li>${error}</li>
			</c:forEach>
		</ol>
	</c:otherwise>
</c:choose>
</body>
</html>