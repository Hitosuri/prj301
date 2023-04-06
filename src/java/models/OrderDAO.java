package models;

import dal.Order;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderDAO extends AbstractDAO<Order> {
    
    public Order getLastOrder() throws Exception {
        String sql = "SELECT TOP 1 * FROM Orders ORDER BY OrderID DESC";
        return selectOne(sql);
    }
    
    public Order getFirstOrder() throws Exception {
        String sql = "SELECT TOP 1 * FROM Orders ORDER BY OrderID";
        return selectOne(sql);
    }
    
    public Order getOrderById(int id) throws Exception {
        String sql = "SELECT * FROM Orders WHERE OrderID = ?";
        return selectOne(sql, id);
    }

    public ArrayList<Order> getOrders() throws Exception {
        String sql = "SELECT * FROM Orders";
        return selectMany(sql);
    }

    public ArrayList<Order> getOrdersInDateRange(Date a, Date b) throws Exception {
        String sql = "SELECT * FROM Orders WHERE OrderDate BETWEEN ? AND ?";
        return selectMany(sql, a, b);
    }

    public ArrayList<Order> getNonCancelOrdersByCID(String id) throws Exception {
        String sql = "SELECT * FROM Orders WHERE CustomerID = ? AND RequiredDate IS NOT NULL";
        return selectMany(sql, id);
    }

    public ArrayList<Order> getCanceledOrdersByCID(String id) throws Exception {
        String sql = "SELECT * FROM Orders WHERE CustomerID = ? AND RequiredDate IS NULL";
        return selectMany(sql, id);
    }

    public void insertOrder(Order order) throws Exception {
        String sql = "INSERT INTO Orders VALUES (?, NULL, ?, ?, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL)";
        int generatedId = insert(sql, order.getCustomerID(), order.getOrderDate(), order.getRequiredDate());
        order.setOrderID(generatedId);
    }

    public int cancelOrder(int id) throws Exception {
        String sql = "UPDATE Orders SET RequiredDate = NULL WHERE OrderID = ?";
        return update(sql, id);
    }
    
    public ArrayList<Integer> getManyYearStat(int fromyear, int toyear) throws Exception {
        ArrayList<Integer> stat = new ArrayList<>();
        for (int i = fromyear; i <= toyear; i++) {
            stat.add(0);
        }
        try {
            String sql = "SELECT YEAR(OrderDate) YY, COUNT(YEAR(OrderDate)) OrderNumber FROM Orders WHERE YEAR(OrderDate) BETWEEN ? AND ? GROUP BY YEAR(OrderDate)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, Math.min(fromyear, toyear));
            ps.setInt(2, Math.max(fromyear, toyear));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                stat.set(rs.getInt("YY") - fromyear, rs.getInt("OrderNumber"));
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return stat;
    }
    
    public ArrayList<Integer> getOneYearStat(int year) throws Exception {
        ArrayList<Integer> stat = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            stat.add(0);
        }
        try {
            String sql = "SELECT MONTH(OrderDate) MM, COUNT(MONTH(OrderDate)) OrderNumber FROM Orders WHERE YEAR(OrderDate) = ? GROUP BY MONTH(OrderDate)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, year);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                stat.set(rs.getInt("MM") - 1, rs.getInt("OrderNumber"));
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return stat;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(new OrderDAO().getManyYearStat(1996, 2022));
//        OrderDAO orderDAO = new OrderDAO();
//        orderDAO.setItemList(orderDAO.getOrdersInDateRange(Date.valueOf("2022-09-01"), Date.valueOf("2022-09-03")));
//        orderDAO.setMaxPageItem(5);
//        orderDAO.setMaxTotalPage(8);
//        int[] pageRange = orderDAO.getPageRange(1);
//        System.out.println(orderDAO.getItemsInPage(1));
//        System.out.println(pageRange[0] + " " + pageRange[1]);
    }

    @Override
    protected Order propMapping(ResultSet rs) throws Exception {
        return new Order(
                rs.getInt("OrderID"),
                rs.getString("CustomerID"),
                rs.getInt("EmployeeID"),
                rs.getDate("OrderDate"),
                rs.getDate("RequiredDate"),
                rs.getDate("ShippedDate"),
                rs.getDouble("Freight"),
                rs.getString("ShipName"),
                rs.getString("ShipAddress"),
                rs.getString("ShipCity"),
                rs.getString("ShipRegion"),
                rs.getString("ShipPostalCode"),
                rs.getString("ShipCountry")
        );
    }
}
