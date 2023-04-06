package models;

import dal.Account;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccountDAO extends AbstractDAO<Account> {
    
    public ArrayList<Account> getCustomerAccountOnly() throws Exception {
        String sql = "SELECT * FROM Accounts WHERE CustomerID IS NOT NULL";
        return selectMany(sql);
    }

    public Account getAccount(String email, String password) throws Exception {
        String sql = "select * from Accounts where Email = ? and Password = ?";
        return selectOne(sql, email, password);
    }

    public Account getAccountByEmail(String email) throws Exception {
        String sql = "select * from Accounts where Email = ?";
        return selectOne(sql, email);
    }

    public void insertAccount(Account account) throws Exception {
        String sql = "INSERT INTO Accounts VALUES (?, ?, ?, NULL, 2)";
        int generatedId = insert(
                sql,
                account.getEmail(),
                account.getPassword(),
                account.getCustomerID()
        );
        account.setAccountID(generatedId);
    }

    public int updateAccount(Account account) throws SQLException, Exception {
        String sql = "UPDATE Accounts SET Email = ?, Password = ?, CustomerID = ? WHERE AccountID = ?";
        return update(
                sql,
                account.getEmail(),
                account.getPassword(),
                account.getCustomerID(),
                account.getAccountID()
        );
    }

    public static void main(String[] args) throws Exception {
//        Account a = new Account(0, "am@gmail.com", "312", "ERNSH", 0, 2);
//        new AccountDAO().insertAccount(a);
        System.out.println(new AccountDAO().getAccountByEmail("cust1@gmail.com"));
    }

    @Override
    protected Account propMapping(ResultSet rs) throws Exception {
        return new Account(
                rs.getInt("AccountID"),
                rs.getString("Email"),
                rs.getString("Password"),
                rs.getString("CustomerID"),
                rs.getInt("EmployeeID"),
                rs.getInt("Role")
        );
    }
}
