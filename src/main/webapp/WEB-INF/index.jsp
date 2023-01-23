<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="navbar" tagdir="/WEB-INF/tags" %>

<!doctype html>
<html lang="en">

    <head>
        <jsp:include page="parts/load-header.jsp"/>

        <fmt:setBundle basename="localization" var="bundle"/>

        <title>Beauty Salon VT</title>
    </head>

    <body>

    <navbar:navbar/>

    <div class="container">
        <hr>

        <select onchange="filterServiceByMaster(this);">
            <option selected value="0"><fmt:message key="allServices" bundle="${bundle}"/></option>
            <c:forEach items="${masters}" var="master">
                <option value="<c:out value="${master.id}" />">
                    <c:out value="${master.user.firstName}" /> <c:out value="${master.user.lastName}" />
                </option>
            </c:forEach>
        </select>

        <div id="services" class="container">
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

        <hr>

        <select onchange="filterMasterByService(this);">
            <option selected value="0"><fmt:message key="allMasters" bundle="${bundle}"/></option>
            <c:forEach items="${services}" var="service">
                <option value="<c:out value="${service.id}" />"><c:out value="${service.name}" /></option>
            </c:forEach>
        </select>

        <select onchange="orderBy();" id="order-column">
            <option selected value="0"><fmt:message key="normalOrder" bundle="${bundle}"/></option>
            <option value="1"><fmt:message key="orderByName" bundle="${bundle}"/></option>
            <option value="2"><fmt:message key="orderByRate" bundle="${bundle}"/></option>
        </select>

        <select onchange="orderBy();" id="order-way">
            <option selected value="0">ASC</option>
            <option value="1">DESC</option>
        </select>

        <div id="masters" class="container">
        	<div class="d-flex flex-wrap align-content-center">
        		<c:forEach items="${masters}" var="master">
        			<div class="d-inline-flex p-2 bd-highlight">
        				<div class="card bg-light mt-2 mb-2" style="width: 15rem;">
        					<div class="card-body">
        						<h5 class="card-title"><c:out value="${master.user.firstName}" /> <c:out value="${master.user.lastName}" /></h5>
        						<p class="card-text"><c:out value="${master.mark}" /></p>
        					</div>
        				</div>
        			</div>
        		</c:forEach>
        	</div>
        </div>

        <hr>
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
        function filterMasterByService(service) {
            console.log(service);
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    document.getElementById("masters").innerHTML = this.responseText;
                }
            };
            xhttp.open("GET", "${pageContext.request.contextPath}/filterMasters?id="+service.value , true);
            xhttp.send();
        }
        function orderBy() {
            var column = document.getElementById("order-column").value;
            var way = document.getElementById("order-way").value;
            console.log(column);
            console.log(way);
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    document.getElementById("masters").innerHTML = this.responseText;
                }
            };
            xhttp.open("GET", "${pageContext.request.contextPath}/orderBy?id="+column+"&way="+way , true);
            xhttp.send();
        }
    </script>

    <jsp:include page="parts/footer.jsp" />
    </body>

</html>