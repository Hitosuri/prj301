package controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Calendar;
import models.AccountDAO;
import models.CustomerDAO;
import models.OrderDAO;
import models.OrderDetailDAO;

public class AdminDashboardController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            CustomerDAO customerDAO = new CustomerDAO();
            OrderDAO orderDAO = new OrderDAO();
            OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
            int totalOrders = orderDAO.getOrders().size();
            int totalCustomer = customerDAO.getCustomers().size();
            int hasAccountCustomer = new AccountDAO().getCustomerAccountOnly().size();
            int newCustomers = customerDAO.getNewCustomers().size();
            double revenue = orderDetailDAO.getTotalRevenue();
            double weeklySale = orderDetailDAO.getWeeklySale();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(orderDAO.getFirstOrder().getOrderDate());
            req.setAttribute("firstOrderYear", calendar.get(Calendar.YEAR));
            calendar.setTime(orderDAO.getLastOrder().getOrderDate());
            req.setAttribute("lastOrderYear", calendar.get(Calendar.YEAR));
            if (req.getAttribute("atyearStat") == null && req.getAttribute("manyYearStat") == null) {
                req.setAttribute("atyearStat", new OrderDAO().getOneYearStat(Calendar.getInstance().get(Calendar.YEAR)));
            }

            req.setAttribute("totalOrders", totalOrders);
            req.setAttribute("totalCustomer", totalCustomer);
            req.setAttribute("totalGuest", totalCustomer - hasAccountCustomer);
            req.setAttribute("newCustomers", newCustomers);
            req.setAttribute("revenue", revenue);
            req.setAttribute("weeklySale", weeklySale);
            req.getRequestDispatcher("/admin/dashboard.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String atyearStr = req.getParameter("atyear");
            String fromyearStr = req.getParameter("fromyear");
            String toyearStr = req.getParameter("toyear");
            if (atyearStr != null) {
                req.setAttribute("atyearStat", new OrderDAO().getOneYearStat(Integer.parseInt(atyearStr)));
            } else {
                req.setAttribute("manyYearStat", new OrderDAO().getManyYearStat(Integer.parseInt(fromyearStr), Integer.parseInt(toyearStr)));
            }
            doGet(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
