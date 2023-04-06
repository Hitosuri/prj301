<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/template/header.jsp"/>
<c:if test="${customer != null}" var="isCustomer"/>
<div id="content">
    <div id="cart">
        <div id="cart-title">
            <h3>SHOPPING CART</h3>
        </div>
        <div id="cart-content">
            <c:if test="${cart == null || cart.size() == 0}" var="isEmpty">
                <h1 style="text-align: center;font-size: 2rem;">${orderMsg == null ? "Your cart is empty" : orderMsg}</h1>
            </c:if>
            <c:forEach items="${cart}" var="item">
                <div class="cart-item">
                    <div class="cart-item-infor">
                        <div class="cart-item-img">
                            <img src="<c:url value="/asset/img/1.jpg"/>"/>
                        </div>
                        <div class="cart-item-name">
                            <a href="<c:url value="/product?id=${item.key.productID}"/>">${item.key.productName}</a>
                        </div>
                        <div class="cart-item-price">
                            ${item.key.unitPrice * item.value} $
                        </div>
                        <div class="cart-item-button">
                            <a href="<c:url value="/cart/remove?id=${item.key.productID}"/>">Remove</a>
                        </div>
                    </div>
                    <div class="cart-item-function">
                        <a href="<c:url value="/cart/decrease?id=${item.key.productID}"/>">-</a>
                        <c:if test="${item.key.unitsInStock > item.value}">
                            <a href="<c:url value="/cart/increase?id=${item.key.productID}"/>">+</a>
                        </c:if>
                        <c:if test="${item.key.unitsInStock <= item.value}">
                            <span class="disabled-anchor">+</span>
                        </c:if>
                        <input type="text" value="${item.value}" disabled/>
                        <c:if test="${item.key.unitsInStock <= item.value}">
                            <span class="msg-error">Max product reached, cannot increase.</span>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div id="cart-summary">
            <c:if test="${!isEmpty}">
                <div id="cart-summary-content">Total amount: <span style="color:red">${totalPrice} $</span></div>
            </c:if>
        </div>
        <c:if test="${!isEmpty}">
            <form action="<c:url value="/cart/submitorder"/>" method="post">
                <div id="customer-info">
                    <div id="customer-info-content">
                        <h3>CUSTOMER INFORMATION:</h3>
                        <div id="customer-info-detail">
                            <div id="customer-info-left">
                                <span id="company-msg" class="msg-error">${requestScope["Company nameErr"]}</span>
                                <input type="text" name="Company name" placeholder="Company name *" <c:if test="${isCustomer}">disabled</c:if> value="${customer.companyName}${requestScope["Company name"]}"/><br/>
                                
                                <span id="contact-name-msg" class="msg-error">${requestScope["Contact nameErr"]}</span>
                                <input type="text" name="Contact name" placeholder="Contact name *" <c:if test="${isCustomer}">disabled</c:if> value="${customer.contactName}${requestScope["Contact name"]}"/><br/>
                                
                                <span id="require-time-msg" class="msg-error">${requestScope["Require timeErr"]}</span>
                                <input type="date" name="Require time" placeholder="Require time (dd/MM/yyyy) *" value="${requestScope["Require time"]}"/><br/>
                            </div>
                            <div id="customer-info-right">
                                <span id="contact-title-msg" class="msg-error">${requestScope["Contact titleErr"]}</span>
                                <input type="text" name="Contact title" placeholder="Contact title *" <c:if test="${isCustomer}">disabled</c:if> value="${customer.contactTitle}${requestScope["Contact title"]}"/><br/>
                                
                                <span id="address-msg" class="msg-error">${AddressErr}</span>
                                <input type="text" name="Address" placeholder="Address *" <c:if test="${isCustomer}">disabled</c:if> value="${customer.address}${Address}"/><br/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="customer-info">
                        <div id="customer-info-content">
                            <h3>PAYMENT METHODS:</h3>
                            <div id="customer-info-payment">
                                <div>
                                    <input type="radio" name="rbPaymentMethod" checked/>
                                    Payment C.O.D - Payment on delivery
                                </div>
                                <div>
                                    <input type="radio" name="rbPaymentMethod" disabled/>
                                    Payment via online payment gateway
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="cart-order">
                        <input type="submit" value="CONFIRM ORDER"/>
                    </div>
                </form>
        </c:if>
    </div>
</div>
<script>
    const iE = {
        submitButton: document.querySelector('#submit-button'),
        elements: [
            {
                name: 'Company name',
                input: document.querySelector('#company'),
                msg: document.querySelector('#company-msg')
            },
            {
                name: 'Contact name',
                input: document.querySelector('#contact-name'),
                msg: document.querySelector('#contact-name-msg')
            },
            {
                name: 'Contact title',
                input: document.querySelector('#contact-title'),
                msg: document.querySelector('#contact-title-msg')
            },
            {
                name: 'Address',
                input: document.querySelector('#address'),
                msg: document.querySelector('#address-msg')
            },
            {
                name: 'Require',
                input: document.querySelector('#require-time'),
                msg: document.querySelector('#require-time-msg')
            }
        ],
    }
    checkValid(iE)
</script>
<c:import url="/template/footer.jsp"/>