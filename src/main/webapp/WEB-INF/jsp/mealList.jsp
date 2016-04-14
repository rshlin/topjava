<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meal list</title>
    <link rel="stylesheet" href="resources/css/style.css">
</head>
<body>
<section>
    <h2><a href="index.jsp"><fmt:message key="app.home"/></a></h2>
    <h3><fmt:message key="app.meal.list"/></h3>
    <form method="post" action="<c:url value="/meals/filter"/> ">
        <dl>
            <dt><fmt:message key="app.meal.do.filter.from.date"/></dt>
            <dd><input type="date" name="startDate" value="${startDate}"></dd>
        </dl>
        <dl>
            <dt><fmt:message key="app.meal.do.filter.to.date"/></dt>
            <dd><input type="date" name="endDate" value="${endDate}"></dd>
        </dl>
        <dl>
            <dt><fmt:message key="app.meal.do.filter.from.time"/></dt>
            <dd><input type="time" name="startTime" value="${startTime}"></dd>
        </dl>
        <dl>
            <dt><fmt:message key="app.meal.do.filter.to.time"/></dt>
            <dd><input type="time" name="endTime" value="${endTime}"></dd>
        </dl>
        <button type="submit"><fmt:message key="app.do.filter"/></button>
    </form>
    <hr>
    <a href="<c:url value="/meals/create"/> "><fmt:message key="app.meal.do.add"/></a>
    <hr>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th><fmt:message key="app.meal.date"/></th>
            <th><fmt:message key="app.meal.description"/></th>
            <th><fmt:message key="app.meal.calories"/></th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${mealList}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.UserMealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%=TimeUtil.toString(meal.getDateTime())%>
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="<c:url value="/meals/update?id=${meal.id}"/>"><fmt:message key="app.do.update"/></a></td>
                <td><a href="<c:url value="/meals/delete?id=${meal.id}"/>"><fmt:message key="app.do.delete"/></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>
