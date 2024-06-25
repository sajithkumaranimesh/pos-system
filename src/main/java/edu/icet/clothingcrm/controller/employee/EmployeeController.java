package edu.icet.clothingcrm.controller.employee;

import edu.icet.clothingcrm.util.CrudUtil;
import edu.icet.clothingcrm.dto.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeController implements EmployeeService{

    private List<Employee> employeeList;

    private static EmployeeController instance;

    private EmployeeController(){}

    public static EmployeeController getInstance(){
        if(instance == null){
            return instance = new EmployeeController();
        }
        return instance;
    }

    public List<Employee> loadEmployees() {
        employeeList = new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM employee");
            while (resultSet.next()) {
                Employee employee = new Employee(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                );
                System.out.println(employee);
                employeeList.add(employee);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return employeeList;
    }

    public Employee searchEmployee(String employeeId){
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM employee where id='" + employeeId + "'");
            while (resultSet.next()) {
                return new Employee(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                );

            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean addEmployee(Employee employee){
        try {
            String sql = "INSERT INTO employee VALUES (?,?,?,?)";
            CrudUtil.execute(
                    sql,
                    employee.getId(),
                    employee.getName(),
                    employee.getEmail(),
                    employee.getAddress()
                    );

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean deleteEmployee(String employeeId) {
        try {
            String query = "DELETE FROM employee WHERE id = ?";
            return CrudUtil.execute(query, employeeId);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
