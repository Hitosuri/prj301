<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Index</title>
        <link href="<c:url value="/asset/css/style.css"/>" rel="stylesheet"/>
        <script>
            function checkValid(input) {
                const submitButton = input.submitButton

                input.elements.forEach(e => {
                    const inputElement = e.input
                    const msgElement = e.msg

                    function toggleThis() {
                        msgElement.textContent = inputElement.value ? '' : e.name + ' is required'
                    }

                    inputElement.onfocus = () => {
                        inputElement.onfocus = null
                        inputElement.addEventListener('blur', toggleThis)
                        inputElement.addEventListener('input', toggleThis)
                    }
                })

                submitButton.addEventListener('click', event => {
                    if (!input.elements.every(e => e.input.value)) {
                        input.elements.forEach(e => {
                            e.msg.textContent = e.input.value ? '' : e.name + ' is required'
                        })
                        event.preventDefault()
                        return
                    }
                    if (input.extraRule) {
                        const falseRule = input.extraRule.find(e => !e.rule())
                        if (falseRule) {
                            event.preventDefault()
                            falseRule.action()
                        }
                    }
                })
            }
        </script>
    </head>
    <body>
        <div id="container">
            <div id="header">
                <div id="logo">
                    <a href="<c:url value="/"/>">
                        <img src="<c:url value="/asset/img/logo.png"/>"/>
                    </a>
                </div>
                <div id="banner">
                    <div class="search-box">
                        <form action="<c:url value="/search"/>">
                            <jsp:useBean id="categoryDAO" class="models.CategoryDAO"/>
                            <c:set var="categories" value="${categoryDAO.getCategories()}"/>
                            <select name="cid">
                                <option value="0">All</option>
                                <c:forEach items="${categories}" var="category">
                                    <option value="${category.categoryID}" <c:if test="${requestScope.categoryID == category.categoryID}">selected</c:if>>${category.categoryName}</option>
                                </c:forEach>
                            </select>
                            <input id="keyword" type="text" name="keyword" placeholder="Search..." value="${keyword}">
                            <button id="search-btn" type="submit">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 612.01 612.01" xml:space="preserve"><path d="M606.209 578.714 448.198 423.228C489.576 378.272 515 318.817 515 253.393 514.98 113.439 399.704 0 257.493 0S.006 113.439.006 253.393s115.276 253.393 257.487 253.393c61.445 0 117.801-21.253 162.068-56.586l158.624 156.099c7.729 7.614 20.277 7.614 28.006 0a19.291 19.291 0 0 0 .018-27.585zM257.493 467.8c-120.326 0-217.869-95.993-217.869-214.407S137.167 38.986 257.493 38.986c120.327 0 217.869 95.993 217.869 214.407S377.82 467.8 257.493 467.8z"/></svg>
                            </button>
                        </form>
                    </div>
                    <ul>
                        <li>
                            <a href="<c:url value="/cart"/>">
                                Cart: ${sessionScope.cart.size()}
                                <c:if test="${sessionScope.cart == null}">
                                    ${cookie.total.value}
                                    <c:if test="${cookie.total == null}">
                                        0
                                    </c:if>
                                </c:if>
                            </a>
                        </li>
                        <c:if test="${empty sessionScope.accSession}">
                            <li><a href="<c:url value="/account/signin"/>">SignIn</a></li>
                            <li><a href="<c:url value="/account/signup"/>">SignUp</a></li>
                        </c:if>
                        <c:if test="${sessionScope.accSession.role == 1}">
                            <li><a href="<c:url value="/admin/dashboard"/>">Dashboard</a></li>
                        </c:if>
                        <c:if test="${not empty sessionScope.accSession.customerID}">
                            <li><a href="<c:url value="/account/profile"/>">Profile</a></li>
                        </c:if>
                        <c:if test="${not empty sessionScope.accSession}">
                            <li><a href="<c:url value="/account/signout"/>">SignOut</a></li>
                        </c:if>
                    </ul>
                </div>
            </div>