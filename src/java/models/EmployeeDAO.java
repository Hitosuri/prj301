package models;

import dal.Employee;
import java.sql.ResultSet;

public class EmployeeDAO extends AbstractDAO<Employee> {
    
    public Employee getEmployeeById(int id) throws Exception {
        return selectOne("SELECT * FROM Employees WHERE EmployeeID = ?", id);
    }

    @Override
    protected Employee propMapping(ResultSet rs) throws Exception {
        return new Employee(
                rs.getInt("EmployeeID"),
                rs.getString("LastName"),
                rs.getString("FirstName"),
                rs.getInt("DepartmentID"),
                rs.getString("Title"),
                rs.getString("TitleOfCourtesy"),
                rs.getDate("BirthDate"),
                rs.getDate("HireDate"),
                rs.getString("Address")
        );
    }
    
}
