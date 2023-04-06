package controllers;

import dal.Account;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Properties;
import models.AccountDAO;

public class ForgotController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("accSession") != null) {
            resp.sendRedirect(req.getContextPath());
            return;
        }
        req.getRequestDispatcher("/forgot.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String email = req.getParameter("email");
            Account account = new AccountDAO().getAccountByEmail(email);
            req.setAttribute("email", email);
            if (account == null) {
                req.setAttribute("clazz", "msg-error");
                req.setAttribute("msg", email + " didn't signuped!");
            } else {
                sendEmail(email, account, req);
                req.setAttribute("msg", "Password has been sent to " + email);
                req.setAttribute("clazz", "msg-success");
            }
            req.getRequestDispatcher("/forgot.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("clazz", "msg-error");
            req.setAttribute("msg", "Send email fail");
            req.getRequestDispatcher("/forgot.jsp").forward(req, resp);
        }

    }

    private void sendEmail(String to, Account account, HttpServletRequest req) {
        final String sender = getInitParameter("sender");
        final String password = getInitParameter("password");

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(
                prop,
                new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, password);
            }
        });

        try {
            String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/account/signin";
            String conntent = "<div class=\"email-content\" style=\"font-family:'Open Sans',Arial,sans-serif;background-color:beige;font-size:1.1rem;margin:0\"><p style=\"font-size:1.2rem\"><strong>This email was generated because someone requested to receive the forgotten password</strong></p><p>Your password is: <strong>" + account.getPassword() + "</strong></p><p>You can access <span style=\"font-family:monospace;font-size:1rem\">" + url + "</span> or click <a href=\"" + url + "\">HERE</a> to signin</p></div>";
            MimeMessage msg = new MimeMessage(session);
            msg.setSubject("Forgotten password request");
            msg.setText(conntent, "utf-8", "html");
            msg.setFrom(new InternetAddress(sender));
            msg.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(to)
            );
            Transport.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
