<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="navbar" tagdir="/WEB-INF/tags" %>

<!doctype html>
<html lang="en">
    <head>
        <jsp:include page="parts/load-header.jsp"/>
        <c:if test="${sessionScope.locale == null}">
        	<fmt:setLocale value="en"/>
        </c:if>
        <c:if test="${sessionScope.locale != null}">
        	<fmt:setLocale value="${sessionScope.locale}"/>
        </c:if>

        <fmt:setBundle basename="localization" var="bundle"/>

        <title>Order</title>
    </head>

    <body>

       <!-- <style>
            #order *{
                margin-top: 25px;
            }
            .order-container{
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                text-align: center;
                margin-top: 50px;
                border: lightcoral 4px solid;
                margin-right: 20px;
                background-color: bisque;
                border-radius: 8px;
                width: 30%;
                padding: 20px;
            }
            .order-container *{
                margin-top: 20px;
            }
        </style>-->

        <navbar:navbar/>

        <div class="container mt-5 mb-5">
        	<div class="row">
        		<div class="col"></div>
        		<div class="col bg-light p-4 rounded">
        			<h2 class="text-center"><fmt:message key="order" bundle="${bundle}"/></h2>
        			<form accept-charset="UTF-8" role="form" action="${pageContext.request.contextPath}/order" method="post" id="order">
        				<select class="mb-3" onchange="selectChange(this);" name="service-id" form="order" required>
        					<option selected disabled><fmt:message key="selectService" bundle="${bundle}"/></option>
        					<c:forEach items="${services}" var="service">
        						<option value="${service.id}"><c:out value="${service.name}" /></option>
        					</c:forEach>
        				</select>

        				<div class="mb-3" id="masters"></div>
        				<input type="date" name="calendar" id="calendar" onchange="getDate(this);" required>
        				<div class="mb-3" id="time"></div>

        				<input type="submit" value="<fmt:message key="submitOrder" bundle="${bundle}"/>">
        			</form>
        		</div>
        		<div class="col"></div>
        	</div>
        </div>

        <script defer>
            //document.getElementById('calendar').valueAsDate = new Date();
            function getDate(date) {
                console.log(date);
                var master = document.querySelector("#masters select[name=master-id]");
                console.log(master);
                var dateValue = date.value;
                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function() {
                    if (this.readyState == 4 && this.status == 200) {
                        document.getElementById("time").innerHTML = this.responseText;
                    }
                };
                xhttp.open("GET", "${pageContext.request.contextPath}/order/time?date="+dateValue+"&id="+master.value , true);
                xhttp.send();
            }
            function selectChange(select) {
                var idService = select.value;
                getMasters(idService);
            }
            function getMasters(id) {
                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function() {
                    if (this.readyState == 4 && this.status == 200) {
                        document.getElementById("masters").innerHTML = this.responseText;
                    }
                };
                xhttp.open("GET", "${pageContext.request.contextPath}/order/masters?id="+id , true);
                xhttp.send();
            }
        </script>

        <jsp:include page="parts/footer.jsp" />
    </body>
</html>