<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="navbar" tagdir="/WEB-INF/tags" %>

<!doctype html>
<html lang="en">
    <head>
        <jsp:include page="parts/load-header.jsp"/>

        <c:if test="${sessionScope.locale != null}">
        	<fmt:setLocale value="${sessionScope.locale}"/>
        </c:if>

        <fmt:setBundle basename="localization" var="bundle"/>

        <title><fmt:message key="createService" bundle="${bundle}"/></title>
    </head>

    <body>
        <navbar:navbar/>

        <div class="container mt-3 mb-3">

            <c:if test="${pageContext.request.getParameter('valid_message') eq 'new_service_unsuccessful'}">
                <div class="row justify-content-center">
                    <div class="w-75 alert alert-danger alert-dismissible fade show" role="alert">
                        <button type="button" class="btn-close btn-sm" data-bs-dismiss="alert"></button>
                        <fmt:message key="newServiceUnsuccessful" bundle="${bundle}"/>
                    </div>
                </div>
            </c:if>

        	<div class="row">
        		<div class="col"></div>

        		<div class="col bg-light p-4 rounded">
        			<h2 class="text-center">
        			    <fmt:message key="createService" bundle="${bundle}"/>
        			    <i class="bi bi-clipboard-plus-fill"></i>
        			</h2>

        			<form accept-charset="UTF-8" role="form" method="post"
        			    action="${pageContext.request.contextPath}/admin/createService">

        				<div class="mb-3 mt-3">
        					<label for="name"><fmt:message key="serviceName" bundle="${bundle}"/>:</label>
        					<input class="form-control" type="text" name="name" id="name" required>
        				</div>

        				<div class="mb-3">
        					<label for="desc"><fmt:message key="description" bundle="${bundle}"/>:</label>
        					<textarea class="form-control" rows="5" name="desc" id="desc" required></textarea>
        				</div>

        				<input class="w-100 btn btn-outline-primary" type="submit"
        				    value="<fmt:message key="createService" bundle="${bundle}"/>">
        			</form>
        		</div>

        		<div class="col"></div>
        	</div>
        </div>

        <jsp:include page="parts/footer.jsp" />
    </body>
</html>