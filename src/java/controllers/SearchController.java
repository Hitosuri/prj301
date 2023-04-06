package controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.ProductDAO;

public class SearchController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String keyword = req.getParameter("keyword");
            int cid = Integer.parseInt(req.getParameter("cid"));
            
            ProductDAO productDAO = new ProductDAO();
            if (cid == 0) {
                productDAO.setItemList(productDAO.searchProductsByName(keyword));
            } else {
                productDAO.setItemList(productDAO.searchProductsByNameAndCategoryId(keyword, cid));
            }
            
            req.setAttribute("productDAO", productDAO);
            req.setAttribute("keyword", keyword);
            req.setAttribute("categoryID", cid);
            req.getRequestDispatcher("search.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
