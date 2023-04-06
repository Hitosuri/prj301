package controllers;

import dal.Category;
import dal.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import models.CategoryDAO;
import models.ProductDAO;

public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            Product product = new ProductDAO().getProductById(id);
            if (product == null) {
                throw new ServletException();
            }
            Category category = new CategoryDAO().getCategoryById(product.getCategoryID());
            
            req.setAttribute("product", product);
            req.setAttribute("category", category);
            req.getRequestDispatcher("/detail.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath());
        }
    }
    
}
