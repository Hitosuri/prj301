<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div id="content-left">
    <h3>CATEGORY</h3>
    <jsp:useBean id="categoryDAO" class="models.CategoryDAO"/>
    <c:set var="categories" value="${categoryDAO.getCategories()}"/>
    <ul>
        <c:forEach items="${categories}" var="category">
            <a href="<c:url value="/category?id=${category.categoryID}"/>">
                <li <c:if test="${requestScope.category.categoryID == category.categoryID}">class="show"</c:if>>${category.categoryName}</li>
                </a>
        </c:forEach>
    </ul>
</div>