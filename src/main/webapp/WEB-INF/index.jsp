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

        <title>Beauty Salon VT</title>
    </head>

    <body>

    <navbar:navbar/>

    <div class="container mt-3 mb-3">

        <div class="container shadow-sm p-3 mb-3 bg-body rounded">
            <h2 class="text-center"><fmt:message key="serviceCatalog" bundle="${bundle}"/></h2>

            <div class="row">
                <div class="col-3">
                    <select class="form-select" onchange="filterServiceByMaster(this);">
                        <option selected value="0"><fmt:message key="allServices" bundle="${bundle}"/></option>
                        <c:forEach items="${masters}" var="master">
                            <option value="<c:out value="${master.id}" />">
                                <c:out value="${master.user.firstName}" /> <c:out value="${master.user.lastName}" />
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="container" id="services">
                <div class="d-flex flex-wrap align-content-center">
                    <c:forEach items="${services}" var="service">
                        <div class="d-inline-flex p-2 bd-highlight">
                            <div class="card bg-light mt-2 mb-2" style="width: 15rem;">
                                <div class="card-body">
                                    <h5 class="card-title"><c:out value="${service.name}" /></h5>
                                    <p class="card-text"><c:out value="${service.description}" /></p>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>

        <div class="d-flex justify-content-center">
            <hr class="w-50" />
        </div>

        <div class="container shadow-sm p-3 mb-5 bg-body rounded">
            <h2 class="text-center"><fmt:message key="hairdressersList" bundle="${bundle}"/></h2>

            <div class="row">
                <div class="col-3">
                    <select class="form-select" onchange="orderBy();" id="service-id">
                        <option selected value="0"><fmt:message key="allMasters" bundle="${bundle}"/></option>
                        <c:forEach items="${services}" var="service">
                            <option value="<c:out value="${service.id}" />"><c:out value="${service.name}" /></option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-3">
                    <select class="form-select" onchange="orderBy();" id="order-column">
                        <option selected value="0"><fmt:message key="normalOrder" bundle="${bundle}"/></option>
                        <option value="1"><fmt:message key="orderByName" bundle="${bundle}"/></option>
                        <option value="2"><fmt:message key="orderByRate" bundle="${bundle}"/></option>
                    </select>
                </div>

                <div class="col-2">
                    <select class="form-select" onchange="orderBy();" id="order-way">
                        <option selected value="0"><fmt:message key="orderASC" bundle="${bundle}"/></option>
                        <option value="1"><fmt:message key="orderDESC" bundle="${bundle}"/></option>
                    </select>
                </div>
            </div>

            <div class="container" id="masters">
                <div class="d-flex flex-wrap align-content-center">
                    <c:forEach items="${masters}" var="master">
                        <div class="d-inline-flex p-2 bd-highlight">
                            <div class="card bg-light mt-2 mb-2" style="width: 15rem;">
                                <div class="card-body">
                                    <h5 class="card-title"><c:out value="${master.user.firstName}" /> <c:out value="${master.user.lastName}" /></h5>
                                    <p class="card-text text-center">
                                        <c:forEach begin="1" end="5" var = "i">
                                            <c:choose>
                                                <c:when test="${i <= master.mark}">
                                                    <i class="bi bi-star-fill text-warning"></i>
                                                </c:when>
                                                <c:when test="${(i > master.mark) and (i - master.mark > 0) and (i - master.mark < 1)}">
                                                    <i class="bi bi-star-half text-warning"></i>
                                                </c:when>
                                                <c:otherwise>
                                                    <i class="bi bi-star text-warning"></i>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                        <br/><c:out value="${master.mark}" />
                                    </p>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>


    <script>
        function filterServiceByMaster(master) {
            console.log(master);
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    document.getElementById("services").innerHTML = this.responseText;
                }
            };
            xhttp.open("GET", "${pageContext.request.contextPath}/filterServices?id="+master.value , true);
            xhttp.send();
        }
        function orderBy() {
            var column = document.getElementById("order-column").value;
            var way = document.getElementById("order-way").value;
            var service = document.getElementById("service-id").value;
            console.log(service);
            console.log(column);
            console.log(way);
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    document.getElementById("masters").innerHTML = this.responseText;
                }
            };
            xhttp.open("GET", "${pageContext.request.contextPath}/orderBy?column="+column+"&way="+way+"&service="+service, true);
            xhttp.send();
        }
    </script>

    <jsp:include page="parts/footer.jsp" />
    </body>

</html>