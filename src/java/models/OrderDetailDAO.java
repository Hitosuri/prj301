package models;

import dal.OrderDetail;
import java.sql.ResultSet;
import java.util.ArrayList;

public class OrderDetailDAO extends AbstractDAO<OrderDetail> {
    
    public ArrayList<OrderDetail> geOrderDetails() throws Exception {
        String sql = "SELECT * FROM [Order Details]";
        return selectMany(sql);
    }
    
    public ArrayList<OrderDetail> geOrderDetailsByPID(int pid) throws Exception {
        String sql = "SELECT * FROM [Order Details] WHERE ProductID = ?";
        return selectMany(sql, pid);
    }
    
    public ArrayList<OrderDetail> geOrderDetailsByOID(int oid) throws Exception {
        String sql = "SELECT * FROM [Order Details] WHERE OrderID = ?";
        return selectMany(sql, oid);
    }
    
    public int insertOrderDetail(OrderDetail orderDetail) throws Exception {
        String sql = "INSERT INTO [Order Details] VALUES (?, ?, ?, ?, ?)";
        return update(
                sql,
                orderDetail.getOrderID(),
                orderDetail.getProductID(),
                orderDetail.getUnitPrice(),
                orderDetail.getQuantity(),
                orderDetail.getDiscount()
        );
    }
    
    public double getTotalRevenue() throws Exception {
        String sql = "SELECT SUM((1 - Discount) * UnitPrice * Quantity) AS Price FROM Orders A LEFT JOIN [Order Details] B ON A.OrderID = B.OrderID WHERE A.RequiredDate IS NOT NULL";
        return selectScalar(sql, Double.class);
    }
    
    public double getWeeklySale() throws Exception {
        String sql = "SELECT SUM((1 - Discount) * UnitPrice * Quantity) AS Price FROM [Order Details] M RIGHT JOIN (SELECT OrderID FROM Orders WHERE OrderDate > DATEADD(DAY, -7, GETDATE()) AND RequiredDate IS NOT NULL) T ON T.OrderID = M.OrderID";
        return selectScalar(sql, Double.class);
    }
    
    public static void main(String[] args) throws Exception {
        System.out.println(new OrderDetailDAO().getWeeklySale());
    }

    @Override
    protected OrderDetail propMapping(ResultSet rs) throws Exception {
        return new OrderDetail(
                rs.getInt("OrderID"),
                rs.getInt("ProductID"),
                rs.getDouble("UnitPrice"),
                rs.getInt("Quantity"),
                rs.getDouble("Discount")
        );
    }
}
