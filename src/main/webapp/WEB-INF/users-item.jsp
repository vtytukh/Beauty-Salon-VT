<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="navbar" tagdir="/WEB-INF/tags" %>

<!doctype html>
<html lang="en">
    <head>
        <jsp:include page="parts/load-header.jsp"/>

        <c:if test="${sessionScope.locale != null}">
        	<fmt:setLocale value="${sessionScope.locale}"/>
        </c:if>

        <fmt:setBundle basename="localization" var="bundle"/>

        <title><fmt:message key="usersItem" bundle="${bundle}"/></title>
    </head>

    <body>

        <navbar:navbar/>

        <div class="container mt-3 mb-3">
            <h2 class="text-center"><fmt:message key="usersItem" bundle="${bundle}"/> <i class="bi bi-person-fill"></i></h2>

            <p><fmt:message key="id" bundle="${bundle}"/>: <c:out value="${user.id}" /></p>
            <p><fmt:message key="user" bundle="${bundle}"/>: <c:out value="${user.firstName}" />
                <c:out value="${user.lastName}" /></p>
            <p>Email: <c:out value="${user.email}" /></p>
            <p><fmt:message key="role" bundle="${bundle}"/>: <c:out value="${user.role.value()}" /></p>
<!--
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
            </div>-->

            <div class="card col-5">
            	<div class="card-header">
            		<i class="bi bi-person-fill"></i> <fmt:message key="id" bundle="${bundle}"/>: <c:out value="${user.id}" />
            	</div>
            	<div class="card-body">
            		<h4 class="card-title"><c:out value="${user.firstName}" /> <c:out value="${user.lastName}" /></h4>
            		<p class="card-text">Email: <c:out value="${user.email}" /></p>
            		<p class="card-text"><fmt:message key="role" bundle="${bundle}"/>: <fmt:message key="role${user.role.value()}" bundle="${bundle}"/></p>

            		<c:if test="${user.role.value() == \"Client\"}">
                        <form action="${pageContext.request.contextPath}/admin/users/setMaster?id=${user.id}" method="post">
                            <input class="btn btn-outline-primary" type="submit" value="<fmt:message key="setMaster" bundle="${bundle}"/>">
                        </form>
                    </c:if>
                    <c:if test="${user.role.value() == \"Master\"}">
                        <input class="btn btn-outline-primary" type="submit" value="<fmt:message key="addServiceToMaster" bundle="${bundle}"/>" id="add-service" onclick="addService();">
                    </c:if>
                    <div class="mb-3 mt-3" id="div-add-service" style="display: none">
                        <c:if test="${user.role.value() == \"Master\"}">
                            <hr/>

                            <form action="${pageContext.request.contextPath}/admin/users/setService?id=${user.id}" method="post" id="services">
                                <div class="row">
                                    <div class="col" id="select-service"></div>
                                    <div class="col">
                                        <input type="number" class="form-control" name="price" min="1" max="10000">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-6 mt-3">
                                        <input class="btn btn-outline-primary form-control" type="submit" value="<fmt:message key="addService" bundle="${bundle}"/>">
                                    </div>
                                </div>
                            </form>

                        </c:if>
                    </div>
            	</div>
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