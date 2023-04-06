<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:if test="${empty pageNumber}">
    <c:set scope="page" var="pageNumber" value="${param.page != null ? param.page : 1}"/>
</c:if>
<c:if test="${empty modelDAO}">
    <c:set scope="page" var="modelDAO" value="${requestScope[param.modelDAOName]}"/>
</c:if>
<c:set scope="page" var="pageRange" value="${modelDAO.getPageRange(pageNumber)}"/>
<h1>${pageRange[0]}</h1>
<h1>${pageRange[1]}</h1>
<div id="paging">
    <div class="pagination">
        <c:if test="${modelDAO.totalPage > 1}">
            <c:if test="${pageNumber != 1}">
                <a href="
                   <c:url value="${param.basePath}">
                       <c:param name="page" value="${pageNumber - 1}"/>
                   </c:url>
                   ">
                    &laquo;
                </a>
            </c:if>

            <c:if test="${pageRange[0] != 1}">
                <span>...</span>
            </c:if>

            <c:forEach begin="${pageRange[0]}" end="${pageRange[1]}" var="page">
                <a href="
                   <c:url value="${param.basePath}">
                       <c:param name="page" value="${page}"/>
                   </c:url>
                   " <c:if test="${pageNumber == page}">class="active"</c:if>>
                    ${page}
                </a>
            </c:forEach>

            <c:if test="${pageRange[1] != modelDAO.totalPage}">
                <span>...</span>
            </c:if>

            <c:if test="${pageNumber != modelDAO.totalPage}">
                <a href="
                   <c:url value="${param.basePath}">
                       <c:param name="page" value="${pageNumber + 1}"/>
                   </c:url>
                   ">&raquo;
                </a>
            </c:if>
        </c:if>
    </div>
</div>
