<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:import url="/template/admin-header.jsp"/>
<div id="content">
    <c:import url="/template/admin-left.jsp"/>
    <div id="content-right">
        <div class="path-admin">CREATE A NEW PRODUCT</b></div>
        <div class="content-main">
            <form action="<c:url value="/admin/createproduct"/>" method="POST" id="content-main-product">
                <div class="content-main-1">
                    <label>Product name (*):</label><br/>
                    <input type="text" name="Product name" value="${requestScope['Product name']}"><br/>
                    <span class="msg-error">${requestScope['Product nameErr']}</span><br/>
                    
                    <label>Unit price:</label><br/>
                    <input type="text" name="Unit price" value="${requestScope['Unit price']}"><br/>
                    <span class="msg-error">${requestScope['Unit priceErr']}</span><br/>
                    
                    <label>Quantity per unit:</label><br/>
                    <input type="text" name="Quantity per unit" value="${requestScope['Quantity per unit']}"><br/>
                    <span class="msg-error">${requestScope['Quantity per unitErr']}</span><br/>
                    
                    <label>Units in stock (*):</label><br/>
                    <input type="text" name="Units in stock" value="${requestScope['Units in stock']}"><br/>
                    <span class="msg-error">${requestScope['Units in stockErr']}</span><br/>
                </div>
                <div class="content-main-1">
                    <label>Category (*):</label><br/>
                    <select name="Category">
                        <c:forEach items="${categories}" var="category">
                            <option value="${category.categoryID}" <c:if test="${requestScope.Category == category.categoryID}">selected</c:if>>${category.categoryName}</option>
                        </c:forEach>
                    </select>
                    <br/>
                    <span class="msg-error">${requestScope.CategoryErr}</span><br/>
                    
                    <label>Reorder level:</label><br/>
                    <input type="text" name="Reorder level" value="${requestScope['Reorder level']}"><br/>
                    <span class="msg-error">${requestScope['Reorder levelErr']}</span><br/>
                    
                    <label>Units on order:</label><br/>
                    <input type="text" name="Units on order" id="" value="0" disabled><br/>
                    
                    <label>Discontinued:</label><br/>
                    <input type="checkbox" name="Discontinued" value="${requestScope.Discontinued}"><br/>
                    
                    <input type="submit" value="Save"/>
                </div>
            </form>
        </div>
    </div>
</div>
<c:import url="/template/admin-footer.jsp"/>