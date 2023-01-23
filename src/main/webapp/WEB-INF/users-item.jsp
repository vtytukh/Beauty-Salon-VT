<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="navbar" tagdir="/WEB-INF/tags" %>

<!doctype html>
<html lang="en">
    <head>
        <jsp:include page="parts/load-header.jsp"/>

        <fmt:setBundle basename="localization" var="bundle"/>

        <title>Users Item</title>
    </head>

    <body>

        <navbar:navbar/>

        <div class="container mt-5 mb-5">
            <p><fmt:message key="id" bundle="${bundle}"/>: <c:out value="${user.id}" /></p>
            <p><fmt:message key="user" bundle="${bundle}"/>: <c:out value="${user.firstName}" />
                <c:out value="${user.lastName}" /></p>
            <p>Email: <c:out value="${user.email}" /></p>
            <p><fmt:message key="role" bundle="${bundle}"/>: <c:out value="${user.role.value()}" /></p>


            <c:if test="${user.role.value() == \"Client\"}">
                <form action="${pageContext.request.contextPath}/admin/users/setMaster?id=${user.id}" method="post">
                    <input class="btn btn-outline-primary" type="submit" value="<fmt:message key="setMaster" bundle="${bundle}"/>">
                </form>
            </c:if>
            <c:if test="${user.role.value() == \"Master\"}">
                <input class="btn btn-outline-primary" type="submit" value="<fmt:message key="addServiceToMaster" bundle="${bundle}"/>" id="add-service" onclick="addService();">
            </c:if>
            <div id="div-add-service" style="display: none">
                <c:if test="${user.role.value() == \"Master\"}">
                    <form action="${pageContext.request.contextPath}/admin/users/setService?id=${user.id}" method="post" id="services">
                        <div id="select-service"></div>
                        <input type="number" name="price" min="1" max="10000">
                        <input class="btn btn-outline-primary" type="submit" value="<fmt:message key="addService" bundle="${bundle}"/>">
                    </form>
                </c:if>
            </div>
        </div>

        <script defer>
            function addService() {
                var add = document.getElementById("div-add-service");
                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function() {
                    if (this.readyState == 4 && this.status == 200) {
                        document.getElementById("select-service").innerHTML = this.responseText;
                    }
                };
                xhttp.open("GET", "${pageContext.request.contextPath}/admin/users/getService?id=${user.id}", true);
                xhttp.send();
                add.style.display = "block";
            }
        </script>

        <jsp:include page="parts/footer.jsp" />
    </body>
</html>