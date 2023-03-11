<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="navbar" tagdir="/WEB-INF/tags" %>

<!doctype html>
<html lang="en">
    <head>
        <jsp:include page="parts/load-header.jsp"/>

        <c:if test="${sessionScope.locale != null}">
        	<fmt:setLocale value="${sessionScope.locale}"/>
        </c:if>

        <fmt:setBundle basename="localization" var="bundle"/>

        <title><fmt:message key="loginHeader" bundle="${bundle}"/></title>
    </head>

    <body class="d-flex flex-column h-100">

        <navbar:navbar/>

        <div class="container mt-5 mb-5">

            <c:if test="${pageContext.request.getParameter('valid_message') eq 'register_success'}">
                <div class="row justify-content-center">
                    <div class="w-75 alert alert-success alert-dismissible fade show" role="alert">
                        <button type="button" class="btn-close btn-sm" data-bs-dismiss="alert"></button>
                        <fmt:message key="registerSuccess" bundle="${bundle}"/>
                    </div>
                </div>
            </c:if>

            <div class="row">
                <div class="col"></div>

                <div class="col bg-light p-4 rounded">
                    <h2 class="text-center"><fmt:message key="loginHeader" bundle="${bundle}"/></h2>

                    <form accept-charset="UTF-8" role="form" method="post"
                        action="${pageContext.request.contextPath}/login">

                        <div class="mb-3 mt-3">
                            <label for="email">Email:</label>
                            <input class="form-control" type="email" name="email" id="email"
                                pattern="^[^\s]+@[^\s]+\.[^\s]+$" minlength="3" maxlength="45"
                                title="<fmt:message key="emailPatternTitle" bundle="${bundle}"/>" required>
                        </div>

                        <div class="mb-3">
                            <label for="password"><fmt:message key="password" bundle="${bundle}"/>:</label>
                            <input class="form-control" type="password" name="password" id="password"
                                pattern="^[\w-]{6,20}$" minlength="6" maxlength="20"
                                title="<fmt:message key="passwordPatternTitle" bundle="${bundle}"/>" required>
                        </div>

                        <c:if test="${pageContext.request.getParameter('valid_message') eq 'no_such_user'}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <button type="button" class="btn-close btn-sm" data-bs-dismiss="alert"></button>
                                <fmt:message key="loginNoSuchUser" bundle="${bundle}"/>
                            </div>
                        </c:if>

                        <c:if test="${pageContext.request.getParameter('valid_message') eq 'invalid_inputs'}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <button type="button" class="btn-close btn-sm" data-bs-dismiss="alert"></button>
                                <fmt:message key="loginInvalidInputs" bundle="${bundle}"/>
                            </div>
                        </c:if>

                        <button type="submit" class="w-100 btn btn-outline-primary">
                            <fmt:message key="loginBtn" bundle="${bundle}"/></button>
                    </form>
                </div>

                <div class="col"></div>
            </div>
        </div>

        <jsp:include page="parts/footer.jsp" />
    </body>
</html>