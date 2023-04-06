package controllers;

import dal.Order;
import dal.OrderDetail;
import dal.Product;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.ArrayList;
import models.OrderDAO;
import models.OrderDetailDAO;
import models.ProductDAO;

public class AdminOrdersController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String actionPath = req.getServletPath();

            switch (actionPath) {
                case "/admin/orders":
                    showOrders(req, resp);
                    break;
                case "/admin/order-detail":
                    showOrderDetail(req, resp);
                    break;
                case "/admin/orders/cancelorder":
                    cancelOrder(req, resp);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/admin/orders");
        }
    }
    
    private void cancelOrder(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int orderId = Integer.parseInt(req.getParameter("id"));
        int result = new OrderDAO().cancelOrder(orderId);
        ArrayList<OrderDetail> orderDetails = new OrderDetailDAO().geOrderDetailsByOID(orderId);
        ProductDAO productDAO = new ProductDAO();
        for (OrderDetail orderDetail : orderDetails) {
            Product product = productDAO.getProductById(orderDetail.getProductID());
            product.setUnitsInStock(product.getUnitsInStock() + orderDetail.getQuantity());
            product.setUnitsOnOrder(product.getUnitsOnOrder() - 1);
            productDAO.updateProduct(product);
        }
        resp.sendRedirect(req.getContextPath() + "/admin/orders");
    }

    private void showOrders(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String txtStartOrderDate = req.getParameter("txtStartOrderDate");
        String txtEndOrderDate = req.getParameter("txtEndOrderDate");
        OrderDAO orderDAO = new OrderDAO();
        ArrayList<Order> orders = null;
        if (txtStartOrderDate != null && txtEndOrderDate != null && !txtStartOrderDate.trim().isEmpty() && !txtEndOrderDate.trim().isEmpty()) {
            Date date1 = Date.valueOf(txtStartOrderDate);
            Date date2 = Date.valueOf(txtEndOrderDate);
            if (date1.compareTo(date2) > 0) {
                orders = orderDAO.getOrdersInDateRange(date2, date1);
            }
            orders = orderDAO.getOrdersInDateRange(date1, date2);
            req.setAttribute("txtStartOrderDate", txtStartOrderDate);
            req.setAttribute("txtEndOrderDate", txtEndOrderDate);
        } else {
            orders = orderDAO.getOrders();
        }
        orders.sort((a, b) -> b.getOrderID() - a.getOrderID());
        orderDAO.setItemList(orders);
        orderDAO.setMaxPageItem(5);
        orderDAO.setMaxTotalPage(10);

        req.setAttribute("orderDAO", orderDAO);
        req.getRequestDispatcher("/admin/orders.jsp").forward(req, resp);
    }

    private void showOrderDetail(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int id = Integer.parseInt(req.getParameter("id"));
        Order order = new OrderDAO().getOrderById(id);
        ArrayList<OrderDetail> orderDetails = new OrderDetailDAO().geOrderDetailsByOID(id);
        req.setAttribute("order", order);
        req.setAttribute("orderDetails", orderDetails);
        req.getRequestDispatcher("/admin/order-detail.jsp").forward(req, resp);
    }

}
