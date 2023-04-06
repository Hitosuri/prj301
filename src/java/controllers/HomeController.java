package controllers;

import dal.Product;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import models.ProductDAO;

public class HomeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ProductDAO productDAO = new ProductDAO();
            ArrayList<Product> hotProduct = productDAO.getHotProducts();
            ArrayList<Product> bestSaleProduct = productDAO.getBestSaleProducts();
            ArrayList<Product> newProduct = productDAO.getNewProducts();
        
            req.setAttribute("hotProduct", hotProduct);
            req.setAttribute("bestSaleProduct", bestSaleProduct);
            req.setAttribute("newProduct", newProduct);
            req.getRequestDispatcher("home.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
