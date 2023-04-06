<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div id="content-left">
    <ul>
        <a href="<c:url value="/admin/dashboard"/>">
            <li <c:if test="${requestScope['jakarta.servlet.forward.servlet_path'] == '/admin/dashboard'}">class="show"</c:if>>Dashboard</li>
        </a>
        <a href="<c:url value="/admin/orders"/>">
            <li <c:if test="${requestScope['jakarta.servlet.forward.servlet_path'] == '/admin/orders'}">class="show"</c:if>>Orders</li>
        </a>
        <a href="<c:url value="/admin/products"/>">
            <li <c:if test="${requestScope['jakarta.servlet.forward.servlet_path'] == '/admin/products'}">class="show"</c:if>>Products</li>
        </a>
        <a href="<c:url value="/admin/customers"/>">
            <li <c:if test="${requestScope['jakarta.servlet.forward.servlet_path'] == '/admin/customers'}">class="show"</c:if>>Customers</li>
        </a>
    </ul>
</div>
