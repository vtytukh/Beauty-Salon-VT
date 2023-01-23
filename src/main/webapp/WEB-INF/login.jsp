<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="navbar" tagdir="/WEB-INF/tags" %>

<!doctype html>
<html lang="en">
    <head>
        <jsp:include page="parts/load-header.jsp"/>

        <fmt:setBundle basename="localization" var="bundle"/>

        <title>LogIn</title>
    </head>

    <body class="d-flex flex-column h-100">

        <navbar:navbar/>

        <div class="container mt-5 mb-5">
            <div class="row">
                <div class="col"></div>

                <div class="col bg-light p-4 rounded">
                    <h2 class="text-center"><fmt:message key="loginHeader" bundle="${bundle}"/> BeautySalon</h3>

                    <form accept-charset="UTF-8" role="form" method="post" action="${pageContext.request.contextPath}/login">

                        <div class="mb-3 mt-3">
                            <label for="email">Email:</label>
                            <input class="form-control" type="email" name="email" id="email" required>
                        </div>

                        <div class="mb-3">
                            <label for="password"><fmt:message key="password" bundle="${bundle}"/>:</label>
                            <input class="form-control" type="password" name="password" id="password" required>
                        </div>

                        <button type="submit" class="w-100 btn btn-outline-primary"><fmt:message key="loginBtn" bundle="${bundle}"/></button>
                    </form>
                </div>

                <div class="col"></div>
            </div>
        </div>

        <jsp:include page="parts/footer.jsp" />
    </body>
</html>