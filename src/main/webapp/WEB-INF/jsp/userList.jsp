<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<section>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th><fmt:message key="app.user.name"/> </th>
            <th><fmt:message key="app.user.email"/> </th>
            <th><fmt:message key="app.user.roles"/> </th>
            <th><fmt:message key="app.user.active"/> </th>
            <th><fmt:message key="app.user.registered"/> </th>
        </tr>
        </thead>
        <c:forEach items="${userList}" var="user">
            <jsp:useBean id="user" scope="page" type="ru.javawebinar.topjava.model.User"/>
            <tr>
                <td><c:out value="${user.name}"/></td>
                <td><a href="mailto:${user.email}">${user.email}</a></td>
                <td>${user.roles}</td>
                <td><c:out value="${user.enabled}"/>
                </td>
                <td><fmt:formatDate value="${user.registered}" pattern="dd-MMMM-yyyy"/></td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
