<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="navbar" tagdir="/WEB-INF/tags" %>

<!doctype html>
<html lang="en">
    <head>
        <jsp:include page="parts/load-header.jsp"/>

        <fmt:setBundle basename="localization" var="bundle"/>

        <title>Records</title>
    </head>

    <body>
        <navbar:navbar/>

        <div class="container mt-5 mb-5">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th scope="col"><fmt:message key="id" bundle="${bundle}"/></th>
                        <th scope="col"><fmt:message key="user" bundle="${bundle}"/></th>
                        <th scope="col"><fmt:message key="master" bundle="${bundle}"/></th>
                        <th scope="col"><fmt:message key="service" bundle="${bundle}"/></th>
                        <th scope="col"><fmt:message key="status" bundle="${bundle}"/></th>
                        <th scope="col"><fmt:message key="date" bundle="${bundle}"/></th>
                        <th scope="col"></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${records}" var="record">
                        <tr>
                            <th scope="row"><c:out value="${record.id}" /></th>
                            <td><c:out value="${record.user.firstName}" />
                                <c:out value="${record.user.lastName}" /></td>
                            <td><c:out value="${record.userMaster.firstName}" />
                                <c:out value="${record.userMaster.lastName}" /></td>
                            <td><c:out value="${record.service.name}" /></td>
                            <td><c:out value="${record.status.value()}" /></td>
                            <td><c:out value="${record.time}" /></td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/records/edit?id=${record.id}">
                                    <fmt:message key="details" bundle="${bundle}"/>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div id="pages"><c:out value="${pages}" /></div>


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