package controllers;

import dal.Category;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import models.CategoryDAO;
import models.ProductDAO;

public class AdminProductsController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String keyword = req.getParameter("keyword");
            if (keyword == null) {
                keyword = "";
            }
            int cid = 0;
            try {
                cid = Integer.parseInt(req.getParameter("cid"));
            } catch (Exception e) {
            }

            ProductDAO productDAO = new ProductDAO();
            if (cid == 0) {
                productDAO.setItemList(productDAO.searchProductsByName(keyword));
            } else {
                productDAO.setItemList(productDAO.searchProductsByNameAndCategoryId(keyword, cid));
                req.setAttribute("categoryID", cid);
            }
            productDAO.setMaxPageItem(5);
            productDAO.setMaxTotalPage(10);

            ArrayList<Category> categories = new CategoryDAO().getCategories();

            req.setAttribute("productDAO", productDAO);
            req.setAttribute("categories", categories);
            req.setAttribute("keyword", keyword);
            req.getRequestDispatcher("/admin/products.jsp").forward(req, resp);
        } catch (Exception e) {
        }
    }

}
