<%@include file="../jspf/page.jspf" %>
<html>
<c:set var="title" value="Meals"/>
<%@include file="../jspf/head.jspf" %>

<body>
<%@include file="../jspf/menu.jspf" %>
<div class="jumbotron">
    <div class="container">
        <a class="btn btn-info btn-sm" href="<c:url value="/meals?action=create"/>">Add</a>
        <table class="table table-striped">
            <thead>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            </thead>
            <c:forEach items="${meals}" var="meal">
                <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.UserMealWithExceed"/>
                <tr class="${meal.exceed?'text-danger':'text-success'}">
                    <td>${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}</td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                    <td><a class="btn btn-primary btn-xs" href="<c:url value="/meals?action=update&&id=${meal.id}"/>">Edit</a>
                    </td>
                    <td><a class="btn btn-danger btn-xs" href="<c:url value="/meals?action=delete&&id=${meal.id}"/>">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<%@include file="../jspf/footer.jspf" %>
</body>
</html>
