<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="navbar" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="rs" tagdir="/WEB-INF/tags" %>

<!doctype html>
<html lang="en">
    <head>
        <jsp:include page="parts/load-header.jsp"/>

        <c:if test="${sessionScope.locale != null}">
        	<fmt:setLocale value="${sessionScope.locale}"/>
        </c:if>

        <fmt:setBundle basename="localization" var="bundle"/>

        <title>My Orders</title>
    </head>

    <body>

        <navbar:navbar/>

        <div class="container">
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th scope="col"><fmt:message key="service" bundle="${bundle}"/></th>
                        <th scope="col"><fmt:message key="date" bundle="${bundle}"/></th>
                        <th scope="col"><fmt:message key="master" bundle="${bundle}"/></th>
                        <th scope="col"><fmt:message key="price" bundle="${bundle}"/></th>
                        <th scope="col"><fmt:message key="status" bundle="${bundle}"/></th>
                        <th scope="col"></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${records}" var="record">
                        <tr>
                            <th><c:out value="${record.service.name}" /></th>
                            <td><c:out value="${record.time}" /></td>
                            <td><c:out value="${record.userMaster.firstName}" /> <c:out value="${record.userMaster.lastName}" /></td>
                            <td><c:out value="${record.serviceMaster.price}" /></td>
                            <td class="status-id">
                                <c:out value="${record.status.value()}" />
                                <rs:record-status status="${record.status.value()}"/>
                            </td>
                            <td>
                                <c:if test="${record.status_id == 1}">
                                <form action="${pageContext.request.contextPath}/myOrders/paid?id=${record.id}" method="post" >
                                    <input type="submit" value="<fmt:message key="pay" bundle="${bundle}"/>" class="btn btn-warning">
                                </form>
                                </c:if>

                                <c:if test="${record.status_id == 4}">
                                    <a href="${pageContext.request.contextPath}/order/comment?id=${record.id}" class="btn btn-info">
                                        <fmt:message key="leaveComment" bundle="${bundle}"/></a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <jsp:include page="parts/footer.jsp" />
    </body>

</html>