<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="/template/header.jsp"/>

<jsp:useBean id="productDAO" class="models.ProductDAO"/>
<div id="content">
    <c:import url="/template/profile-left.jsp"/>
    <div id="content-right">
        <div class="path">${type == null ? "LIST ORDERS" : type}</b></div>
        <div class="content-main">
            <div id="profile-content-order">
                <c:forEach items="${orders}" var="order" varStatus="loopStatus">
                    <div>
                        <div class="profile-order-title">
                            <div class="profile-order-title-left">
                                <div>Order creation date: ${order.orderDate}</div>
                                <div>Order: <a href="#">#${loopStatus.count}</a></div>
                            </div>
                            <div class="profile-order-title-right">
                                <c:choose>
                                    <c:when test="${order.requiredDate == null}">
                                        <span>Canceled</span>
                                    </c:when>
                                    <c:when test="${order.shippedDate == null}">
                                        <a class="cancel-order" href="<c:url value="/account/orders/cancelorder?id=${order.orderID}"/>">Cancel</a>
                                        <span>Pending</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span style="color: blue;">Completed</span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="profile-order-content-box">
                            <c:set scope="request" var="orderDetails" value="${ordersMap.get(order.orderID)}"/>
                            <c:forEach items="${orderDetails}" var="orderDetail">
                                <c:set var="product" value="${productDAO.getProductById(orderDetail.productID)}"/>
                                <div class="profile-order-content">
                                    <div class="profile-order-content-col1">
                                        <a href="<c:url value="/product?id=${product.productID}"/>"><img src="<c:url value="/asset/img/2.jpg"/>" width="100%"/></a>
                                    </div>
                                    <div class="profile-order-content-col2">${product.productName}</div>
                                    <div class="profile-order-content-col3">Quantity: ${orderDetail.quantity}</div>
                                    <div class="profile-order-content-col4"><fmt:formatNumber value="${orderDetail.quantity * orderDetail.unitPrice * (1 - orderDetail.discount)}" maxFractionDigits="3"/> $</div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>

                </c:forEach>
            </div>
        </div>
    </div>
</div>
<c:import url="/template/footer.jsp"/>