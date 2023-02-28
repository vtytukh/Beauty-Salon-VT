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

        <title><fmt:message key="leaveComment" bundle="${bundle}"/></title>
    </head>

    <body>
        <navbar:navbar/>

        <div class="container mt-3 mb-3">
        	<div class="row">
        		<div class="col"></div>

        		<div class="col bg-light p-4 rounded">
        		    <h2 class="text-center"><fmt:message key="leaveComment" bundle="${bundle}"/> <i class="bi bi-star-half"></i></h2>

        			<p><fmt:message key="evaluateService" bundle="${bundle}"/> <strong>${record.userMaster.firstName} ${record.userMaster.lastName}</strong>,
        			<fmt:message key="providedService" bundle="${bundle}"/> <strong>${record.service.name}</strong>.</p>

        			<form accept-charset="UTF-8" role="form" method="post" id="mark-form"
        			    action="${pageContext.request.contextPath}/order/comment?id=${record.id}&master=${record.serviceMaster.master_id}">

        				<div class="mb-3 mt-3 text-center">
        				    <span><fmt:message key="serviceEvaluation" bundle="${bundle}"/>:</span><br/>
                            <input type="radio" class="btn-check" name="mark" value="1" id="option1" autocomplete="off" form="mark-form">
                            <label class="btn btn-outline-warning" for="option1">1</label>

                            <input type="radio" class="btn-check" name="mark" value="2" id="option2" autocomplete="off" form="mark-form">
                            <label class="btn btn-outline-warning" for="option2">2</label>

                            <input type="radio" class="btn-check" name="mark" value="3" id="option3" autocomplete="off" form="mark-form">
                            <label class="btn btn-outline-warning" for="option3">3</label>

                            <input type="radio" class="btn-check" name="mark" value="4" id="option4" autocomplete="off" form="mark-form">
                            <label class="btn btn-outline-warning" for="option4">4</label>

                            <input type="radio" class="btn-check" name="mark" value="5" id="option5" autocomplete="off" form="mark-form" checked>
                            <label class="btn btn-outline-warning" for="option5">5</label>
        				</div>

        				<div class="mb-3">
                            <label for="feedback"><fmt:message key="feedbackComment" bundle="${bundle}"/>:</label>
                            <textarea class="form-control" rows="5" name="feedback" id="feedback"></textarea>
        				</div>

        				<input class="w-100 btn btn-outline-primary" type="submit" value="<fmt:message key="leaveComment" bundle="${bundle}"/>">
        			</form>
        		</div>

        		<div class="col"></div>
        	</div>
        </div>

        <jsp:include page="parts/footer.jsp" />
    </body>
</html>