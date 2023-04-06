package controllers;

import dal.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import models.AccountDAO;

public class SigninController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getServletPath().equals("/account/signout")) {
            req.getSession().removeAttribute("accSession");
            resp.sendRedirect(req.getContextPath() + "/account/signin");
        } else {
            req.getRequestDispatcher("/signin.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String email = req.getParameter("email");
            String pass = req.getParameter("pass");
            Account account = new AccountDAO().getAccount(email, pass);
            if (account != null) {
                HttpSession session = req.getSession();
                session.setAttribute("accSession", account);
                resp.sendRedirect(req.getContextPath() + (account.getRole() == 1 ? "/admin/dashboard" : ""));
            } else {
                req.setAttribute("errMsg", "Email or Password was incorrect");
                req.getRequestDispatcher("/signin.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
