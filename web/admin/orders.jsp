<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:import url="/template/admin-header.jsp"/>
<c:set scope="page" var="pageNumber" value="${param.page != null ? param.page : 1}"/>
<jsp:useBean id="customerDAO" class="models.CustomerDAO"/>
<c:if test="${!(pageNumber >= 1 && pageNumber <= orderDAO.totalPage)}">
    <c:redirect url="/admin/orders"/>
</c:if>
<div id="content">
    <c:import url="/template/admin-left.jsp"/>
    <div id="content-right">
        <div class="path-admin">ORDERS LIST</b></div>
        <div class="content-main">
            <div id="content-main-dashboard">
                <div id="order-title">
                    <b>Filter by Order date:</b>
                    <form>
                        From: <input type="date" name="txtStartOrderDate" value="${txtStartOrderDate}"/>
                        To: <input type="date" name="txtEndOrderDate" value="${txtEndOrderDate}"/>
                        <input type="submit" value="Filter">
                    </form>
                </div>
                <div id="order-table">
                    <table id="orders">
                        <tr>
                            <th>OrderID</th>
                            <th>OrderDate</th>
                            <th>RequiredDate</th>
                            <th>ShippedDate</th>
                            <th>Employee</th>
                            <th>Customer</th>
                            <th>Freight($)</th>
                            <th>Status</th>
                        </tr>
                        <c:forEach var="order" items="${orderDAO.getItemsInPage(pageNumber)}">
                            <tr>
                                <td><a href="<c:url value="/admin/order-detail?id=${order.orderID}"/>">#${order.orderID}</a></td>
                                <td>${order.orderDate}</td>
                                <td>${order.requiredDate}</td>
                                <td>${order.shippedDate}</td>
                                <td></td>
                                <td>
                                    <c:catch>
                                        ${customerDAO.getCustomerById(order.customerID).contactName}
                                    </c:catch>
                                </td>
                                <td>${order.freight}</td>
                                <c:choose>
                                    <c:when test="${not empty order.shippedDate}">
                                        <td style="color: green;">
                                            Completed
                                        </td>
                                    </c:when>
                                    <c:when test="${not empty order.requiredDate}">
                                        <td style="color: blue;">
                                            Pending | 
                                            <a href="<c:url value="/admin/orders/cancelorder?id=${order.orderID}"/>">Cancel</a>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td style="color: red;">
                                            Order canceled
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
                <c:import url="/template/pagination-bar.jsp">
                    <c:param name="page" value="${pageNumber}"/>
                    <c:param name="modelDAOName" value="orderDAO"/>
                    <c:param name="basePath" value="/admin/orders"/>
                </c:import>
            </div>
        </div>
    </div>
</div>
<c:import url="/template/admin-footer.jsp"/>