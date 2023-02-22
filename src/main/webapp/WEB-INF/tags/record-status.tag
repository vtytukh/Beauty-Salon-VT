<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@tag description="Record status badges" pageEncoding="UTF-8"%>
<%@attribute name="status" required="true" rtexprvalue="true" %>

<c:if test="${sessionScope.locale != null}">
	<fmt:setLocale value="${sessionScope.locale}"/>
</c:if>

<fmt:setBundle basename="localization" var="bundle"/>

<c:choose>
    <c:when test="${empty status}">
        <span class="badge bg-secondary"><i class="bi bi-calendar"></i>
        <fmt:message key="freeStatus" bundle="${bundle}"/></span>
    </c:when>
    <c:when test="${status eq 'pending'}">
        <span class="badge bg-light text-dark"><i class="bi bi-calendar-minus"></i>
        <fmt:message key="pendingStatus" bundle="${bundle}"/></span>
    </c:when>
    <c:when test="${status eq 'accepted'}">
        <span class="badge bg-warning text-dark"><i class="bi bi-calendar-event"></i>
        <fmt:message key="acceptedStatus" bundle="${bundle}"/></span>
    </c:when>
    <c:when test="${status eq 'paid'}">
        <span class="badge bg-info text-dark"><i class="bi bi-calendar-event-fill"></i>
        <fmt:message key="paidStatus" bundle="${bundle}"/></span>
    </c:when>
    <c:when test="${status eq 'finished'}">
        <span class="badge bg-primary"><i class="bi bi-calendar-check"></i>
        <fmt:message key="finishedStatus" bundle="${bundle}"/></span>
    </c:when>
    <c:when test="${status eq 'feedbacked'}">
        <span class="badge bg-success"><i class="bi bi-calendar-check-fill"></i>
        <fmt:message key="feedbackedStatus" bundle="${bundle}"/></span>
    </c:when>
    <c:when test="${status eq 'canceled'}">
        <span class="badge bg-danger"><i class="bi bi-calendar-x"></i>
        <fmt:message key="canceledStatus" bundle="${bundle}"/></span>
    </c:when>
</c:choose>