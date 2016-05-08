<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<link rel="stylesheet" href="webjars/datatables/1.10.11/css/jquery.dataTables.min.css">
<link rel="stylesheet" href="webjars/datetimepicker/2.5.1/jquery.datetimepicker.css">
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron">
    <div class="container">
        <div class="shadow">
                <h3><fmt:message key="meals.title"/></h3>
                <form class="form-horizontal" action="" id="filter-form">
                    <div class="form-group">
                        <label for="startDate" class="control-label col-sm-2">From Date:</label>
                        <input type="date" class="col-sm-2" name="startDate" id="startDate" value="${startDate}">
                        <label for="endDate" class="control-label col-sm-2">To Date:</label>
                        <input type="date" class="col-sm-2" name="endDate" id="endDate" value="${endDate}">
                    </div>
                    <div class="form-group">
                        <label for="startTime" class="control-label col-sm-2">From Time:</label>
                        <input type="time" class="col-sm-2" name="startTime" id="startTime" value="${startTime}">
                        <label for="endTime" class="control-label col-sm-2">To Time:</label>
                        <input type="time" class="col-sm-2" name="endTime" id="endTime" value="${endTime}">

                    </div>
                    <button type="submit" class="btn btn-sm btn-primary pull-right">Filter</button>
                </form>
                <hr>
                <a class="btn btn-sm btn-info" id="add"><fmt:message key="meals.add"/></a>
                <hr>
                <table class="table table-striped display" id="datatable">
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
                        <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.UserMealWithExceed"/>
                        <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                            <td>
                                    <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                                <%=TimeUtil.toString(meal.getDateTime())%>
                            </td>
                            <td>${meal.description}</td>
                            <td>${meal.calories}</td>
                            <td><a class="btn btn-xs btn-primary edit" id="${meal.id}">Update</a></td>
                            <td><a class="btn btn-xs btn-danger delete" id="${meal.id}">Delete</a></td>
                        </tr>
                    </c:forEach>
                </table>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
<div class="modal fade" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title"><fmt:message key="meals.edit"/></h2>
            </div>
            <div class="modal-body">
                <form action="" class="form-horizontal" id="detailsForm">
                    <input type="text" id="id" hidden="hidden" name="id">
                    <div class="form-group">
                        <label for="dateTime" class="control-label col-xs-3">Date</label>
                        <div class="col-xs-9">
                            <input type="datetime-local" class="form-control" id="dateTime" name="dateTime"
                                   placeholder="Date">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="description" class="control-label col-xs-3">Description</label>
                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="description" name="description"
                                   placeholder="Description">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="calories" class="control-label col-xs-3">Calories</label>
                        <div class="col-xs-9">
                            <input type="number" class="form-group" id="calories" name="calories" placeholder="2000">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button type="submit" class="btn btn-primary">Save</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="webjars/jquery/2.2.3/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript" src="webjars/datetimepicker/2.5.1/jquery.datetimepicker.js"></script>
<script type="text/javascript" src="webjars/datatables/1.10.11/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="webjars/noty/2.3.8/js/noty/packaged/jquery.noty.packaged.min.js"></script>
<script type="text/javascript" src="resources/js/datatablesUtil.js"></script>
<script type="text/javascript">
    function makeFilterable() {
        $('#filter-form').submit(function () {
            filterTable();
            return false;
        });
    }
    function filterTable() {
        var filterForm = $('#filter-form');
        $.ajax({
            url: ajaxUrl + 'filter',
            type: "POST",
            data: filterForm.serialize(),
            success: function (data) {
                datatableApi.clear();
                $.each(data, function (key, item) {
                    datatableApi.row.add(item);
                });
                datatableApi.draw();
            }
        });
    }

    updateTable = filterTable;

    var ajaxUrl = 'ajax/meals/'
    var datatableApi;
    $(function () {
        datatableApi = $('#datatable').DataTable({
            "paging": false,
            "info": false,
            "columns": [
                {"data": "dateTime"},
                {"data": "description"},
                {"data": "calories"},
                {
                    "defaultContent": "",
                    "orderable": false
                },
                {
                    "defaultContent": "",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        });
        makeEditable();
        makeFilterable();
    })
</script>


</body>
</html>
