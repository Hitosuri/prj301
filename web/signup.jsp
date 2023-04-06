<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:import url="/template/header.jsp"/>
<div id="content">
    <div id="form">
        <div id="form-title">
            <span><a href="signup" style="color: red;">SIGN UP</a></span>
            <span><a href="signin">SIGN IN</a></span>
        </div>
        <div id="form-content">
            <form action="signup" method="post">
                <label>Company name<span style="color: red;">*</span></label><br/>
                <input name="Company name" id="company" type="text" value="${requestScope["Company name"]}"/><br/>
                <span id="company-msg" class="msg-error">${requestScope["Company nameErr"]}</span><br/>
                
                <label>Contact name<span style="color: red;">*</span></label><br/>
                ${ContactnameErr}
                <input name="Contact name" id="contact-name" type="text" value="${requestScope["Contact name"]}"/><br/>
                <span id="contact-name-msg" class="msg-error">${requestScope["Contact nameErr"]}</span><br/>
                
                <label>Contact title<span style="color: red;">*</span></label><br/>
                <input name="Contact title" id="contact-title" type="text" value="${requestScope["Contact title"]}"/><br/>
                <span id="contact-title-msg" class="msg-error">${requestScope["Contact titleErr"]}</span><br/>
                
                <label>Address<span style="color: red;">*</span></label><br/>
                <input name="Address" id="address" type="text" value="${Address}"/><br/>
                <span id="address-msg" class="msg-error">${AddressErr}</span><br/>
                
                <label>Email<span style="color: red;">*</span></label><br/>
                <input name="Email" id="email" type="text" value="${Email}"/><br/>
                <span id="email-msg" class="msg-error">${EmailErr}</span><br/>
                
                <label>Password<span style="color: red;">*</span></label><br/>
                <input name="Password" id="password" type="password" value="${Password}"/><br/>
                <span id="password-msg" class="msg-error">${PasswordErr}</span><br/>
                
                <label>Re-Password<span style="color: red;">*</span></label><br/>
                <input name="RePassword" id="repassword" type="password" value="${RePassword}"/><br/>
                <span id="repassword-msg" class="msg-error">${RePasswordErr}</span><br/>
                
                <div></div>
                <input id="submit-button" type="submit" value="SIGN UP" style="margin-bottom: 30px;"/>
            </form>
        </div>
    </div>
</div>
<script>
    const passwordInput = document.querySelector('#password')
    const rePasswordInput = document.querySelector('#repassword')
    const rePasswordInputMsg = document.querySelector('#repassword-msg')
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
                name: 'Email',
                input: document.querySelector('#email'),
                msg: document.querySelector('#email-msg')
            },
            {
                name: 'Password',
                input: passwordInput,
                msg: document.querySelector('#password-msg')
            },
            {
                name: 'Re-Password',
                input: rePasswordInput,
                msg: rePasswordInputMsg
            },
        ],
        extraRule: [
            {
                rule: () => rePasswordInput.value.trim() && passwordInput.value === rePasswordInput.value,
                action: () => {
                    rePasswordInputMsg.textContent = 'Password and Re-Password does not match!'
                }
            }
        ]
    }
    checkValid(iE)
</script>
<c:import url="/template/footer.jsp"/>
