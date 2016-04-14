<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<html>
<head>
    <title>Meal</title>
    <link rel="stylesheet" href="resource/css/style.css">
</head>
<body>
<section>
    <h2><a href=""><fmt:message key="app.home"/></a></h2>
    <h3><fmt:message key="app.meal.do.edit"/></h3>
    <hr>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.UserMeal" scope="request"/>
    <form method="post" action="<c:url value="/meals"/> ">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt><fmt:message key="app.meal.datetime"/>:</dt>
            <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime"></dd>
        </dl>
        <dl>
            <dt><fmt:message key="app.meal.description"/>:</dt>
            <dd><input type="text" value="${meal.description}" size=40 name="description"></dd>
        </dl>
        <dl>
            <dt><fmt:message key="app.meal.calories"/>:</dt>
            <dd><input type="number" value="${meal.calories}" name="calories"></dd>
        </dl>
        <button type="submit"><fmt:message key="app.do.save"/></button>
        <button onclick="window.history.back()"><fmt:message key="app.do.cancel"/></button>
    </form>
</section>
</body>
</html>
