<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:import url="/template/admin-header.jsp"/>
<div id="content">
    <c:import url="/template/admin-left.jsp"/>
    <div id="content-right">
        <div class="path-admin">DASHBOARD</b></div>
        <div class="content-main">
            <div id="content-main-dashboard">
                <div id="dashboard-1">
                    <div id="dashboard-1-container">
                        <div class="dashboard-item">
                            <div class="dashboard-item-title">Weekly Sales</div>
                            <div class="dashboard-item-content">$<fmt:formatNumber value="${weeklySale}" maxFractionDigits="0"/></div>
                        </div>
                        <div class="dashboard-item">
                            <div class="dashboard-item-title">Total Orders</div>
                            <div class="dashboard-item-content">$<fmt:formatNumber value="${revenue}" maxFractionDigits="0"/></div>
                        </div>
                        <div class="dashboard-item">
                            <div class="dashboard-item-title">Total Customers</div>
                            <div class="dashboard-item-content">${totalCustomer}</div>
                        </div>
                        <div class="dashboard-item">
                            <div class="dashboard-item-title">Total Guest</div>
                            <div class="dashboard-item-content">${totalGuest}</div>
                        </div>
                    </div>
                </div>
                <div id="dashboard-2">
                    <div id="chart" style="text-align: center;">
                        <div id="chart1">
                            <table>
                                <tr>
                                <form method="POST">
                                    <td>In many year:</td>
                                    <td>
                                        From:
                                        <select name="fromyear">
                                            <c:forEach begin="${firstOrderYear}" end="${lastOrderYear}" var="year">
                                                <option value="${year}" <c:if test="${year == param.fromyear}">selected</c:if>>${year}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td>
                                        To:
                                        <select name="toyear">
                                            <c:forEach begin="${firstOrderYear}" end="${lastOrderYear}" var="year">
                                                <option value="${year}" <c:if test="${year == (empty param.toyear ? lastOrderYear : param.toyear)}">selected</c:if>>${year}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td>
                                        <input type="submit" value="Show">
                                    </td>
                                </form>
                                </tr>
                                <tr>
                                <form method="POST">
                                    <td>In one year:</td>
                                    <td colspan="2">
                                        Year:
                                        <select name="atyear">
                                            <c:forEach begin="${firstOrderYear}" end="${lastOrderYear}" var="year">
                                                <option value="${year}" <c:if test="${year == (empty param.atyear ? lastOrderYear : param.atyear)}">selected</c:if>>${year}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td>
                                        <input type="submit" value="Show">
                                    </td>
                                </form>
                                </tr>
                            </table>
                            <h3>Statistic Orders (Month)</h3>
                            <canvas id="myChart1" style="width: 100%;"></canvas>
                        </div>
                        <div id="chart2">
                            <canvas id="myChart2" style="width: 80%;"></canvas>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<c:import url="/template/admin-footer.jsp"/>
<script>
    function OrdersChart() {
    <c:if test="${empty manyYearStat}">
        var xValues = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,];
    </c:if>
    <c:if test="${not empty manyYearStat}">
        var xValues = [<c:forEach begin="${param.fromyear}" end="${param.toyear}" var="year">${year},</c:forEach>]
    </c:if>

        new Chart("myChart1", {
            type: "line",
            data: {
                labels: xValues,
                datasets: [{
                        data: ${atyearStat}${manyYearStat},
                        borderColor: "sienna",
                        fill: true
                    }]
            },
            options: {
                legend: {display: false}
            }
        });
    }

    function CustomersChart() {
        var xValues = ["Total", "New customer"];
        var yValues = [${totalCustomer}, ${newCustomers}, ${totalCustomer * 1.5}];
        var barColors = ["green", "red"];

        new Chart("myChart2", {
            type: "bar",
            data: {
                labels: xValues,
                datasets: [{
                        backgroundColor: barColors,
                        data: yValues
                    }]
            },
            options: {
                legend: {display: false},
                title: {
                    display: true,
                    text: "New Customers (30 daily Avg)"
                }
            }
        });
    }

    OrdersChart();
    CustomersChart();
</script>