<%@include file="../jspf/page.jspf" %>
<html>
<c:set var="title" value="Edit Meal"/>
<%@include file="../jspf/head.jspf" %>
<body>
<%@include file="../jspf/menu.jspf" %>
<div class="jumbotron">
    <div class="container">
        <form method="post" action="meals" class="form-horizontal">
            <jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.UserMeal"/>
            <input name="id" type="hidden" value="${meal.id}">
            <div class="form-group">
                <div class="col-sm-2">
                    <label for="date">Date and Time: </label>
                </div>
                <div class="col-sm-6"><input id="date" name="date" class="form-control" type="datetime-local"
                                             value="${meal.dateTime}" required></div>
            </div>
            <div class="form-group">
                <div class="col-sm-1">
                    <label for="descr">Description: </label>
                </div>
                <div class="col-sm-6">
                    <input id="descr" name="descr" class="form-control" type="text" value="${meal.description}"
                           pattern="[a-zA-Z а-яА-Я]{3,20}" required>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-1">
                    <label for="cal">Calories: </label>
                </div>
                <div class="col-sm-6">
                    <input id="cal" name="cal" type="number" class="form-control" value="${meal.calories}"
                           pattern="[0-9]{1,4}" required>
                </div>
            </div>
            <div >
                <button type="submit" class="btn btn-primary">Submit</button>
                <button class="btn btn-default" onclick="window.history.back()"> Cancel</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>
