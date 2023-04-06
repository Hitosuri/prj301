<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:import url="/template/admin-header.jsp"/>
<c:set scope="page" var="pageNumber" value="${param.page != null ? param.page : 1}"/>
<c:if test="${!(pageNumber >= 1 && pageNumber <= productDAO.totalPage)}">
    <c:redirect url="/admin/products"/>
</c:if>
<div id="content">
    <c:import url="/template/admin-left.jsp"/>
    <div id="content-right">
        <div class="path-admin">PRODUCTS LIST</b></div>
        <div class="content-main">
            <div id="content-main-dashboard">
                <div id="product-title-header">
                    <div id="product-title-1" style="width: 25%;">
                        <b>Filter by Catetory:</b>
                        <form action="<c:url value="/admin/products"/>">
                            <select name="cid">
                                <option value="0">All</option>
                                <c:forEach items="${categories}" var="category">
                                    <option value="${category.categoryID}" <c:if test="${requestScope.categoryID == category.categoryID}">selected</c:if>>${category.categoryName}</option>
                                </c:forEach>
                            </select>
                            <input type="submit" value="Filter"/>
                        </form>
                    </div>
                    <div id="product-title-2" style="width: 55%;">
                        <form action="<c:url value="/admin/products"/>">
                            <input type="text" name="keyword" placeholder="Enter product name to search" value="${keyword}"/>
                            <input type="submit" value="Search"/>
                                   </form>
                            </div>
                            <div id="product-title-3" style="width: 20%;">
                                <a href="<c:url value="/admin/createproduct"/>">Create a new Product</a><br/>
                                <form action="">
                                    <label for="upload-file">Import .xls or .xlsx file</label>
                                    <input type="file" name="file" id="upload-file" />
                                </form>
                            </div>
                    </div>
                    <div id="order-table-admin">
                        <table id="orders">
                            <tr>
                                <th>ProductID</th>
                                <th>ProductName</th>
                                <th>UnitPrice</th>
                                <th>Unit</th>
                                <th>UnitsInStock</th>
                                <th>Category</th>
                                <th>Discontinued</th>
                                <th></th>
                            </tr>
                            <c:forEach var="product" items="${productDAO.getItemsInPage(pageNumber)}">
                                <tr>
                                    <td><a href="<c:url value="/product?id=${product.productID}"/>">#${product.productID}</a></td>
                                    <td>${product.productName}</td>
                                    <td>${product.unitPrice}</td>
                                    <td>${product.quantityPerUnit}</td>
                                    <td>${product.unitsInStock}</td>
                                    <td>${product.categoryID}</td>
                                    <td>${product.discontinued}</td>
                                    <td>
                                        <a href="<c:url value="/admin/editproduct?id=${product.productID}"/>">Edit</a> &nbsp; | &nbsp; 
                                        <a href="<c:url value="/admin/deleteproduct?id=${product.productID}"/>">Delete</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                    <c:url value="admin/products" var="baseUrl">
                        <c:if test="${not empty categoryID}">
                            <c:param name="cid" value="${categoryID}"/>
                        </c:if>
                        <c:if test="${not empty keyword}">
                            <c:param name="keyword" value="${keyword}"/>
                        </c:if>
                    </c:url>
                    <c:import url="/template/pagination-bar.jsp">
                        <c:param name="page" value="${pageNumber}"/>
                        <c:param name="modelDAOName" value="productDAO"/>
                        <c:param name="basePath" value="/${baseUrl}"/>
                    </c:import>
                </div>
            </div>
        </div>
    </div>
</div>
<c:import url="/template/admin-footer.jsp"/>