<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="/template/header.jsp"/>
<div id="content">
    <c:import url="/template/left.jsp"/>
    <div id="content-right">
        <div class="path">Hot</b></div>
        <div class="content-main">
            <c:forEach items="${hotProduct}" var="product">
                <div class="product">
                    <a href="<c:url value="/product?id=${product.productID}"/>"><img src="<c:url value="/asset/img/1.jpg"/>" width="100%"/></a>
                    <div class="name"><a href="<c:url value="/product?id=${product.productID}"/>">${product.productName}</a></div>
                    <div class="price">$${product.unitPrice}</div>
                    <c:if test="${product.unitsInStock > 0}">
                        <div><a href="<c:url value="/cart/buynow?id=${product.productID}"/>">Buy now</a></div>
                    </c:if>
                </div>
            </c:forEach>
        </div>
        <div class="path">Best Sale</b></div>
        <div class="content-main">
            <c:forEach items="${bestSaleProduct}" var="product">
                <div class="product">
                    <a href="<c:url value="/product?id=${product.productID}"/>"><img src="<c:url value="/asset/img/1.jpg"/>" width="100%"/></a>
                    <div class="name"><a href="<c:url value="/product?id=${product.productID}"/>">${product.productName}</a></div>
                    <div class="price">$${product.unitPrice}</div>
                    <c:if test="${product.unitsInStock > 0}">
                        <div><a href="<c:url value="/cart/buynow?id=${product.productID}"/>">Buy now</a></div>
                    </c:if>
                </div>
            </c:forEach>
        </div>
        <div class="path">New Product</b></div>
        <div class="content-main">
            <c:forEach items="${newProduct}" var="product">
                <div class="product">
                    <a href="<c:url value="/product?id=${product.productID}"/>"><img src="<c:url value="/asset/img/1.jpg"/>" width="100%"/></a>
                    <div class="name"><a href="<c:url value="/product?id=${product.productID}"/>">${product.productName}</a></div>
                    <div class="price">$${product.unitPrice}</div>
                    <c:if test="${product.unitsInStock > 0}">
                        <div><a href="<c:url value="/cart/buynow?id=${product.productID}"/>">Buy now</a></div>
                    </c:if>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<c:import url="/template/footer.jsp"/>