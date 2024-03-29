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

        <title><fmt:message key="allUsers" bundle="${bundle}"/></title>
    </head>

    <body>
        <navbar:navbar/>

        <div class="container mt-3 mb-3">

            <c:if test="${pageContext.request.getParameter('valid_message') eq 'new_service_success'}">
                <div class="row justify-content-center">
                    <div class="w-75 alert alert-success alert-dismissible fade show" role="alert">
                        <button type="button" class="btn-close btn-sm" data-bs-dismiss="alert"></button>
                        <fmt:message key="newServiceSuccessful" bundle="${bundle}"/>
                    </div>
                </div>
            </c:if>

            <c:if test="${pageContext.request.getParameter('valid_message') eq 'master_added_success'}">
                <div class="row justify-content-center">
                    <div class="w-75 alert alert-success alert-dismissible fade show" role="alert">
                        <button type="button" class="btn-close btn-sm" data-bs-dismiss="alert"></button>
                        <fmt:message key="masterAddedSuccessfully" bundle="${bundle}"/>
                    </div>
                </div>
            </c:if>

            <c:if test="${pageContext.request.getParameter('valid_message') eq 'master_added_unsuccessful'}">
                <div class="row justify-content-center">
                    <div class="w-75 alert alert-danger alert-dismissible fade show" role="alert">
                        <button type="button" class="btn-close btn-sm" data-bs-dismiss="alert"></button>
                        <fmt:message key="masterAddedUnsuccessfully" bundle="${bundle}"/>
                    </div>
                </div>
            </c:if>

            <c:if test="${pageContext.request.getParameter('valid_message') eq 'service_added_success'}">
                <div class="row justify-content-center">
                    <div class="w-75 alert alert-success alert-dismissible fade show" role="alert">
                        <button type="button" class="btn-close btn-sm" data-bs-dismiss="alert"></button>
                        <fmt:message key="serviceAddedSuccessfully" bundle="${bundle}"/>
                    </div>
                </div>
            </c:if>

            <c:if test="${pageContext.request.getParameter('valid_message') eq 'service_added_unsuccessful'}">
                <div class="row justify-content-center">
                    <div class="w-75 alert alert-danger alert-dismissible fade show" role="alert">
                        <button type="button" class="btn-close btn-sm" data-bs-dismiss="alert"></button>
                        <fmt:message key="serviceAddedUnsuccessfully" bundle="${bundle}"/>
                    </div>
                </div>
            </c:if>

            <h2 class="text-center">
                <fmt:message key="allUsers" bundle="${bundle}"/> <i class="bi bi-people-fill"></i>
            </h2>

            <table class="table table-hover table-striped">
                <thead class="table-secondary">
                    <tr>
                        <th scope="col"><fmt:message key="id" bundle="${bundle}"/></th>
                        <th scope="col"><fmt:message key="user" bundle="${bundle}"/></th>
                        <th scope="col">Email</th>
                        <th scope="col"><fmt:message key="role" bundle="${bundle}"/></th>
                        <th scope="col"><fmt:message key="edit" bundle="${bundle}"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${users}" var="user">
                        <tr>
                            <th scope="row"><c:out value="${user.id}" /></th>
                            <td><c:out value="${user.firstName}" />
                                <c:out value="${user.lastName}" /></td>
                            <td><c:out value="${user.email}" /></td>
                            <td><fmt:message key="role${user.role.value()}" bundle="${bundle}"/></td>
                            <td>
                                <a title="<fmt:message key="edit" bundle="${bundle}"/>"
                                    href="${pageContext.request.contextPath}/admin/users/edit?id=${user.id}">
                                    <i class="bi bi-pencil-square"></i>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <c:if test="${noOfPages gt 1}">
                <nav aria-label="Users pagination">
                    <ul class="pagination justify-content-center">
                        <%-- For displaying Previous link except for the 1st page --%>
                        <c:if test="${currentPage != 1}">
                            <li class="page-item">
                                <a class="page-link"
                                    href="${pageContext.request.contextPath}/admin/users?page=${currentPage - 1}">
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
                                            href="${pageContext.request.contextPath}/admin/users?page=${i}">${i}
                                        </a>
                                    </li>
                                </c:when>
                                <c:when test="${currentPage eq i}">
                                    <li class="page-item active">
                                        <a class="page-link">${i}</a>
                                    </li>
                                </c:when>
                            </c:choose>
                        </c:forEach>

                        <%-- For displaying Next link --%>
                        <c:if test="${currentPage lt noOfPages}">
                            <li class="page-item">
                                <a class="page-link"
                                    href="${pageContext.request.contextPath}/admin/users?page=${currentPage + 1}">
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