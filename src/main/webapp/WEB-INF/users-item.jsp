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
            <div class="d-flex justify-content-center">
                <div class="card col-5">
                    <div class="card-header">
                        <i class="bi bi-person-fill"></i> <fmt:message key="id" bundle="${bundle}"/>: <c:out value="${user.id}" />
                    </div>
                    <div class="card-body">
                        <h4 class="card-title"><fmt:message key="user" bundle="${bundle}"/>: <c:out value="${user.firstName}" /> <c:out value="${user.lastName}" /></h4>
                        <p class="card-text"><em>Email</em>: <c:out value="${user.email}" /></p>
                        <p class="card-text"><em><fmt:message key="role" bundle="${bundle}"/></em>: <fmt:message key="role${user.role.value()}" bundle="${bundle}"/></p>

                        <c:if test="${user.role.value() == \"Client\"}">
                            <div class="d-flex justify-content-center">
                                <form action="${pageContext.request.contextPath}/admin/users/setMaster?id=${user.id}" method="post">
                                    <input class="btn btn-outline-primary" type="submit" value="<fmt:message key="setMaster" bundle="${bundle}"/>">
                                </form>
                            </div>
                        </c:if>

                        <c:if test="${user.role.value() == \"Master\"}">
                            <div class="d-flex justify-content-center">
                                <input class="btn btn-outline-primary" type="submit" value="<fmt:message key="addServiceToMaster" bundle="${bundle}"/>" id="add-service" onclick="addService();">
                            </div>
                        </c:if>
                        <div class="mb-3 mt-3" id="div-add-service" style="display: none">
                            <c:if test="${user.role.value() == \"Master\"}">
                                <hr/>
                                <form action="${pageContext.request.contextPath}/admin/users/setService?id=${user.id}" method="post" id="services">
                                    <div class="row">
                                        <div class="col">
                                            <label for="service-id"><fmt:message key="selectServiceForAdding" bundle="${bundle}"/>:</label>
                                            <div id="select-service"></div>
                                        </div>
                                        <div class="col">
                                            <label for="price"><fmt:message key="servicePrice" bundle="${bundle}"/>:</label>
                                            <input type="number" class="form-control" name="price" id="price" min="1" max="10000" required>
                                        </div>
                                    </div>
                                    <div class="row d-flex justify-content-center">
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