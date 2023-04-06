package models;

import dal.Product;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProductDAO extends AbstractDAO<Product> {
    
    public int deleteProduct(int id) throws Exception {
        String sql = "DELETE FROM Products WHERE ProductID = ?";
        return update(sql, id);
    }
    
    public void insertProduct(Product product) throws Exception {
        String sql = "INSERT INTO Products VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        int generatedId = insert(
                sql,
                product.getProductName(),
                product.getCategoryID(),
                product.getQuantityPerUnit(),
                product.getUnitPrice(),
                product.getUnitsInStock(),
                product.getUnitsOnOrder(),
                product.getReorderLevel(),
                product.isDiscontinued()
        );
        product.setProductID(generatedId);
    }

    public ArrayList<Product> getProducts() throws Exception {
        String sql = "SELECT * FROM Products ORDER BY ProductID DESC";
        return selectMany(sql);
    }

    public int updateProduct(Product product) throws Exception {
        String sql = "UPDATE Products SET ProductName = ?, CategoryID = ?, QuantityPerUnit = ?, UnitPrice = ?, UnitsInStock = ?, UnitsOnOrder = ?, ReorderLevel = ?, Discontinued = ? WHERE ProductID = ?";
        return update(
                sql,
                product.getProductName(),
                product.getCategoryID(),
                product.getQuantityPerUnit(),
                product.getUnitPrice(),
                product.getUnitsInStock(),
                product.getUnitsOnOrder(),
                product.getReorderLevel(),
                product.isDiscontinued(),
                product.getProductID()
        );
    }

    public ArrayList<Product> getHotProducts() throws Exception {
        String sql = "SELECT TOP 4 Products.* FROM Products INNER JOIN ( SELECT ProductID, ROW_NUMBER() OVER( ORDER BY MAX(Discount) ASC) AS DiscountRank FROM [ORDER Details] GROUP BY ProductID) AS Ranker ON Ranker.ProductID = Products.ProductID WHERE UnitsInStock > 0 ORDER BY DiscountRank DESC";
        return selectMany(sql);
    }

    public ArrayList<Product> getBestSaleProducts() throws Exception {
        String sql = "SELECT TOP 4 Products.* FROM Products INNER JOIN ( SELECT ProductID, ROW_NUMBER() OVER( ORDER BY COUNT(*) ASC) AS TotalRank FROM [ORDER Details] GROUP BY ProductID) AS Ranker ON Ranker.ProductID = Products.ProductID WHERE UnitsInStock > 0 ORDER BY TotalRank DESC";
        return selectMany(sql);
    }

    public ArrayList<Product> getNewProducts() throws Exception {
        String sql = "SELECT TOP 4 * FROM Products WHERE UnitsInStock > 0 ORDER BY ProductID DESC";
        return selectMany(sql);
    }

    public ArrayList<Product> getProductsByCategoryId(int id) throws Exception {
        String sql = "select * from Products where CategoryID = ? ORDER BY ProductID DESC";
        return selectMany(sql, id);
    }

    public ArrayList<Product> searchProductsByName(String keyword) throws Exception {
        String sql = "SELECT * from Products where productName LIKE ? COLLATE SQL_Latin1_General_CP1_CS_AS";
        return selectMany(sql, "%" + keyword + "%");
    }

    public ArrayList<Product> searchProductsByNameAndCategoryId(String keyword, int categoryID) throws Exception {
        String sql = "select * from Products where productName like ? COLLATE SQL_Latin1_General_CP1_CS_AS and CategoryID = ? ORDER BY ProductID DESC";
        return selectMany(sql, "%" + keyword + "%", categoryID);
    }

    public Product getProductById(int id) throws Exception {
        String sql = "SELECT * FROM Products WHERE ProductID = ?";
        return selectOne(sql, id);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(new ProductDAO().searchProductsByNameAndCategoryId("h", 2).size());
    }

    @Override
    protected Product propMapping(ResultSet rs) throws Exception {
        return new Product(
                rs.getInt("ProductID"),
                rs.getString("ProductName"),
                rs.getInt("CategoryID"),
                rs.getString("QuantityPerUnit"),
                rs.getDouble("UnitPrice"),
                rs.getInt("UnitsInStock"),
                rs.getInt("UnitsOnOrder"),
                rs.getInt("ReorderLevel"),
                rs.getBoolean("Discontinued")
        );
    }
}
