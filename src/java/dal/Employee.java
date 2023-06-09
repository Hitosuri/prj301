package dal;

import java.sql.Date;

public class Employee {

    private int employeeID;
    private String lastName;
    private String firstName;
    private int departmentID;
    private String title;
    private String titleOfCourtesy;
    private Date birthDate;
    private Date hireDate;
    private String address;

    public Employee(int employeeID, String lastName, String firstName, int departmentID, String title, String titleOfCourtesy, Date birthDate, Date hireDate, String address) {
        this.employeeID = employeeID;
        this.lastName = lastName;
        this.firstName = firstName;
        this.departmentID = departmentID;
        this.title = title;
        this.titleOfCourtesy = titleOfCourtesy;
        this.birthDate = birthDate;
        this.hireDate = hireDate;
        this.address = address;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleOfCourtesy() {
        return titleOfCourtesy;
    }

    public void setTitleOfCourtesy(String titleOfCourtesy) {
        this.titleOfCourtesy = titleOfCourtesy;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Employee{" + "employeeID=" + employeeID + ", lastName=" + lastName + ", firstName=" + firstName + ", departmentID=" + departmentID + ", title=" + title + ", titleOfCourtesy=" + titleOfCourtesy + ", birthDate=" + birthDate + ", hireDate=" + hireDate + ", address=" + address + '}';
    }

}
