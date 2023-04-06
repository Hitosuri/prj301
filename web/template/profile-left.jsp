<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:useBean id="customerDAO" class="models.CustomerDAO"/>
<c:set var="customer" value="${customerDAO.getCustomerById(sessionScope.accSession.customerID)}"/>
<div id="content-left">
    <h3 style="font-weight: normal;">Welcome, ${customer.contactName}</h3>
    <h3>Account Management</h3>
    <ul>
        <a href="<c:url value="/account/profile"/>">
            <li <c:if test="${requestScope['jakarta.servlet.forward.servlet_path'] == '/account/profile'}">class="show"</c:if>>Personal information</li>
        </a>
    </ul>
    <h3>My order</h3>
    <ul>
        <a href="<c:url value="/account/orders"/>">
            <li <c:if test="${requestScope['jakarta.servlet.forward.servlet_path'] == '/account/orders'}">class="show"</c:if>>All orders</li>
        </a>
        <a href="<c:url value="/account/orders/cancel"/>">
            <li <c:if test="${requestScope['jakarta.servlet.forward.servlet_path'] == '/account/orders/cancel'}">class="show"</c:if>>Canceled order</li>
        </a>
    </ul>
</div>