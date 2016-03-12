<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Meal</title>
</head>
<body>
<a href="<c:url value="/"/>">Home</a>
<ul>
    <li><a href="<c:url value="/users"/>">Users</a></li>
    <li><a href="<c:url value="/meals"/>">Meals</a></li>
</ul>
<hr>
<form method="post" action="meals">
    <jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.UserMeal"/>
    <input name="id" type="hidden" value="${meal.id}">
    <input name="date" type="datetime-local" value="${meal.dateTime}">
    <input name="descr" type="text" value="${meal.description}">
    <input name="cal" type="number" value="${meal.calories}">
    <input type="submit" value="Submit"/>
</form>

</body>
</html>
