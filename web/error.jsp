<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error</title>
        <style>
            body {
                margin: 0;
                font-size: 20px;
            }

            * {
                box-sizing: border-box;
            }

            .container {
                position: relative;
                display: flex;
                align-items: center;
                justify-content: center;
                height: 100vh;
                background: white;
                color: black;
                font-family: arial, sans-serif;
                overflow: hidden;
            }

            .content {
                position: relative;
                width: 600px;
                max-width: 100%;
                margin: 20px;
                background: white;
                padding: 60px 40px;
                text-align: center;
                box-shadow: -10px 10px 67px -12px rgba(0, 0, 0, 0.2);
                opacity: 0;
                animation: apparition 0.8s 0.3s cubic-bezier(0.39, 0.575, 0.28, 0.995) forwards;
            }
            .content p {
                font-size: 1.3rem;
                margin-top: 0;
                margin-bottom: 0.6rem;
                letter-spacing: 0.1rem;
                color: #595959;
            }
            .content p:last-child {
                margin-bottom: 0;
            }
            .content button {
                display: inline-block;
                margin-top: 2rem;
                padding: 0.5rem 1rem;
                border: 3px solid #595959;
                background: transparent;
                font-size: 1rem;
                color: #595959;
                text-decoration: none;
                cursor: pointer;
                font-weight: bold;
            }

            .particle {
                position: absolute;
                display: block;
                pointer-events: none;
            }
            .particle:nth-child(1) {
                top: 49.8777506112%;
                left: 83.4970530452%;
                font-size: 18px;
                filter: blur(0.02px);
                animation: 25s floatReverse2 infinite;
            }
            .particle:nth-child(2) {
                top: 58.6080586081%;
                left: 15.7016683023%;
                font-size: 19px;
                filter: blur(0.04px);
                animation: 21s floatReverse2 infinite;
            }
            .particle:nth-child(3) {
                top: 5.8536585366%;
                left: 20.5882352941%;
                font-size: 20px;
                filter: blur(0.06px);
                animation: 40s floatReverse2 infinite;
            }
            .particle:nth-child(4) {
                top: 1.9393939394%;
                left: 28.2926829268%;
                font-size: 25px;
                filter: blur(0.08px);
                animation: 38s floatReverse2 infinite;
            }
            .particle:nth-child(5) {
                top: 86.6180048662%;
                left: 80.2348336595%;
                font-size: 22px;
                filter: blur(0.1px);
                animation: 35s float2 infinite;
            }
            .particle:nth-child(6) {
                top: 77.9537149817%;
                left: 37.2184133203%;
                font-size: 21px;
                filter: blur(0.12px);
                animation: 28s float infinite;
            }
            .particle:nth-child(7) {
                top: 42.9268292683%;
                left: 20.5882352941%;
                font-size: 20px;
                filter: blur(0.14px);
                animation: 36s floatReverse2 infinite;
            }
            .particle:nth-child(8) {
                top: 90.5109489051%;
                left: 37.1819960861%;
                font-size: 22px;
                filter: blur(0.16px);
                animation: 23s float2 infinite;
            }
            .particle:nth-child(9) {
                top: 37.8181818182%;
                left: 8.7804878049%;
                font-size: 25px;
                filter: blur(0.18px);
                animation: 24s floatReverse2 infinite;
            }
            .particle:nth-child(10) {
                top: 53.9215686275%;
                left: 26.5748031496%;
                font-size: 16px;
                filter: blur(0.2px);
                animation: 27s floatReverse infinite;
            }
            .particle:nth-child(11) {
                top: 75.9556103576%;
                left: 88.0316518299%;
                font-size: 11px;
                filter: blur(0.22px);
                animation: 26s float2 infinite;
            }
            .particle:nth-child(12) {
                top: 82.0265379976%;
                left: 35.9572400389%;
                font-size: 29px;
                filter: blur(0.24px);
                animation: 30s floatReverse infinite;
            }
            .particle:nth-child(13) {
                top: 40.2948402948%;
                left: 87.7712031558%;
                font-size: 14px;
                filter: blur(0.26px);
                animation: 29s floatReverse infinite;
            }
            .particle:nth-child(14) {
                top: 96.2332928311%;
                left: 14.6627565982%;
                font-size: 23px;
                filter: blur(0.28px);
                animation: 21s floatReverse infinite;
            }
            .particle:nth-child(15) {
                top: 32.15590743%;
                left: 26.444662096%;
                font-size: 21px;
                filter: blur(0.3px);
                animation: 38s floatReverse infinite;
            }
            .particle:nth-child(16) {
                top: 66.5060240964%;
                left: 56.3106796117%;
                font-size: 30px;
                filter: blur(0.32px);
                animation: 32s floatReverse2 infinite;
            }
            .particle:nth-child(17) {
                top: 65.5256723716%;
                left: 43.2220039293%;
                font-size: 18px;
                filter: blur(0.34px);
                animation: 36s floatReverse infinite;
            }
            .particle:nth-child(18) {
                top: 61.7647058824%;
                left: 16.7322834646%;
                font-size: 16px;
                filter: blur(0.36px);
                animation: 34s float infinite;
            }
            .particle:nth-child(19) {
                top: 21.5158924205%;
                left: 25.5402750491%;
                font-size: 18px;
                filter: blur(0.38px);
                animation: 39s float infinite;
            }
            .particle:nth-child(20) {
                top: 10.6537530266%;
                left: 80.8966861598%;
                font-size: 26px;
                filter: blur(0.4px);
                animation: 26s float2 infinite;
            }
            .particle:nth-child(21) {
                top: 61.0086100861%;
                left: 13.8203356367%;
                font-size: 13px;
                filter: blur(0.42px);
                animation: 24s floatReverse2 infinite;
            }
            .particle:nth-child(22) {
                top: 84.7290640394%;
                left: 51.3833992095%;
                font-size: 12px;
                filter: blur(0.44px);
                animation: 29s float2 infinite;
            }
            .particle:nth-child(23) {
                top: 36.4981504316%;
                left: 76.1622156281%;
                font-size: 11px;
                filter: blur(0.46px);
                animation: 33s floatReverse infinite;
            }
            .particle:nth-child(24) {
                top: 93.4809348093%;
                left: 6.9101678184%;
                font-size: 13px;
                filter: blur(0.48px);
                animation: 25s floatReverse2 infinite;
            }
            .particle:nth-child(25) {
                top: 33.8983050847%;
                left: 26.3157894737%;
                font-size: 26px;
                filter: blur(0.5px);
                animation: 22s floatReverse2 infinite;
            }
            .particle:nth-child(26) {
                top: 25.2121212121%;
                left: 65.3658536585%;
                font-size: 25px;
                filter: blur(0.52px);
                animation: 21s floatReverse2 infinite;
            }
            .particle:nth-child(27) {
                top: 38.5542168675%;
                left: 62.1359223301%;
                font-size: 30px;
                filter: blur(0.54px);
                animation: 33s float2 infinite;
            }
            .particle:nth-child(28) {
                top: 22.6322263223%;
                left: 40.473840079%;
                font-size: 13px;
                filter: blur(0.56px);
                animation: 23s float infinite;
            }
            .particle:nth-child(29) {
                top: 13.4939759036%;
                left: 83.4951456311%;
                font-size: 30px;
                filter: blur(0.58px);
                animation: 37s floatReverse2 infinite;
            }
            .particle:nth-child(30) {
                top: 16.5048543689%;
                left: 34.1796875%;
                font-size: 24px;
                filter: blur(0.6px);
                animation: 23s floatReverse2 infinite;
            }
            .particle:nth-child(31) {
                top: 48.9596083231%;
                left: 33.4316617502%;
                font-size: 17px;
                filter: blur(0.62px);
                animation: 37s floatReverse infinite;
            }
            .particle:nth-child(32) {
                top: 33.04981774%;
                left: 49.853372434%;
                font-size: 23px;
                filter: blur(0.64px);
                animation: 21s floatReverse infinite;
            }
            .particle:nth-child(33) {
                top: 22.4938875306%;
                left: 18.6640471513%;
                font-size: 18px;
                filter: blur(0.66px);
                animation: 30s floatReverse2 infinite;
            }
            .particle:nth-child(34) {
                top: 22.6044226044%;
                left: 59.1715976331%;
                font-size: 14px;
                filter: blur(0.68px);
                animation: 40s float infinite;
            }
            .particle:nth-child(35) {
                top: 60.024600246%;
                left: 77.9861796644%;
                font-size: 13px;
                filter: blur(0.7px);
                animation: 21s float2 infinite;
            }
            .particle:nth-child(36) {
                top: 40.146878825%;
                left: 74.7295968535%;
                font-size: 17px;
                filter: blur(0.72px);
                animation: 28s floatReverse infinite;
            }
            .particle:nth-child(37) {
                top: 92.9782082324%;
                left: 58.4795321637%;
                font-size: 26px;
                filter: blur(0.74px);
                animation: 34s floatReverse2 infinite;
            }
            .particle:nth-child(38) {
                top: 28.431372549%;
                left: 22.6377952756%;
                font-size: 16px;
                filter: blur(0.76px);
                animation: 29s floatReverse2 infinite;
            }
            .particle:nth-child(39) {
                top: 22.2760290557%;
                left: 38.9863547758%;
                font-size: 26px;
                filter: blur(0.78px);
                animation: 29s floatReverse infinite;
            }
            .particle:nth-child(40) {
                top: 39.7575757576%;
                left: 47.8048780488%;
                font-size: 25px;
                filter: blur(0.8px);
                animation: 29s floatReverse2 infinite;
            }
            .particle:nth-child(41) {
                top: 91.7073170732%;
                left: 45.0980392157%;
                font-size: 20px;
                filter: blur(0.82px);
                animation: 40s floatReverse infinite;
            }
            .particle:nth-child(42) {
                top: 74.9391727494%;
                left: 95.8904109589%;
                font-size: 22px;
                filter: blur(0.84px);
                animation: 36s floatReverse infinite;
            }
            .particle:nth-child(43) {
                top: 18.4466019417%;
                left: 76.171875%;
                font-size: 24px;
                filter: blur(0.86px);
                animation: 39s float2 infinite;
            }
            .particle:nth-child(44) {
                top: 50.9179926561%;
                left: 28.5152409046%;
                font-size: 17px;
                filter: blur(0.88px);
                animation: 26s float2 infinite;
            }
            .particle:nth-child(45) {
                top: 61.1650485437%;
                left: 1.953125%;
                font-size: 24px;
                filter: blur(0.9px);
                animation: 36s floatReverse2 infinite;
            }
            .particle:nth-child(46) {
                top: 32.4723247232%;
                left: 30.602171767%;
                font-size: 13px;
                filter: blur(0.92px);
                animation: 39s float2 infinite;
            }
            .particle:nth-child(47) {
                top: 55.2404438964%;
                left: 90.9990108803%;
                font-size: 11px;
                filter: blur(0.94px);
                animation: 29s float2 infinite;
            }
            .particle:nth-child(48) {
                top: 57.8431372549%;
                left: 25.5905511811%;
                font-size: 16px;
                filter: blur(0.96px);
                animation: 31s floatReverse2 infinite;
            }
            .particle:nth-child(49) {
                top: 8.8343558282%;
                left: 35.4679802956%;
                font-size: 15px;
                filter: blur(0.98px);
                animation: 34s float infinite;
            }
            .particle:nth-child(50) {
                top: 78.640776699%;
                left: 52.734375%;
                font-size: 24px;
                filter: blur(1px);
                animation: 28s float2 infinite;
            }
            .particle:nth-child(51) {
                top: 86.3803680982%;
                left: 25.6157635468%;
                font-size: 15px;
                filter: blur(1.02px);
                animation: 21s float infinite;
            }
            .particle:nth-child(52) {
                top: 32.7710843373%;
                left: 38.8349514563%;
                font-size: 30px;
                filter: blur(1.04px);
                animation: 29s float2 infinite;
            }
            .particle:nth-child(53) {
                top: 83.8471023428%;
                left: 36.5974282888%;
                font-size: 11px;
                filter: blur(1.06px);
                animation: 36s float2 infinite;
            }
            .particle:nth-child(54) {
                top: 55.4744525547%;
                left: 38.1604696673%;
                font-size: 22px;
                filter: blur(1.08px);
                animation: 36s floatReverse2 infinite;
            }
            .particle:nth-child(55) {
                top: 42.4607961399%;
                left: 22.351797862%;
                font-size: 29px;
                filter: blur(1.1px);
                animation: 32s floatReverse2 infinite;
            }
            .particle:nth-child(56) {
                top: 56.157635468%;
                left: 3.95256917%;
                font-size: 12px;
                filter: blur(1.12px);
                animation: 21s float infinite;
            }
            .particle:nth-child(57) {
                top: 29.9516908213%;
                left: 42.8015564202%;
                font-size: 28px;
                filter: blur(1.14px);
                animation: 35s floatReverse infinite;
            }
            .particle:nth-child(58) {
                top: 10.6537530266%;
                left: 18.5185185185%;
                font-size: 26px;
                filter: blur(1.16px);
                animation: 21s float infinite;
            }
            .particle:nth-child(59) {
                top: 92.0440636475%;
                left: 31.465093412%;
                font-size: 17px;
                filter: blur(1.18px);
                animation: 24s float infinite;
            }
            .particle:nth-child(60) {
                top: 69.4376528117%;
                left: 15.7170923379%;
                font-size: 18px;
                filter: blur(1.2px);
                animation: 22s floatReverse infinite;
            }
            .particle:nth-child(61) {
                top: 29.520295203%;
                left: 68.1145113524%;
                font-size: 13px;
                filter: blur(1.22px);
                animation: 40s float2 infinite;
            }
            .particle:nth-child(62) {
                top: 38.9768574909%;
                left: 61.7042115573%;
                font-size: 21px;
                filter: blur(1.24px);
                animation: 25s floatReverse infinite;
            }
            .particle:nth-child(63) {
                top: 89.1041162228%;
                left: 92.5925925926%;
                font-size: 26px;
                filter: blur(1.26px);
                animation: 31s floatReverse infinite;
            }
            .particle:nth-child(64) {
                top: 82.0265379976%;
                left: 90.3790087464%;
                font-size: 29px;
                filter: blur(1.28px);
                animation: 29s float2 infinite;
            }
            .particle:nth-child(65) {
                top: 12.6520681265%;
                left: 95.8904109589%;
                font-size: 22px;
                filter: blur(1.3px);
                animation: 22s float2 infinite;
            }
            .particle:nth-child(66) {
                top: 94.8004836759%;
                left: 26.2901655307%;
                font-size: 27px;
                filter: blur(1.32px);
                animation: 34s float2 infinite;
            }
            .particle:nth-child(67) {
                top: 88.6746987952%;
                left: 11.6504854369%;
                font-size: 30px;
                filter: blur(1.34px);
                animation: 25s float infinite;
            }
            .particle:nth-child(68) {
                top: 84.1596130593%;
                left: 14.605647517%;
                font-size: 27px;
                filter: blur(1.36px);
                animation: 40s float infinite;
            }
            .particle:nth-child(69) {
                top: 70.243902439%;
                left: 40.1960784314%;
                font-size: 20px;
                filter: blur(1.38px);
                animation: 29s floatReverse infinite;
            }
            .particle:nth-child(70) {
                top: 84.6248462485%;
                left: 60.2171767029%;
                font-size: 13px;
                filter: blur(1.4px);
                animation: 28s floatReverse2 infinite;
            }
            .particle:nth-child(71) {
                top: 39.9512789281%;
                left: 62.6836434868%;
                font-size: 21px;
                filter: blur(1.42px);
                animation: 28s floatReverse infinite;
            }
            .particle:nth-child(72) {
                top: 21.4111922141%;
                left: 32.28962818%;
                font-size: 22px;
                filter: blur(1.44px);
                animation: 31s float infinite;
            }
            .particle:nth-child(73) {
                top: 21.3851761847%;
                left: 19.550342131%;
                font-size: 23px;
                filter: blur(1.46px);
                animation: 23s float2 infinite;
            }
            .particle:nth-child(74) {
                top: 78.5454545455%;
                left: 83.9024390244%;
                font-size: 25px;
                filter: blur(1.48px);
                animation: 37s float infinite;
            }
            .particle:nth-child(75) {
                top: 40.7272727273%;
                left: 94.6341463415%;
                font-size: 25px;
                filter: blur(1.5px);
                animation: 24s float infinite;
            }
            .particle:nth-child(76) {
                top: 80.9756097561%;
                left: 87.2549019608%;
                font-size: 20px;
                filter: blur(1.52px);
                animation: 25s floatReverse infinite;
            }
            .particle:nth-child(77) {
                top: 6.8796068796%;
                left: 52.2682445759%;
                font-size: 14px;
                filter: blur(1.54px);
                animation: 33s floatReverse2 infinite;
            }
            .particle:nth-child(78) {
                top: 11.7647058824%;
                left: 5.905511811%;
                font-size: 16px;
                filter: blur(1.56px);
                animation: 38s floatReverse2 infinite;
            }
            .particle:nth-child(79) {
                top: 84.2615012107%;
                left: 85.7699805068%;
                font-size: 26px;
                filter: blur(1.58px);
                animation: 21s float infinite;
            }
            .particle:nth-child(80) {
                top: 40.8262454435%;
                left: 20.5278592375%;
                font-size: 23px;
                filter: blur(1.6px);
                animation: 33s floatReverse infinite;
            }

            @keyframes apparition {
                from {
                    opacity: 0;
                    transform: translateY(100px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }
            @keyframes float {
                0%, 100% {
                    transform: translateY(0);
                }
                50% {
                    transform: translateY(180px);
                }
            }
            @keyframes floatReverse {
                0%, 100% {
                    transform: translateY(0);
                }
                50% {
                    transform: translateY(-180px);
                }
            }
            @keyframes float2 {
                0%, 100% {
                    transform: translateY(0);
                }
                50% {
                    transform: translateY(28px);
                }
            }
            @keyframes floatReverse2 {
                0%, 100% {
                    transform: translateY(0);
                }
                50% {
                    transform: translateY(-28px);
                }
            }
        </style>
    </head>
    <body>
        <main class='container'>
            <c:forEach begin="1" end="20">
                <span class='particle'>4</span>
            </c:forEach>
            <c:forEach begin="1" end="20">
                <span class='particle'>0</span>
            </c:forEach>
            <c:forEach begin="1" end="20">
                <span class='particle'>5</span>
            </c:forEach>
            <article class='content'>
                <p>You got lost in the <strong>${requestScope['jakarta.servlet.error.status_code']}</strong> galaxy.</p>
                <p style="font-size: 1.1rem;">${requestScope['jakarta.servlet.error.message']}</p>
                <a href="<c:url value="/"/>">
                    <button>Go back to home.</button>
                </a>
            </article>
        </main>

    </body>
</html>
