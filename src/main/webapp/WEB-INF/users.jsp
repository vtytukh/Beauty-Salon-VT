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

        <title>Users</title>
    </head>

    <body>
        <navbar:navbar/>

        <div class="container mt-5 mb-5">

            <table class="table table-hover table-striped">
                <thead class="table-secondary">
                <tr>
                    <th scope="col"><fmt:message key="id" bundle="${bundle}"/></th>
                    <th scope="col"><fmt:message key="user" bundle="${bundle}"/></th>
                    <th scope="col">Email</th>
                    <th scope="col"><fmt:message key="role" bundle="${bundle}"/></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${users}" var="user">
                        <tr>
                            <th scope="row"><c:out value="${user.id}" /></th>
                            <td><c:out value="${user.firstName}" />
                                <c:out value="${user.lastName}" /></td>
                            <td><c:out value="${user.email}" /></td>
                            <td><c:out value="${user.role.value()}" /></td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/users/edit?id=${user.id}">
                                    <fmt:message key="edit" bundle="${bundle}"/>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <!--<div class="pagination justify-content-center" id="pages"><c:out value="${pages}" /></div>-->
            <nav aria-label="Users pagination">
                <ul class="pagination justify-content-center">
                	<%-- For displaying Previous link except for the 1st page --%>
                	<c:if test="${currentPage != 1}">
                		<li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/admin/users?page=${currentPage - 1}">&laquo;</a></li>
                	</c:if>

                	<%-- For displaying Page numbers --%>
                	<c:forEach begin="1" end="${noOfPages}" var="i">
                		<c:choose>
                		    <c:when test="${currentPage - 1 eq i or currentPage + 1 eq i}">
                                <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/admin/users?page=${i}">${i}</a></li>
                            </c:when>
                			<c:when test="${currentPage eq i}">
                				<li class="page-item active"><a class="page-link">${i}</a></li>
                			</c:when>
                		</c:choose>
                	</c:forEach>

                	<%-- For displaying Next link --%>
                	<c:if test="${currentPage lt noOfPages}">
                		<li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/admin/users?page=${currentPage + 1}">&raquo;</a></td>
                	</c:if>
                </ul>
            </nav>
        </div>

        <script defer>
            document.addEventListener("DOMContentLoaded", load);
            function load() {

                document.getElementById("pages").innerHTML = `${pages}`;
            }
        </script>

        <jsp:include page="parts/footer.jsp" />
    </body>
</html>