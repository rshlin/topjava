<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <nav>
        <h2>
            <a href="index.html">Home</a>
        </h2>
        <h2>
            <jsp:useBean id="profile" scope="session" type="ru.javawebinar.topjava.web.LoggedUser"/>
            <c:choose>
                <c:when test="${profile.id==-1}"> <a href="users?a=''">Sign Up</a></c:when>
                <c:otherwise><a href="users">Profile</a></c:otherwise>
            </c:choose>
        </h2>
    </nav>
    <h3>Meal list</h3>
    <a href="meals?action=create">Add Meal</a>
    <hr>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${mealList}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        ${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
<section style="padding: 10px">
    <form id="filter" method="get">
        <input type="hidden" name="action" value="filter">
        <div id="date">
            <label for="startDate">From Date:</label>
            <input id="startDate" name="startDate" type="date">
            <label for="endDate">To Date:</label>
            <input id="endDate" name="endDate" type="date">
        </div>
        <div id="time">
            <label for="startTime">From Time:</label>
            <input id="startTime" name="startTime" type="time">
            <label for="endTime">From Time:</label>
            <input id="endTime" name="endTime" type="time"></div>
        <div>
            <input type="submit" value="Filter"></div>
    </form>
</section>
</body>
</html>