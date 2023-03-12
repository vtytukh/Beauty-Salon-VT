<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="navbar" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="rs" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="rd" tagdir="/WEB-INF/tags" %>

<!doctype html>
<html lang="en">
    <head>
        <jsp:include page="parts/load-header.jsp"/>

        <c:if test="${sessionScope.locale != null}">
        	<fmt:setLocale value="${sessionScope.locale}"/>
        </c:if>

        <fmt:setBundle basename="localization" var="bundle"/>

        <title><fmt:message key="myOrders" bundle="${bundle}"/></title>
    </head>

    <body>

        <navbar:navbar/>

        <div class="container mt-3 mb-3">

            <c:if test="${pageContext.request.getParameter('valid_message') eq 'added_success'}">
                <div class="row justify-content-center">
                    <div class="w-75 alert alert-success alert-dismissible fade show" role="alert">
                        <button type="button" class="btn-close btn-sm" data-bs-dismiss="alert"></button>
                        <fmt:message key="addedRecordSuccessfully" bundle="${bundle}"/>
                    </div>
                </div>
            </c:if>

            <c:if test="${pageContext.request.getParameter('valid_message') eq 'feedback_success'}">
                <div class="row justify-content-center">
                    <div class="w-75 alert alert-success alert-dismissible fade show" role="alert">
                        <button type="button" class="btn-close btn-sm" data-bs-dismiss="alert"></button>
                        <fmt:message key="addedFeedbackSuccessfully" bundle="${bundle}"/>
                    </div>
                </div>
            </c:if>

            <c:if test="${pageContext.request.getParameter('valid_message') eq 'feedback_unsuccessful'}">
                <div class="row justify-content-center">
                    <div class="w-75 alert alert-danger alert-dismissible fade show" role="alert">
                        <button type="button" class="btn-close btn-sm" data-bs-dismiss="alert"></button>
                        <fmt:message key="addedFeedbackUnsuccessfully" bundle="${bundle}"/>
                    </div>
                </div>
            </c:if>

            <h2 class="text-center">
                <fmt:message key="myOrders" bundle="${bundle}"/> <i class="bi bi-calendar2-week-fill"></i>
            </h2>

            <table class="table table-hover table-striped">
                <thead class="table-secondary">
                    <tr>
                        <th scope="col"><fmt:message key="service" bundle="${bundle}"/></th>
                        <th scope="col"><fmt:message key="date" bundle="${bundle}"/></th>
                        <th scope="col"><fmt:message key="master" bundle="${bundle}"/></th>
                        <th scope="col"><fmt:message key="price" bundle="${bundle}"/></th>
                        <th scope="col"><fmt:message key="status" bundle="${bundle}"/></th>
                        <th scope="col"><fmt:message key="action" bundle="${bundle}"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${records}" var="record">
                        <tr class="align-middle">
                            <th><c:out value="${record.service.name}" /></th>
                            <td><rd:format-date date="${record.time}"/></td>
                            <td><c:out value="${record.userMaster.firstName}" />
                                <c:out value="${record.userMaster.lastName}" />
                            </td>
                            <td><c:out value="${record.serviceMaster.price}" /> ₴</td>
                            <td class="status-id"><rs:record-status status="${record.status.value()}"/></td>
                            <td>
                                <c:if test="${record.status_id == 1}">
                                <form action="${pageContext.request.contextPath}/myOrders/paid?id=${record.id}"
                                    method="post" >
                                    <input class="w-100 btn btn-warning" type="submit"
                                        value="<fmt:message key="pay" bundle="${bundle}"/>">
                                </form>
                                </c:if>

                                <c:if test="${record.status_id == 4}">
                                    <a class="w-100 btn btn-primary"
                                        href="${pageContext.request.contextPath}/order/comment?id=${record.id}">
                                        <fmt:message key="leaveComment" bundle="${bundle}"/>
                                    </a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <c:if test="${noOfPages gt 1}">
                <nav aria-label="Orders pagination">
                    <ul class="pagination justify-content-center">
                        <%-- For displaying Previous link except for the 1st page --%>
                        <c:if test="${currentPage != 1}">
                            <li class="page-item">
                                <a class="page-link"
                                    href="${pageContext.request.contextPath}/myOrders?page=${currentPage - 1}">
                                    <i class="bi bi-caret-left-fill"></i>
                                </a>
                            </li>
                        </c:if>

                        <%-- For displaying Page numbers --%>
                        <c:forEach begin="1" end="${noOfPages}" var="i">
                            <c:choose>
                                <c:when test="${currentPage - 1 eq i or currentPage + 1 eq i}">
                                    <li class="page-item">
                                        <a class="page-link"
                                            href="${pageContext.request.contextPath}/myOrders?page=${i}">${i}
                                        </a>
                                    </li>
                                </c:when>
                                <c:when test="${currentPage eq i}">
                                    <li class="page-item active"><a class="page-link">${i}</a></li>
                                </c:when>
                            </c:choose>
                        </c:forEach>

                        <%-- For displaying Next link --%>
                        <c:if test="${currentPage lt noOfPages}">
                            <li class="page-item">
                                <a class="page-link"
                                    href="${pageContext.request.contextPath}/myOrders?page=${currentPage + 1}">
                                    <i class="bi bi-caret-right-fill"></i>
                                </a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </c:if>
        </div>

        <jsp:include page="parts/footer.jsp" />
    </body>

</html>