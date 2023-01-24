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

        <title>Order comment</title>
    </head>

    <body>

        <navbar:navbar/>

        <div class="container">
            <h2><fmt:message key="evaluate" bundle="${bundle}"/> ${record.service.name}
                <fmt:message key="madeBy" bundle="${bundle}"/> ${record.userMaster.firstName} ${record.userMaster.lastName} </h2>
            <form action="${pageContext.request.contextPath}/order/comment?id=${record.id}&master=${record.serviceMaster.master_id}"
                  method="post" id="mark-form">
                <input type="radio" class="btn-check" name="mark" value="1" id="option1" autocomplete="off" form="mark-form">
                <label class="btn btn-secondary" for="option1">1</label>

                <input type="radio" class="btn-check" name="mark" value="2" id="option2" autocomplete="off" form="mark-form">
                <label class="btn btn-secondary" for="option2">2</label>

                <input type="radio" class="btn-check" name="mark" value="3" id="option3" autocomplete="off" form="mark-form">
                <label class="btn btn-secondary" for="option3">3</label>

                <input type="radio" class="btn-check" name="mark" value="4" id="option4" autocomplete="off" form="mark-form">
                <label class="btn btn-secondary" for="option4">4</label>

                <input type="radio" class="btn-check" name="mark" value="5" id="option5" autocomplete="off" form="mark-form">
                <label class="btn btn-secondary" for="option5">5</label>

                <input type="submit" value="<fmt:message key="leaveComment" bundle="${bundle}"/>" class="btn btn-success">
            </form>
        </div>

        <jsp:include page="parts/footer.jsp" />
    </body>
</html>