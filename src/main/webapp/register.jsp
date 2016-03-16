<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
<form method="post">
    <label for="name">Name</label>
    <input id="name" name="name" type="text">
    <label for="email">Email</label>
    <input id="email" name="email" type="text">
    <label for="password">Password</label>
    <input id="password" name="password" type="password">
    <label for="caloriesPerDay">Calories</label>
    <input id="caloriesPerDay" name="caloriesPerDay" type="number" value="2000">
    <input type="submit" value="Submit">
</form>
</body>
</html>
