package edu.icet.clothingcrm.bo.custom;

import edu.icet.clothingcrm.bo.SuperBo;
import edu.icet.clothingcrm.dto.Employee;

public interface EmployeeBo extends SuperBo {
    boolean saveEmployee(Employee dto);
    boolean deleteEmployeeById(String employeeId);
    Employee searchEmployeeById(String employeeId);
}
