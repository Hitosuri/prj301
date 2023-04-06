<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="/template/header.jsp"/>

<div id="content">
    <c:import url="/template/profile-left.jsp"/>
    <div id="content-right">
        <div class="path">Personal information</b></div>
        <div class="content-main">
            <div id="profile-content">
                <div class="profile-content-col">
                    <div>Company name: <br/>${customer.companyName}</div>
                    <div>Contact name: <br/>${customer.contactName}</div>
                    <div>
                        <a href="<c:url value="/account/profile/edit"/>">
                            <button type="button">Edit info</button>
                        </a>
                    </div>
                </div>
                <div class="profile-content-col">
                    <div>Company title: <br/>${customer.contactTitle}</div>
                    <div>Address: <br/>${customer.address}</div>
                </div>
                <div class="profile-content-col">
                    <div>Email: <br/>${sessionScope.accSession.email}</div>
                </div>
            </div>
        </div>
    </div>
</div>
<c:import url="/template/footer.jsp"/>