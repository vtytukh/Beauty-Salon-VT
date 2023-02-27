<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@tag description="Formatting record date" pageEncoding="UTF-8"%>
<%@attribute name="date" required="true" rtexprvalue="true" %>

<c:if test="${sessionScope.locale != null}">
	<fmt:setLocale value="${sessionScope.locale}"/>
</c:if>

<fmt:setBundle basename="localization" var="bundle"/>

<fmt:parseDate value="${date}" var="parsedRecordTime" pattern="yyyy-MM-dd HH:mm:ss" parseLocale="uk-UA" />
<fmt:setLocale value="uk-UA"/>
<fmt:formatDate type="date" value="${parsedRecordTime}" var="formattedRecordDate" pattern="dd.MM.yyyy HH:mm" />
<c:out value="${formattedRecordDate}" />