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

public class SignupController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/signup.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            AccountDAO accountDAO = new AccountDAO();
            FormValidator formValidator = new FormValidator(req);
            formValidator.setCheckParam("Company name", true, String.class);
            formValidator.setCheckParam("Contact name", true, String.class);
            formValidator.setCheckParam("Contact title", true, String.class);
            formValidator.setCheckParam("Address", true, String.class);
            formValidator.setCheckParam("Email", true, String.class, a -> ((String) a).matches("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+"), "Email does not meet email structure");
            formValidator.addCheckFunction("Email", a -> {
                try {
                    return accountDAO.getAccountByEmail((String) a) == null;
                } catch (Exception e) {
                }
                return false;
            }, "Account with email %Email% is existed");
            formValidator.setCheckParam("Password", true, String.class);
            formValidator.setCheckParam("RePassword", true, String.class, a -> ((String) a).equals(formValidator.get("Password")), "Password and RePassword does not match");
            boolean validForm = formValidator.isValid();
            
//            HashMap<String, String> inputForm = new HashMap<>();
//            for (Map.Entry<String, String[]> param : req.getParameterMap().entrySet()) {
//                inputForm.put(param.getKey(), param.getValue()[0].trim());
//            }
//
//            boolean validForm = true;
//            AccountDAO accountDAO = new AccountDAO();
//
//            HashMap<String, String> errMessage = new HashMap<>();
//
//            Set<String> inputFormKeys = inputForm.keySet();
//            for (String inputFormKey : inputFormKeys) {
//                if (inputForm.get(inputFormKey).trim().isEmpty()) {
//                    validForm = false;
//                    errMessage.put(inputFormKey, inputFormKey + " is required");
//                }
//            }
//
//            if (validForm && !inputForm.get("Password").equals(inputForm.get("RePassword"))) {
//                validForm = false;
//                errMessage.put("RePassword", "Password and RePassword does not match");
//                inputForm.put("Password", "");
//                inputForm.put("RePassword", "");
//            }
//
//            if (!inputForm.get("Email").isEmpty() && !inputForm.get("Email").matches("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+")) {
//                validForm = false;
//                errMessage.put("Email", inputForm.get("Email") + " does not meet email structure");
//            }
//
//            if (validForm && accountDAO.getAccountByEmail(inputForm.get("Email")) != null) {
//                validForm = false;
//                errMessage.put("Email", "Account with email " + inputForm.get("Email") + " is existed");
//            }

//            if (validForm) {
//                try {
//                    Customer customer = new Customer("", inputForm.get("Company name"), inputForm.get("Contact name"), inputForm.get("Contact title"), inputForm.get("Address"), null);
//                    int result = new CustomerDAO().insertCustomer(customer);
//                    Account account = new Account(0, inputForm.get("Email"), inputForm.get("Password"), customer.getCustomerID(), 0, 0);
//                    accountDAO.insertAccount(account);
//                    resp.sendRedirect("signin");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    validForm = false;
//                }
//            }
//            if (!validForm) {
//                for (String inputFormKey : inputFormKeys) {
//                    req.setAttribute(inputFormKey, inputForm.get(inputFormKey));
//                }
//                Set<String> errMessageKeys = errMessage.keySet();
//                for (String errMessageKey : errMessageKeys) {
//                    req.setAttribute(errMessageKey + "Err", errMessage.get(errMessageKey));
//                }
//                req.getRequestDispatcher("/signup.jsp").forward(req, resp);
//            }

            if (validForm) {
                try {
                    Customer customer = new Customer(
                            "",
                            (String) formValidator.get("Company name"),
                            (String) formValidator.get("Contact name"),
                            (String) formValidator.get("Contact title"),
                            (String) formValidator.get("Address"),
                            null
                    );
                    int result = new CustomerDAO().insertCustomer(customer);
                    Account account = new Account(
                            0,
                            (String) formValidator.get("Email"),
                            (String) formValidator.get("Password"),
                            customer.getCustomerID(),
                            0,
                            2
                    );
                    accountDAO.insertAccount(account);
                    req.getSession().setAttribute("signupSuccess", true);
                    resp.sendRedirect("signin");
                } catch (Exception e) {
                    e.printStackTrace();
                    validForm = false;
                }
            }
            if (!validForm) {
                for (String paramName : formValidator.getParamNameSet()) {
                    req.setAttribute(paramName, formValidator.getRaw(paramName));
                }
                req.getRequestDispatcher("/signup.jsp").forward(req, resp);
            }
        } catch (Exception e) {
        }

    }

}
