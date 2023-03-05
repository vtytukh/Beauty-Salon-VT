<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@tag description="Page navigation bar" pageEncoding="UTF-8"%>
<%@attribute name="navbar" fragment="true" %>

<c:if test="${sessionScope.locale == null}">
    <fmt:setLocale value="en"/>
</c:if>
<c:if test="${sessionScope.locale != null}">
	<fmt:setLocale value="${sessionScope.locale}"/>
</c:if>

<fmt:setBundle basename="localization" var="bundle"/>

<header class="p-3 mb-3 bg-light border-bottom">
	<div class="container">
		<div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">

			<a href="${pageContext.request.contextPath}" class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-dark text-decoration-none">
				<img src="${pageContext.request.contextPath}/sources/logo.jpg" alt="BeautySalon Logo" width="70" class="bi me-2 rounded-pill">
				<span class="fs-5">BeautySalon</span>
			</a>

			<c:if test="${sessionScope.role == 'Admin'}">
				<ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-lg-start mb-md-0">
					<li>
						<a href="${pageContext.request.contextPath}/admin/records" class="nav-link px-2">
							<fmt:message key="orders" bundle="${bundle}"/>
						</a>
					</li>
					<li><span class="nav-link disabled px-2">/</span></li>
					<li>
						<a href="${pageContext.request.contextPath}/admin/createService" class="nav-link px-2">
							<fmt:message key="createService" bundle="${bundle}"/>
						</a>
					</li>
					<li><span class="nav-link disabled px-2">/</span></li>
					<li>
						<a href="${pageContext.request.contextPath}/admin/users" class="nav-link px-2">
							<fmt:message key="allUsers" bundle="${bundle}"/>
						</a>
					</li>
				</ul>
			</c:if>
			<c:if test="${sessionScope.role == 'Master'}">
				<ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-lg-start mb-md-0">
					<li>
						<a href="${pageContext.request.contextPath}/master/timeTable" class="nav-link px-2">
							<fmt:message key="timeTable" bundle="${bundle}"/>
						</a>
					</li>
				</ul>
			</c:if>
			<c:if test="${sessionScope.authenticated != null && sessionScope.authenticated == true && sessionScope.role == 'Client'}">
				<ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-lg-start mb-md-0">
					<li>
						<a href="${pageContext.request.contextPath}/order" class="nav-link px-2">
							<fmt:message key="order" bundle="${bundle}"/>
						</a>
					</li>
					<li><span class="nav-link disabled px-2">/</span></li>
					<li>
						<a href="${pageContext.request.contextPath}/myOrders" class="nav-link px-2">
							<fmt:message key="myOrders" bundle="${bundle}"/>
						</a>
					</li>
				</ul>
			</c:if>

			<c:if test="${sessionScope.authenticated != null && sessionScope.authenticated == true}">
				<div class="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3 text-center text-muted small">
					<span><fmt:message key="loggedInWelcome" bundle="${bundle}"/>:<br/>
					${sessionScope.email},<br/>
					<span class="badge bg-secondary">${sessionScope.role}</span></span>
				</div>
			</c:if>

<!--
			<c:if test="${sessionScope.locale == null}">
                <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-lg-start mb-md-0">
                    <li>
                        <a href="${pageContext.request.contextPath}/language?locale=en" class="nav-link px-2"><span class="badge bg-info">EN</span></a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/language?locale=ua" class="nav-link">UA</a>
                    </li>
                </ul>
            </c:if>
            <c:if test="${sessionScope.locale == 'en'}">
                <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-lg-start mb-md-0">
                    <li>
                        <a href="${pageContext.request.contextPath}/language?locale=en" class="nav-link px-2"><span class="badge bg-info">EN</span></a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/language?locale=ua" class="nav-link">UA</a>
                    </li>
                </ul>
            </c:if>
            <c:if test="${sessionScope.locale == 'ua'}">
                <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-lg-start mb-md-0">
                    <li>
                        <a href="${pageContext.request.contextPath}/language?locale=en" class="nav-link px-2">EN</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/language?locale=ua" class="nav-link"><span class="badge bg-info">UA</span></a>
                    </li>
                </ul>
            </c:if> -->

            <div class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-lg-start mb-md-0">
                <div class="dropdown">
                    <button type="button" class="btn btn-outline-secondary me-2 dropdown-toggle" data-bs-toggle="dropdown">
                        ${fn:toUpperCase(locale)}
                    </button>
                    <ul class="dropdown-menu">
                        <li>
                            <form method="post" action="${pageContext.request.contextPath}/language">
                                <button class="dropdown-item <c:if test="${sessionScope.locale == 'en'}">active</c:if>" type="submit" value="en" name="locale">EN</button>
                            </form>
                        </li>
                        <li>
                            <form method="post" action="${pageContext.request.contextPath}/language">
                                <button class="dropdown-item <c:if test="${sessionScope.locale == 'ua'}">active</c:if>" type="submit" value="ua" name="locale">UA</button>
                            </form>
                        </li>
                    </ul>
                </div>
            </div>

			<div class="text-end">
				<c:if test="${sessionScope.authenticated != null && sessionScope.authenticated == true}">
					<form action="${pageContext.request.contextPath}/logout" method="post">
						<input type="submit" value="<fmt:message key="logout" bundle="${bundle}"/>" class="btn btn-warning">
					</form>
				</c:if>

				<c:if test="${sessionScope.authenticated == null}">
					<a href="${pageContext.request.contextPath}/login" class="btn btn-outline-primary me-2">
						<fmt:message key="login" bundle="${bundle}"/>
					</a>
					<a href="${pageContext.request.contextPath}/register" class="btn btn-warning">
						<fmt:message key="register" bundle="${bundle}"/>
					</a>
				</c:if>
			</div>
		</div>
	</div>
</header>