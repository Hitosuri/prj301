package controllers;

import dal.Account;
import dal.Order;
import dal.OrderDetail;
import dal.Product;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import models.OrderDAO;
import models.OrderDetailDAO;
import models.ProductDAO;

public class CustomerOrdersController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String actionPath = req.getServletPath();

            Account account = (Account) req.getSession().getAttribute("accSession");
            if (account == null) {
                throw new ServletException();
            }

            switch (actionPath) {
                case "/account/orders":
                    showOrders(req, resp, new OrderDAO().getNonCancelOrdersByCID(account.getCustomerID()));
                    break;
                case "/account/orders/cancel":
                    req.setAttribute("type", "Cancel Orders");
                    showOrders(req, resp, new OrderDAO().getCanceledOrdersByCID(account.getCustomerID()));
                    break;
                case "/account/orders/cancelorder":
                    cancelOrder(req, resp);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath());
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
        resp.sendRedirect(req.getContextPath() + "/account/orders");
    }

    private void showOrders(HttpServletRequest req, HttpServletResponse resp, ArrayList<Order> orders) throws ServletException, IOException, Exception {
        orders.sort((a, b) -> {
            int compare = b.getOrderDate().compareTo(a.getOrderDate());
            if (compare != 0) {
                return compare;
            }
            return b.getOrderID() - a.getOrderID();
        });
        Map<Integer, ArrayList<OrderDetail>> ordersMap = new HashMap<>();
        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        for (Order order : orders) {
            ArrayList<OrderDetail> orderDetails = orderDetailDAO.geOrderDetailsByOID(order.getOrderID());
            ordersMap.put(order.getOrderID(), orderDetails);
        }
        req.setAttribute("orders", orders);
        req.setAttribute("ordersMap", ordersMap);
        req.getRequestDispatcher("/customer-orders.jsp").forward(req, resp);
    }

}
