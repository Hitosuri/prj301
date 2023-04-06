package dal;

import java.sql.Date;

public class Customer {

    private String customerID;
    private String companyName;
    private String contactName;
    private String contactTitle;
    private String address;
    private Date createDate;

    public Customer(String customerID, String companyName, String contactName, String contactTitle, String address, Date createDate) {
        this.customerID = customerID;
        this.companyName = companyName;
        this.contactName = contactName;
        this.contactTitle = contactTitle;
        this.address = address;
        this.createDate = createDate;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactTitle() {
        return contactTitle;
    }

    public void setContactTitle(String contactTitle) {
        this.contactTitle = contactTitle;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Customer{" + "customerID=" + customerID + ", companyName=" + companyName + ", contactName=" + contactName + ", contactTitle=" + contactTitle + ", address=" + address + ", createDate=" + createDate + '}';
    }

}
