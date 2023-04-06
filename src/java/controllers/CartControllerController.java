package controllers;

import dal.Account;
import dal.Customer;
import dal.Order;
import dal.OrderDetail;
import dal.Product;
import helpers.FormValidator;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import models.AccountDAO;
import models.CustomerDAO;
import models.OrderDAO;
import models.OrderDetailDAO;
import models.ProductDAO;

public class CartControllerController extends HttpServlet {

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

            Map<Integer, Integer> cart = (Map) req.getSession().getAttribute("cart");
            if (cart == null) {
                cart = cookieGet(req);
            }
            req.getSession().setAttribute("cart", cart);

            switch (actionPath) {
                case "/cart":
                    showCart(req, resp, cart);
                    break;
                case "/cart/addtocart":
                    addToCart(req, resp, cart);
                    resp.sendRedirect(req.getContextPath() + "/product?id=" + Integer.parseInt(req.getParameter("id")));
                    break;
                case "/cart/buynow":
                    addToCart(req, resp, cart);
                    resp.sendRedirect(req.getContextPath() + "/cart");
                    break;
                case "/cart/remove":
                    remove(req, resp, cart);
                    break;
                case "/cart/increase":
                    increase(req, resp, cart);
                    break;
                case "/cart/decrease":
                    decrease(req, resp, cart);
                    break;
                case "/cart/submitorder":
                    submitOrder(req, resp, cart);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath());
        }
    }

    private void submitOrder(HttpServletRequest req, HttpServletResponse resp, Map<Integer, Integer> cart) throws ServletException, IOException, SQLException, Exception {
        Account account = (Account) req.getSession().getAttribute("accSession");
        FormValidator formValidator = new FormValidator(req);
        boolean signined = account != null;
        
        formValidator.setCheckParam("Require time", true, Date.class, t -> ((Date) t).getTime() >= System.currentTimeMillis(), "Require time must be after orders time");
        if (!(signined && account.getCustomerID() != null)) {
            formValidator.setCheckParam("Company name", true, String.class);
            formValidator.setCheckParam("Contact name", true, String.class);
            formValidator.setCheckParam("Contact title", true, String.class);
            formValidator.setCheckParam("Address", true, String.class);
        }
        boolean validForm = formValidator.isValid();

        if (validForm) {
            int result = 0;
            Customer customer = null;
            if (signined && account.getCustomerID() != null) {
                customer = new CustomerDAO().getCustomerById(account.getCustomerID());
            } else {
                customer = new Customer("", (String) formValidator.get("Company name"), (String) formValidator.get("Contact name"), (String) formValidator.get("Contact title"), (String) formValidator.get("Address"), null);
                result = new CustomerDAO().insertCustomer(customer);
                if (signined) {
                    account.setCustomerID(customer.getCustomerID());
                    result = new AccountDAO().updateAccount(account);
                }
            }

            Order order = new Order(0, customer.getCustomerID(), 0, new Date(System.currentTimeMillis()), (Date) formValidator.get("Require time"), null, 0, null, null, null, null, null, null);
            new OrderDAO().insertOrder(order);
            ArrayList<Integer> idList = new ArrayList<>(cart.keySet());

            OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
            ProductDAO productDAO = new ProductDAO();
            for (Integer id : idList) {
                Product product = productDAO.getProductById(id);
                product.setUnitsInStock(product.getUnitsInStock() - cart.get(id));
                product.setUnitsOnOrder(product.getUnitsOnOrder() + 1);
                result = productDAO.updateProduct(product);
                OrderDetail orderDetail = new OrderDetail(order.getOrderID(), product.getProductID(), product.getUnitPrice(), cart.get(id), 0);
                result = orderDetailDAO.insertOrderDetail(orderDetail);
            }
            req.getSession().removeAttribute("cart");
            cookieClear(req, resp);
            if (signined) {
                resp.sendRedirect(req.getContextPath() + "/account/orders");
            } else {
                cart.clear();
                req.setAttribute("orderMsg", "Orders successfully!");
                showCart(req, resp, cart);
            }
        }
        if (!validForm) {
            for (String paramName : formValidator.getParamNameSet()) {
                req.setAttribute(paramName, formValidator.getRaw(paramName));
            }
            showCart(req, resp, cart);
        }
    }

    private void showCart(HttpServletRequest req, HttpServletResponse resp, Map<Integer, Integer> cart) throws ServletException, IOException, Exception {
        Map<Product, Integer> realCart = new LinkedHashMap<>();
        ArrayList<Integer> idList = new ArrayList<>(cart.keySet());

        ProductDAO productDAO = new ProductDAO();
        int totalPrice = 0;
        for (int i = idList.size() - 1; i >= 0; i--) {
            Product product = productDAO.getProductById(idList.get(i));
            int currentQuantity = cart.get(idList.get(i));
            realCart.put(product, currentQuantity);
            totalPrice += product.getUnitPrice() * currentQuantity;
        }
        req.setAttribute("cart", realCart);
        req.setAttribute("totalPrice", totalPrice);

        Account account = (Account) req.getSession().getAttribute("accSession");
        if (account != null && account.getCustomerID() != null) {
            Customer customer = new CustomerDAO().getCustomerById(account.getCustomerID());
            req.setAttribute("customer", customer);
        }
        req.getRequestDispatcher("/cart.jsp").forward(req, resp);
    }

    private void decrease(HttpServletRequest req, HttpServletResponse resp, Map<Integer, Integer> cart) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        int productQuantity = cart.get(id);
        if (--productQuantity <= 0) {
            resp.sendRedirect(req.getContextPath() + "/cart/remove?id=" + id);
        } else {
            cart.put(id, productQuantity);
            cookieCreate(cart, req, resp);
            resp.sendRedirect(req.getContextPath() + "/cart");
        }
    }

    private void increase(HttpServletRequest req, HttpServletResponse resp, Map<Integer, Integer> cart) throws IOException, Exception {
        int id = Integer.parseInt(req.getParameter("id"));
        int unitInStock = new ProductDAO().getProductById(id).getUnitsInStock();
        int productQuantity = cart.get(id);
        if (++productQuantity <= unitInStock) {
            cart.put(id, productQuantity);
            cookieCreate(cart, req, resp);
        }
        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    private void remove(HttpServletRequest req, HttpServletResponse resp, Map<Integer, Integer> cart) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        cart.remove(id);
        if (cart.size() > 0) {
            cookieCreate(cart, req, resp);
        } else {
            cookieClear(req, resp);
        }
        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    private void addToCart(HttpServletRequest req, HttpServletResponse resp, Map<Integer, Integer> cart) throws ServletException, Exception {
        int id = Integer.parseInt(req.getParameter("id"));
        if (new ProductDAO().getProductById(id) == null) {
            throw new ServletException();
        }
        Integer quantityInCart = cart.get(id);
        if (quantityInCart == null) {
            quantityInCart = 0;
        }
        cart.put(id, ++quantityInCart);
        cookieCreate(cart, req, resp);
    }

    private void cookieCreate(Map<Integer, Integer> cart, HttpServletRequest req, HttpServletResponse resp) {
        StringBuilder str = new StringBuilder();
        Set<Integer> keySet = cart.keySet();
        for (Integer i : keySet) {
            str.append(i + "o" + cart.get(i) + "-");
        }
        if (str.length() > 0) {
            str.deleteCharAt(str.length() - 1);
        }
        Cookie[] cookies = new Cookie[2];
        cookies[0] = new Cookie("cart", str.toString());
        cookies[1] = new Cookie("total", String.valueOf(cart.size()));
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(60 * 60 * 24 * 7);
            cookie.setHttpOnly(true);
            cookie.setPath(req.getContextPath());
            resp.addCookie(cookie);
        }
    }

    private Map<Integer, Integer> cookieGet(HttpServletRequest req) {
        Map<Integer, Integer> cart = new LinkedHashMap<>();
        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            return cart;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equalsIgnoreCase("cart")) {
                if (cookie.getValue().trim().isEmpty()) {
                    return cart;
                }
                String[] sp = cookie.getValue().split("-");
                for (String p : sp) {
                    String[] pair = p.split("o");
                    cart.put(Integer.parseInt(pair[0]), Integer.parseInt(pair[1]));
                }
                break;
            }
        }
        return cart;
    }

    private void cookieClear(HttpServletRequest req, HttpServletResponse resp) {
        Cookie[] cookies = {new Cookie("cart", null), new Cookie("total", null)};
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            cookie.setHttpOnly(true);
            cookie.setPath(req.getContextPath());
            resp.addCookie(cookie);
        }
    }

}
