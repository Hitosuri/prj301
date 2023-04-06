<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:import url="/template/admin-header.jsp"/>
<div id="content">
    <c:import url="/template/admin-left.jsp"/>
    <div id="content-right">
        <div class="path-admin">EDIT PRODUCT</b></div>
        <div class="content-main">
            <form action="<c:url value="/admin/editproduct"/>" method="POST" id="content-main-product">
                <input type="hidden" name="productId" value="${product.productID}">
                <div class="content-main-1">
                    <label>Product name (*):</label><br/>
                    <input type="text" name="Product name" value="${product.productName}"><br/>
                    <span class="msg-error">${requestScope['Product nameErr']}</span><br/>

                    <label>Unit price:</label><br/>
                    <input type="text" name="Unit price" value="${product.unitPrice}"><br/>
                    <span class="msg-error">${requestScope['Unit priceErr']}</span><br/>

                    <label>Quantity per unit:</label><br/>
                    <input type="text" name="Quantity per unit" value="${product.quantityPerUnit}"><br/>
                    <span class="msg-error">${requestScope['Quantity per unitErr']}</span><br/>

                    <label>Units in stock (*):</label><br/>
                    <input type="text" name="Units in stock" value="${product.unitsInStock}"><br/>
                    <span class="msg-error">${requestScope['Units in stockErr']}</span><br/>
                </div>
                <div class="content-main-1">
                    <label>Category (*):</label><br/>
                    <select name="Category">
                        <c:forEach items="${categories}" var="category">
                            <option value="${category.categoryID}" <c:if test="${product.categoryID == category.categoryID}">selected</c:if>>${category.categoryName}</option>
                        </c:forEach>
                    </select>
                    <br/>
                    <span class="msg-error">${requestScope.CategoryErr}</span><br/>

                    <label>Reorder level:</label><br/>
                    <input type="text" name="Reorder level" value="${product.reorderLevel}"><br/>
                    <span class="msg-error">${requestScope['Reorder levelErr']}</span><br/>

                    <label>Units on order:</label><br/>
                    <input type="text" name="Units on order" id="" value="${product.unitsOnOrder}" disabled><br/>

                    <label>Discontinued:</label><br/>
                    <input type="checkbox" name="Discontinued" <c:if test="${product.discontinued}">checked</c:if><br/><br/>

                    <input type="submit" value="Save"/>
                    <a href="<c:url value="/admin/products"/>">
                        <button type="button">Cancel</button>
                    </a>
                </div>
            </form>
        </div>
    </div>
</div>
<c:import url="/template/admin-footer.jsp"/>