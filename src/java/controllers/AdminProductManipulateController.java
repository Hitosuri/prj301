package controllers;

import dal.Category;
import dal.OrderDetail;
import dal.Product;
import helpers.FormValidator;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import models.CategoryDAO;
import models.OrderDetailDAO;
import models.ProductDAO;

public class AdminProductManipulateController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String actionPath = req.getServletPath();

            switch (actionPath) {
                case "/admin/createproduct":
                    createProduct(req, resp);
                    break;
                case "/admin/editproduct":
                    editProduct(req, resp);
                    break;
                case "/admin/deleteproduct":
                    deleteProduct(req, resp);
                    break;
                case "/admin/deleteproduct-confirm":
                    confirmDelete(req, resp);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String actionPath = req.getServletPath();

            FormValidator formValidator = new FormValidator(req);
            formValidator.setCheckParam("Product name", true, String.class);
            formValidator.setCheckParam("Unit price", false, Double.class, t -> (Double) t >= 0, "Unit price must be larger than 0");
            formValidator.setCheckParam("Quantity per unit", false, String.class);
            formValidator.setCheckParam("Units in stock", true, Integer.class, t -> (Integer) t >= 0, "Units in stock must be larger than 0");
            formValidator.setCheckParam("Category", true, Integer.class);
            formValidator.setCheckParam("Reorder level", false, Integer.class);
            formValidator.setCheckParam("Discontinued", true, Boolean.class);

            switch (actionPath) {
                case "/admin/createproduct":
                    applyCreateProduct(req, resp, formValidator);
                    break;
                case "/admin/editproduct":
                    applyEditProduct(req, resp, formValidator);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void applyEditProduct(HttpServletRequest req, HttpServletResponse resp, FormValidator formValidator) throws Exception {
        boolean validForm = formValidator.isValid();
        
        int productId = Integer.parseInt(req.getParameter("productId"));
        ProductDAO productDAO = new ProductDAO();
        Product currentProduct = productDAO.getProductById(productId);

        if (validForm) {
            try {
                int currentUnitOnOrder = currentProduct.getUnitsOnOrder();
                Product product = new Product(
                        productId,
                        (String) formValidator.get("Product name"),
                        (Integer) formValidator.get("Category"),
                        (String) formValidator.get("Quantity per unit"),
                        (Double) formValidator.get("Unit price"),
                        (Integer) formValidator.get("Units in stock"),
                        currentUnitOnOrder,
                        (Integer) formValidator.get("Reorder level"),
                        (Boolean) formValidator.get("Discontinued")
                );
                System.out.println(product);
                productDAO.updateProduct(product);
                resp.sendRedirect(req.getContextPath() + "/admin/products");
            } catch (Exception e) {
                e.printStackTrace();
                validForm = false;
            }
        }
        if (!validForm) {
            ArrayList<Category> categories = new CategoryDAO().getCategories();
            req.setAttribute("categories", categories);
            req.setAttribute("product", currentProduct);
            req.getRequestDispatcher("/admin/edit-product.jsp").forward(req, resp);
        }
    }

    private void createProduct(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ArrayList<Category> categories = new CategoryDAO().getCategories();
        req.setAttribute("categories", categories);
        req.getRequestDispatcher("/admin/create-product.jsp").forward(req, resp);
    }

    private void editProduct(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int productId = Integer.parseInt(req.getParameter("id"));
        Product product = new ProductDAO().getProductById(productId);
        ArrayList<Category> categories = new CategoryDAO().getCategories();
        req.setAttribute("categories", categories);
        req.setAttribute("product", product);
        req.getRequestDispatcher("/admin/edit-product.jsp").forward(req, resp);
    }

    private void applyCreateProduct(HttpServletRequest req, HttpServletResponse resp, FormValidator formValidator) throws Exception {
        boolean validForm = formValidator.isValid();

        if (validForm) {
            try {
                Product product = new Product(
                        0,
                        (String) formValidator.get("Product name"),
                        (Integer) formValidator.get("Category"),
                        (String) formValidator.get("Quantity per unit"),
                        (Double) formValidator.get("Unit price"),
                        (Integer) formValidator.get("Units in stock"),
                        0,
                        (Integer) formValidator.get("Reorder level"),
                        (Boolean) formValidator.get("Discontinued")
                );
                new ProductDAO().insertProduct(product);
                resp.sendRedirect(req.getContextPath() + "/admin/products");
            } catch (Exception e) {
                e.printStackTrace();
                validForm = false;
            }
        }
        if (!validForm) {
            for (String paramName : formValidator.getParamNameSet()) {
                req.setAttribute(paramName, formValidator.getRaw(paramName));
            }
            ArrayList<Category> categories = new CategoryDAO().getCategories();
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/admin/create-product.jsp").forward(req, resp);
        }
    }

    private void deleteProduct(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int productId = Integer.parseInt(req.getParameter("id"));
        Product product = new ProductDAO().getProductById(productId);
        req.setAttribute("product", product);
        req.getRequestDispatcher("/admin/delete-product.jsp").forward(req, resp);
    }

    private void confirmDelete(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int productId = Integer.parseInt(req.getParameter("id"));
        ArrayList<OrderDetail> orderDetailList = new OrderDetailDAO().geOrderDetailsByPID(productId);
        System.out.println(orderDetailList);
        if (orderDetailList.size() > 0) {
            String msg = "This product is in " + orderDetailList.size() + (orderDetailList.size() == 1 ? " order" : " orders") + ", cannot delete!";
            req.setAttribute("cannotDelete", true);
            req.setAttribute("msg", msg);
            req.getRequestDispatcher("/admin/delete-product.jsp").forward(req, resp);
        } else {
            int result = new ProductDAO().deleteProduct(productId);
            resp.sendRedirect(req.getContextPath() + "/admin/products");
        }
    }

}
