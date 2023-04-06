<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:import url="/template/header.jsp"/>
<div id="content">
    <div id="content-detail">
        <div id="content-title">
            <a href="<c:url value="/"/>">Home</a> >
            <a href="<c:url value="/category?id=${category.categoryID}"/>">${category.categoryName}</a> >
            ${product.productName}
        </div>
        <div id="product">
            <div id="product-name">
                <h2>${product.productName}</h2>
                <div id="product-detail">
                    <div id="product-detail-left">
                        <div id="product-img">
                            <img src="<c:url value="/asset/img/1.jpg"/>"/>
                        </div>
                        <div id="product-img-items">
                            <div><a href="#"><img src="<c:url value="/asset/img/1.jpg"/>"/></a></div>
                            <div><a href="#"><img src="<c:url value="/asset/img/1.jpg"/>"/></a></div>
                            <div><a href="#"><img src="<c:url value="/asset/img/1.jpg"/>"/></a></div>
                            <div><a href="#"><img src="<c:url value="/asset/img/1.jpg"/>"/></a></div>
                        </div>
                    </div>
                    <div id="product-detail-right">
                        <div id="product-detail-right-content">
                            <div id="product-price">$ ${product.unitPrice}</div>
                            <div id="product-status">${product.unitsInStock > 0 ? "In stock" : "Out stock"}</div>
                            <div id="product-detail-buttons">
                                <c:if test="${product.unitsInStock > 0}">
                                    <div id="product-detail-button">
                                        <a href="<c:url value="/cart/buynow?id=${product.productID}"/>" style="text-decoration: none;">
                                            <input type="button" value="BUY NOW">
                                        </a>
                                        <a href="<c:url value="/cart/addtocart?id=${product.productID}"/>">
                                            <input type="button" value="ADD TO CART" style="background-color: #fff; color:red;border: 1px solid gray;">
                                        </a>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="info-detail">
            <div id="info-detail-title">
                <h2>Information deltail</h2>
                <div style="margin:10px auto;">
                    Lorem ipsum dolor sit amet consectetur adipisicing elit. Illum, debitis. Asperiores soluta eveniet eos accusantium doloremque cum suscipit ducimus enim at sapiente mollitia consequuntur dicta quaerat, sunt voluptates autem. Quam!
                    Lorem ipsum dolor, sit amet consectetur adipisicing elit. Rem illum autem veritatis maxime corporis quod quibusdam nostrum eaque laborum numquam quos unde eveniet aut, exercitationem voluptatum veniam fugiat, debitis esse?
                    Lorem ipsum dolor sit amet consectetur adipisicing elit. Distinctio eligendi ratione vitae nobis numquam dolorum assumenda saepe enim cumque blanditiis, deleniti neque voluptate vel ducimus in omnis harum aut nisi.
                </div>
            </div>
        </div>
    </div>
</div>
<c:import url="/template/footer.jsp"/>