<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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

        <title>Create service</title>
    </head>

    <body>
        <navbar:navbar/>

        <div class="container mt-5 mb-5">
        	<div class="row">
        		<div class="col"></div>

        		<div class="col bg-light p-4 rounded">
        			<h2 class="text-center"><fmt:message key="createService" bundle="${bundle}"/></h3>

        			<form accept-charset="UTF-8" role="form" method="post" action="${pageContext.request.contextPath}/admin/createService">

        				<div class="mb-3 mt-3">
        					<label for="name"><fmt:message key="serviceName" bundle="${bundle}"/>:</label>
        					<input class="form-control" type="text" name="name" id="name" required>
        				</div>

        				<div class="mb-3">
        					<label for="desc"><fmt:message key="description" bundle="${bundle}"/>:</label>
        					<textarea class="form-control" rows="5" name="desc" id="desc" required></textarea>
        				</div>

        				<button type="submit" class="w-100 btn btn-outline-primary"><fmt:message key="createService" bundle="${bundle}"/></button>
        			</form>
        		</div>

        		<div class="col"></div>
        	</div>
        </div>

        <jsp:include page="parts/footer.jsp" />
    </body>
</html>