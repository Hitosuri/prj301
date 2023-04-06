<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:import url="/template/header.jsp"/>
<c:set scope="page" var="pageNumber" value="${param.page != null ? param.page : 1}"/>
<c:if test="${!(pageNumber >= 1 && pageNumber <= productDAO.totalPage)}">
    <c:redirect url="/category?id=${param.id}"/>
</c:if>
<div id="content">
    <%@include file="template/left.jsp" %>
    <div id="content-right">
        <div class="path">${category.categoryName}</div>
        <div class="content-main">
            <c:forEach var="product" items="${productDAO.getItemsInPage(pageNumber)}">
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
        <c:import url="/template/pagination-bar.jsp">
            <c:param name="page" value="${pageNumber}"/>
            <c:param name="modelDAOName" value="productDAO"/>
            <c:param name="basePath" value="/category?id=${param.id}"/>
        </c:import>
    </div>
</div>

<c:import url="/template/footer.jsp"/>