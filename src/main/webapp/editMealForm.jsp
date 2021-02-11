<%--
  Created by IntelliJ IDEA.
  User: jok77
  Date: 08.02.2021
  Time: 13:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
    <style>
        .td {
            text-align: center;
            vertical-align: middle;
        }
    </style>
</head>
<body>
<table border="5">
    <tr align="center">
<%--        <td>ID </td>--%>
        <td>Date</td>
        <td>Description</td>
        <td>Calories</td>
        <td>Action</td>
    </tr>

<%--    <c:set var="isAdd" scope="session" value="${isAddMeal}"/>--%>
<%--    <c:if test="${isAdd}">--%>



    <tr align="center">
        <fmt:parseDate value="${mealObj.dateTime}" pattern="yyyy-MM-dd" var="parsedDateTime" type="date"/>
        <td><fmt:formatDate pattern="dd.MM.yyyy" value="${ parsedDateTime }"/></td>
        <td>${mealObj.description}</td>
        <td>${mealObj.calories}</td>
        <td>
            <form method="get" action="meals"><input type="submit" value="Back"></form>
        </td>
    </tr>
<%--    </c:if>--%>

    <tr>
        <form method="post" action="meals">
            <td><input type="datetime-local" name="date"  required value="<c:out value="${meal.dateTime}"/>"></td>
            <td><input type="text" name="nameMeal"  required value="<c:out value="${meal.description}"/>"></td>
            <td><input type="text" name="calories" pattern="^[0-9]{1,5}$" required value="<c:out value="${meal.calories}"/>"></td>
            <td><input type="submit" value="Enter"></td>
                <input type="hidden" name="id" value="<c:out value="${meal.id}"/>">
        </form>
    </tr>--%>
</table>
</body>
</html>

