<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:import url="/template/admin-header.jsp"/>
<div id="content">
    <c:import url="/template/admin-left.jsp"/>
    <div id="content-right">
        <div class="path-admin">DELETE PRODUCT</b></div>
        <div class="content-main" style="justify-content: center;gap: 32px;padding: 100px 0;">
            <c:if test="${empty cannotDelete}">
                <h1 style="text-align: center;width: 100%;font-size: 2rem;">
                    Do you sure to delete product ${product.productName}
                </h1>
                <a href="<c:url value="/admin/deleteproduct-confirm?id=${product.productID}"/>">
                    <button type="button">Yes</button>
                </a>
                <a href="<c:url value="/admin/products"/>">
                    <button type="button">No</button>
                </a>
            </c:if>
            <c:if test="${cannotDelete}">
                <h1 style="text-align: center;width: 100%;font-size: 2rem;color: crimson;">
                    ${msg}
                </h1>
                <a href="<c:url value="/admin/products"/>">
                    <button type="button">Back to product list</button>
                </a>
            </c:if>
        </div>
    </div>
</div>
<c:import url="/template/admin-footer.jsp"/>