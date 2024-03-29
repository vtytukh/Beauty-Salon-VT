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

        <title><fmt:message key="recordsItem" bundle="${bundle}"/></title>
    </head>

    <body>

    <navbar:navbar/>

    <div class="container mt-3 mb-3">
        <h2 class="text-center">
            <fmt:message key="recordsItem" bundle="${bundle}"/> <i class="bi bi-clipboard-check-fill"></i>
        </h2>

        <div class="d-flex justify-content-center">
            <div class="card col-5">
                <div class="card-header">
                    <i class="bi bi-clipboard-check-fill"></i> <fmt:message key="id" bundle="${bundle}"/>:
                        <c:out value="${record.id}" />
                </div>
                <div class="card-body">
                    <h4 class="card-title">
                        <fmt:message key="user" bundle="${bundle}"/>:
                        <c:out value="${record.user.firstName}" />
                        <c:out value="${record.user.lastName}" />
                    </h4>
                    <p class="card-text">
                        <em><fmt:message key="master" bundle="${bundle}"/></em>:
                        <c:out value="${record.userMaster.firstName}" />
                        <c:out value="${record.userMaster.lastName}" />
                    </p>
                    <p class="card-text">
                        <em><fmt:message key="service" bundle="${bundle}"/></em>:
                        <c:out value="${record.service.name}" />
                    </p>
                    <p class="card-text">
                        <em><fmt:message key="status" bundle="${bundle}"/></em>:
                        <rs:record-status status="${record.status.value()}"/>
                    </p>

                    <div class="d-flex justify-content-center">
                        <form action="${pageContext.request.contextPath}/admin/records/updateTime?id=${record.id}"
                        method="post" id="time-form">
                            <select class="form-select" name="time" onchange="setButton();" required>
                                <option selected onload="load(this);" id="first-option" disabled>???</option>
                                <c:forEach items="${recTime}" var="time">
                                    <option value="<c:out value="${time}" />"><c:out value="${time}" />:00</option>
                                </c:forEach>
                            </select>
                            <input class="btn btn-outline-primary form-control mt-3" type="submit"
                                value="<fmt:message key="changeTime" bundle="${bundle}"/>" id="time-button"
                                style="display: none">
                        </form>
                    </div>

                    <div id="time-accept"></div>

                    <c:if test="${record.status_id == 2}">
                        <div class="d-flex justify-content-center">
                            <div class="col-6">
                                <form action="${pageContext.request.contextPath}/admin/records/accept?id=${record.id}"
                                    method="post">
                                    <input class="btn btn-outline-primary form-control mt-3" type="submit"
                                        value="<fmt:message key="acceptPayment" bundle="${bundle}"/>">
                                </form>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${record.status_id < 4}">
                    <hr/>
                        <div class="row d-flex justify-content-center">
                            <div class="col-6">
                                <form action="${pageContext.request.contextPath}/admin/records/cancel?id=${record.id}"
                                    method="post">
                                    <input class="btn btn-outline-danger form-control" type="submit"
                                        value="<fmt:message key="cancelRecord" bundle="${bundle}"/>">
                                </form>
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
        <script defer>
            document.addEventListener("DOMContentLoaded", load);
            function load() {
                var f = document.querySelector("#first-option");
                console.log(f);
                var test = "${record.time}";
                console.log(test);
                var str = "<c:out value="${record.time}" />";
                console.log(str);
                f.value = str.substring(11,13);
                f.innerHTML = "<fmt:message key="currentRecordTime" bundle="${bundle}"/>: "+str.substring(11,13)+":00";
            }
            function setButton() {
                var form = document.querySelector("#time-button");
                console.log(form);
                form.style.display = "block";
            }
        </script>

        <jsp:include page="parts/footer.jsp" />
    </body>

</html>