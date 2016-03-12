<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Meals</title>
    <style>
        tr {
            color: green;
        }

        th {
            color: black;
        }

        .exceed {
            color: red;
        }

        /*table{
            border: solid;
        }*/
    </style>
</head>
<body>
<a href="<c:url value="/"/>">Home</a>
<ul>
    <li><a href="users">Users</a></li>
    <li><a href="meals">Meals</a></li>
</ul>
<hr>
<a href="<c:url value="/meals?action=create"/>">Add</a>
<table border="1" cellpadding="8" cellspacing="0">
    <thead>
    <th>Date</th>
    <th>Description</th>
    <th>Calories</th>
    <th></th>
    <th></th>
    </thead>
    <c:forEach items="${meals}" var="meal">
        <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.UserMealWithExceed"/>
        <tr class="${meal.exceed?'exceed':''}">
            <td>${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="<c:url value="/meals?action=update&&id=${meal.id}"/>">Edit</a></td>
            <td><a href="<c:url value="/meals?action=delete&&id=${meal.id}"/>">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
