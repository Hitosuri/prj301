<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:set scope="page" var="pageNumber" value="${param.page != null ? param.page : 1}"/>
<c:if test="${!(pageNumber >= 1 && pageNumber <= productDAO.totalPage)}">
    <c:redirect url="/search?cid=${categoryID}&keyword=${keyword}"/>
</c:if>
<c:import url="/template/header.jsp"/>
<div id="content">
    <%@include file="template/left.jsp" %>
    <div id="content-right">
        <div class="path">Search result for: ${keyword}</div>
        <div class="content-main">
            <c:set scope="page" value="${productDAO.getItemsInPage(pageNumber)}" var="products"/>
            <c:if test="${products.size() == 0}">
                <h1 style="width: 100%;text-align: center;font-size: 3rem;margin: 100px 0;">Not found!</h1>
            </c:if>
            <c:forEach var="product" items="${products}">
                <div class="product">
                    <a href="<c:url value="/product?id=${product.productID}"/>"><img src="<c:url value="/asset/img/1.jpg"/>" width="100%"/></a>
                    <div class="name"><a href="<c:url value="/product?id=${product.productID}"/>">${product.productName}</a></div>
                    <div class="price">$${product.unitPrice}</div>
                    <c:if test="${product.unitsInStock > 0}">
                        <div><a href="<c:url value="/cart?action=buynow&id=${product.productID}"/>">Buy now</a></div>
                    </c:if>
                </div>
            </c:forEach>
        </div>
        <c:import url="/template/pagination-bar.jsp">
            <c:param name="page" value="${pageNumber}"/>
            <c:param name="modelDAOName" value="productDAO"/>
            <c:param name="basePath" value="/search?cid=${categoryID}&keyword=${keyword}"/>
        </c:import>
    </div>
</div>

<c:import url="/template/footer.jsp"/>