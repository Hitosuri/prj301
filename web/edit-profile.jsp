<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="/template/header.jsp"/>

<div id="content">
    <c:import url="/template/profile-left.jsp"/>
    <div id="content-right">
        <div class="path">Personal information</b></div>
        <div class="content-main">
            <form action="applychange" method="POST" id="profile-content">
                <div class="profile-content-col">
                    <div>Company name: <br/>
                        <input type="text" name="Company name" value="${customer.companyName}${requestScope["Company name"]}">
                        <br/>
                        <span id="company-msg" class="msg-error">${requestScope["Company nameErr"]}</span>
                    </div>
                    <div>Contact name: <br/>
                        <input type="text" name="Contact name" value="${customer.contactName}${requestScope["Contact name"]}">
                        <br/>
                        <span id="contact-name-msg" class="msg-error">${requestScope["Contact nameErr"]}</span>
                    </div>
                    <div>
                        <input type="submit" value="Apply change"/>
                        <a href="<c:url value="/account/profile"/>">
                            <button type="button">Cancel</button>
                        </a>
                    </div>
                </div>
                <div class="profile-content-col">
                    <div>Company title: <br/>
                        <input name="Contact title" type="text" value="${customer.contactTitle}${requestScope["Contact title"]}"/>
                        <br/>
                        <span id="contact-title-msg" class="msg-error">${requestScope["Contact titleErr"]}</span>
                    </div>
                    <div>Address: <br/>
                        <input name="Address" type="text" value="${customer.address}${Address}"/>
                        <br/>
                        <span id="address-msg" class="msg-error">${AddressErr}</span>
                    </div>
                </div>
                <div class="profile-content-col">
                    <div>Email: <br/>
                        <input name="Email" type="text" value="${account.email}${Email}"/>
                        <br/>
                        <span id="email-msg" class="msg-error">${EmailErr}</span>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<c:import url="/template/footer.jsp"/>