package edu.icet.clothingcrm.controller.employee;

import edu.icet.clothingcrm.dto.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> loadEmployees();

    Employee searchEmployee(String employeeId);

    boolean addEmployee(Employee employee);

    boolean deleteEmployee(String employeeId);

}
