<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<table border="5">
    <tr align="center">
        <td style="display:none;">ID</td>
        <td>Date</td>
        <td>Description</td>
        <td>Calories</td>
        <td>Update</td>
        <td>Delete</td>
    </tr>
    <c:forEach var="meal" items="${list}">
        <tr align="center" bgcolor="${meal.excess?'red':'green'}">
            <td style="display:none;">${meal.id }</td>
            <td>${meal.formattedDateTime}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=edit&userId=<c:out value="${meal.id}"/>">Update</a></td>
            <td><a href="meals?action=delete&userId=<c:out value="${meal.id}"/>">Delete</a></td>
        </tr>
    </c:forEach>
</table>
<p><a href="meals?action=insert">Add Meal</a></p>
</body>
</html>