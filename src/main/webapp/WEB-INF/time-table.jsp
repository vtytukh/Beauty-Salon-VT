<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="navbar" tagdir="/WEB-INF/tags" %>

<!doctype html>
<html lang="en">
    <head>
        <jsp:include page="parts/load-header.jsp"/>

        <fmt:setBundle basename="localization" var="bundle"/>

        <title>Time-table</title>
    </head>

    <body>

        <navbar:navbar/>

        <div class="container" style="margin-top: 20px">
            <form action="${pageContext.request.contextPath}/master/timeTable" method="get">
                <input type="date" name="date" id="calendar" required>
                <input type="submit">
            </form>

            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th scope="col"><fmt:message key="time" bundle="${bundle}"/></th>
                        <th scope="col"><fmt:message key="service" bundle="${bundle}"/></th>
                        <th scope="col"><fmt:message key="user" bundle="${bundle}"/></th>
                        <th scope="col"><fmt:message key="status" bundle="${bundle}"/></th>
                        <th scope="col"></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${records}" var="record">
                        <tr>
                            <th scope="row"><c:out value="${record.hour}" />:00</th>
                            <td><c:out value="${record.serviceMaster.service.name}" /></td>
                            <td><c:out value="${record.user.firstName}" /> <c:out value="${record.user.lastName}" /></td>
                            <td><c:out value="${record.status.value()}" /></td>
                            <td>
                                <c:if test="${record.status.value() == \"accepted\"}">
                                    <form action="${pageContext.request.contextPath}/master/timeTable/updateStatus?id=${record.id}" method="post">
                                        <input type="submit" value="<fmt:message key="finished" bundle="${bundle}"/>">
                                    </form>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <script defer>
            function getToday() {
                var today = new Date();
                var dd = String(today.getDate()).padStart(2, '0');
                var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
                var yyyy = today.getFullYear();

                today = mm + '/' + dd + '/' + yyyy;
                return today;
            }
            document.getElementById('calendar').valueAsDate = new Date();
        </script>

        <jsp:include page="parts/footer.jsp" />
    </body>
</html>