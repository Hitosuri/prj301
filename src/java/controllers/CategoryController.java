package controllers;

import dal.Category;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import models.CategoryDAO;
import models.ProductDAO;

public class CategoryController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            Category category = new CategoryDAO().getCategoryById(id);
            if (category == null) {
                throw new ServletException();
            }
            
            ProductDAO productDAO = new ProductDAO();
            productDAO.setItemList(productDAO.getProductsByCategoryId(id));
            
            req.setAttribute("productDAO", productDAO);
            req.setAttribute("category", category);
            req.getRequestDispatcher("/category.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath());
        }
    }
    
}
