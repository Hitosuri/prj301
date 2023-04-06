package controllers;

import dal.Account;
import dal.Customer;
import helpers.FormValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import models.AccountDAO;
import models.CustomerDAO;

public class ProfileController extends HttpServlet {

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

            HttpSession session = req.getSession();
            Account account = (Account) session.getAttribute("accSession");
            if (account == null) {
                throw new ServletException();
            }
            Customer customer = new CustomerDAO().getCustomerById(account.getCustomerID());
            if (customer == null) {
                throw new ServletException();
            }

            switch (actionPath) {
                case "/account/profile":
                    showInfo(customer, req, resp);
                    break;
                case "/account/profile/edit":
                    editProfile(customer, req, resp, account);
                    break;
                case "/account/profile/applychange":
                    applyChange(req, resp, account, customer);
                    break;
                default:
                    resp.sendRedirect(req.getContextPath() + "/account/profile");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath());
        }
    }

    private void showInfo(Customer customer, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("customer", customer);
        req.getRequestDispatcher("/profile.jsp").forward(req, resp);
    }

    private void editProfile(Customer customer, HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        req.setAttribute("customer", customer);
        req.setAttribute("account", account);
        req.getRequestDispatcher("/edit-profile.jsp").forward(req, resp);
    }

    private void applyChange(HttpServletRequest req, HttpServletResponse resp, Account account, Customer customer) throws ServletException, IOException, Exception {
        AccountDAO accountDAO = new AccountDAO();
        FormValidator formValidator = new FormValidator(req);
        formValidator.setCheckParam("Company name", true, String.class);
        formValidator.setCheckParam("Contact name", true, String.class);
        formValidator.setCheckParam("Contact title", true, String.class);
        formValidator.setCheckParam("Address", true, String.class);
        formValidator.setCheckParam("Email", true, String.class, a -> ((String) a).matches("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+"), "Email does not meet email structure");
        formValidator.addCheckFunction("Email", a -> {
            try {
                return account.getEmail().equalsIgnoreCase((String) formValidator.get("Email")) || accountDAO.getAccountByEmail((String) a) == null;
            } catch (Exception e) {
            }
            return false;
        }, "Account with email %Email% is existed");
        boolean validForm = formValidator.isValid();
        
        if (validForm) {
            try {
                customer.setCompanyName((String) formValidator.get("Company name"));
                customer.setContactName((String) formValidator.get("Contact name"));
                customer.setContactTitle((String) formValidator.get("Contact title"));
                customer.setAddress((String) formValidator.get("Address"));
                int result = new CustomerDAO().updateCustomer(customer);

                account.setEmail((String) formValidator.get("Email"));
                result = new AccountDAO().updateAccount(account);

                resp.sendRedirect(req.getContextPath() + "/account/profile");
            } catch (Exception e) {
                e.printStackTrace();
                validForm = false;
            }
        }
        if (!validForm) {
            for (String paramName : formValidator.getParamNameSet()) {
                req.setAttribute(paramName, formValidator.getRaw(paramName));
            }
            req.getRequestDispatcher("/edit-profile.jsp").forward(req, resp);
        }
    }

}
