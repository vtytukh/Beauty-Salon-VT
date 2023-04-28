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

        <title><fmt:message key="timeTable" bundle="${bundle}"/></title>
    </head>

    <body>

        <navbar:navbar/>

        <div class="container mt-3 mb-3">

            <c:if test="${pageContext.request.getParameter('valid_message') eq 'finished_success'}">
                <div class="row justify-content-center">
                    <div class="w-75 alert alert-success alert-dismissible fade show" role="alert">
                        <button type="button" class="btn-close btn-sm" data-bs-dismiss="alert"></button>
                        <fmt:message key="finishedServiceSuccessfully" bundle="${bundle}"/>
                    </div>
                </div>
            </c:if>

            <c:if test="${pageContext.request.getParameter('valid_message') eq 'finished_unsuccessful'}">
                <div class="row justify-content-center">
                    <div class="w-75 alert alert-danger alert-dismissible fade show" role="alert">
                        <button type="button" class="btn-close btn-sm" data-bs-dismiss="alert"></button>
                        <fmt:message key="finishedServiceUnsuccessfully" bundle="${bundle}"/>
                    </div>
                </div>
            </c:if>

            <h2 class="text-center"><fmt:message key="timeTable" bundle="${bundle}"/> <i class="bi bi-calendar2-week-fill"></i></h2>

            <div class="mt-4 mb-3">
                <form action="${pageContext.request.contextPath}/master/timeTable" method="get">
                    <div class="row">
                        <div class="col-auto">
                            <label class="col-form-label" for="calendar"><fmt:message key="chooseDate" bundle="${bundle}"/>:</label>
                        </div>
                        <div class="col-auto">
                            <input class="form-control" type="date" name="date" id="calendar" value="${requestedDate}" required>
                        </div>
                        <div class="col-auto">
                            <input class="btn btn-outline-primary" type="submit" value="<fmt:message key="getRecords" bundle="${bundle}"/>">
                        </div>
                    </div>
                </form>
            </div>

            <table class="table table-hover table-striped">
                <thead class="table-secondary">
                    <tr>
                        <th scope="col"><fmt:message key="time" bundle="${bundle}"/></th>
                        <th scope="col"><fmt:message key="service" bundle="${bundle}"/></th>
                        <th scope="col"><fmt:message key="client" bundle="${bundle}"/></th>
                        <th scope="col"><fmt:message key="status" bundle="${bundle}"/></th>
                        <th scope="col"><fmt:message key="edit" bundle="${bundle}"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${records}" var="record">
                        <tr class="align-middle">
                            <th scope="row"><c:out value="${record.hour}" />:00</th>
                            <td><c:out value="${record.serviceMaster.service.name}" /></td>
                            <td><c:out value="${record.user.firstName}" /> <c:out value="${record.user.lastName}" /></td>
                            <td><rs:record-status status="${record.status.value()}"/></td>
                            <td>
                                <c:if test="${record.status.value() eq 'accepted'}">
                                    <form action="${pageContext.request.contextPath}/master/timeTable/updateStatus?id=${record.id}" method="post">
                                        <input class="btn btn-outline-primary" type="submit" value="<fmt:message key="finished" bundle="${bundle}"/>">
                                    </form>
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