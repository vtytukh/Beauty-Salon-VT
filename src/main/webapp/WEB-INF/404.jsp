<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>

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
        <title>404 Error Page</title>
    </head>

    <body>
        <div class="d-flex align-items-center justify-content-center vh-100">
            <div class="text-center">
                <h1 class="display-1 fw-bold">404</h1>
                <p class="fs-3"> <span class="text-danger">Opps!</span> Page not found.</p>
                <p class="lead">
                    The page you’re looking for doesn’t exist.
                  </p>
                <a href="${pageContext.request.contextPath}" class="btn btn-primary">Go Home</a>
            </div>
        </div>
    </body>

</html>