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

        <title><fmt:message key="order" bundle="${bundle}"/></title>
    </head>

    <body>
        <navbar:navbar/>

        <div class="container mt-3 mb-3">
        	<div class="row">
        		<div class="col"></div>
        		<div class="col bg-light p-4 rounded">
        			<h2 class="text-center"><fmt:message key="order" bundle="${bundle}"/> <i class="bi bi-clipboard-plus-fill"></i></h2>

        			<form accept-charset="UTF-8" role="form" action="${pageContext.request.contextPath}/order" method="post" id="order">
        				<div class="mb-3 mt-3">
                            <label for="service-id"><fmt:message key="selectService" bundle="${bundle}"/>:</label>
                            <select class="form-select" onchange="selectChange(this);" id="service-id" name="service-id" form="order" required>
                                <option selected disabled><fmt:message key="selectService" bundle="${bundle}"/></option>
                                <c:forEach items="${services}" var="service">
                                    <option value="${service.id}"><c:out value="${service.name}" /></option>
                                </c:forEach>
                            </select>
        				</div>

        				<div class="mt-3 mb-3">
        				    <label for="master-id"><fmt:message key="selectMaster" bundle="${bundle}"/>:</label>
        				    <div id="masters">
        				        <select class="form-select" id="master-id" name="master-id" form="order" required disabled>
                                    <option><fmt:message key="selectMaster" bundle="${bundle}"/></option>
                                </select>
        				    </div>
        				</div>

                        <div class="mt-3 mb-3">
                            <label for="calendar"><fmt:message key="selectDate" bundle="${bundle}"/>:</label>
        				    <input class="form-control" type="date" name="calendar" id="calendar" onchange="getDate(this);" required>
        				</div>

        				<div class="mt-3 mb-3">
        				    <label for="time"><fmt:message key="selectTime" bundle="${bundle}"/>:</label>
        				    <div id="time">
                                <select class="form-select" id="time" name="time" form="order" required disabled>
                                    <option><fmt:message key="selectTime" bundle="${bundle}"/></option>
                                </select>
                            </div>
        				</div>

        				<input class="w-100 btn btn-outline-primary" type="submit" value="<fmt:message key="submitOrder" bundle="${bundle}"/>">
        			</form>
        		</div>
        		<div class="col"></div>
        	</div>
        </div>

        <script type="text/javascript">
            window.onload=function(){
                var today = new Date().toISOString().split('T')[0];
                document.getElementsByName("calendar")[0].setAttribute('min', today);
            }
        </script>

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