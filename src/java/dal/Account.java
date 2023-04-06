package dal;

public class Account {

    private int accountID;
    private String email;
    private String password;
    private String customerID;
    private int employeeID;
    private int role;

    public Account(int accountID, String email, String password, String customerID, int employeeID, int role) {
        this.accountID = accountID;
        this.email = email;
        this.password = password;
        this.customerID = customerID;
        this.employeeID = employeeID;
        this.role = role;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Account{" + "accountID=" + accountID + ", email=" + email + ", password=" + password + ", customerID=" + customerID + ", employeeID=" + employeeID + ", role=" + role + '}';
    }

}
