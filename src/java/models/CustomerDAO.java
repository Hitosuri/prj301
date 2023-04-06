package models;

import dal.Customer;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

public class CustomerDAO extends AbstractDAO<Customer> {
    
    public ArrayList<Customer> getNewCustomers() throws Exception {
        String sql = "SELECT * FROM Customers WHERE CreateDate > DATEADD(DAY, -30, GETDATE())";
        return selectMany(sql);
    }

    public ArrayList<Customer> getCustomers() throws Exception {
        String sql = "SELECT * FROM Customers";
        return selectMany(sql);
    }

    public Customer getCustomerById(String id) throws Exception {
        String sql = "SELECT * FROM Customers WHERE CustomerID = ?";
        return selectOne(sql, id);
    }

    public int insertCustomer(Customer customer) throws Exception {
        Random rd = new Random();
        String CustomerID = customer.getCustomerID();
        while (getCustomerById(CustomerID) != null || CustomerID.trim().length() != 5 || !CustomerID.equals(CustomerID.toUpperCase())) {
            CustomerID = rd.ints(65, 90).limit(5).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
        }
        customer.setCustomerID(CustomerID);
        customer.setCreateDate(new Date(System.currentTimeMillis()));

        String sql = "INSERT INTO Customers VALUES (?, ?, ?, ?, ?, ?)";
        return update(
                sql,
                CustomerID,
                customer.getCompanyName(),
                customer.getContactName(),
                customer.getContactTitle(),
                customer.getAddress(),
                customer.getCreateDate()
        );
    }

    public int updateCustomer(Customer customer) throws Exception {
        String sql = "UPDATE Customers SET CompanyName = ?, ContactName = ?, ContactTitle = ?, Address = ? WHERE CustomerID = ?";
        return update(
                sql,
                customer.getCompanyName(),
                customer.getContactName(),
                customer.getContactTitle(),
                customer.getAddress(),
                customer.getCustomerID()
        );
    }

    public static void main(String[] args) throws Exception {
        Customer c = new Customer("", "abc", "abc", "abc", "abc", null);
        new CustomerDAO().insertCustomer(c);
        System.out.println(c);
    }

    @Override
    protected Customer propMapping(ResultSet rs) throws Exception {
        return new Customer(
                rs.getString("CustomerID"),
                rs.getString("CompanyName"),
                rs.getString("ContactName"),
                rs.getString("ContactTitle"),
                rs.getString("Address"),
                rs.getDate("CreateDate")
        );
    }
}
