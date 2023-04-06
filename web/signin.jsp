<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:import url="/template/header.jsp"/>
<div id="content">
    <div id="form">
        <div id="form-title">
            <span><a href="signup">SIGN UP</a></span>
            <span><a href="signin" style="color: red;">SIGN IN</a></span>
        </div>
        <c:if test="${sessionScope.signupSuccess}">
            <h1 style="text-align: center;color: forestgreen;font-size: 1.2rem;padding: 12px 0;">
                Signup successfully, now you can signin
            </h1>
            <c:remove var="signupSuccess" scope="session"/>
        </c:if>
        <div id="form-content">
            <form action="signin" method="post">
                <label>Email<span style="color: red;">*</span></label><br/>
                <input id="email" type="text" name="email"/><br/>
                <span id="email-msg" class="msg-error">${errMsg}</span><br/>
                <label>Password<span style="color: red;">*</span></label><br/>
                <input id="pass" type="password" name="pass"/><br/>
                <span id="pass-msg" class="msg-error">${errMsg}</span><br/>
                <div><a href="forgot">Forgot password?</a></div>
                <input id="submit-button" type="submit" value="SIGN IN"/><br/>
                <input type="button" value="FACEBOOK LOGIN" style="background-color: #3b5998;"/><br/>
                <input type="button" value="ZALO LOGIN" style="background-color: #009dff;margin-bottom: 30px;"/>
            </form>
        </div>
    </div>
</div>
<script>
    const iE = {
        submitButton: document.querySelector('#submit-button'),
        elements: [
            {
                name: 'Email',
                input: document.querySelector('#email'),
                msg: document.querySelector('#email-msg')
            },
            {
                name: 'Password',
                input: document.querySelector('#pass'),
                msg: document.querySelector('#pass-msg')
            }
        ]
    }
    checkValid(iE)
</script>
<c:import url="/template/footer.jsp"/>
