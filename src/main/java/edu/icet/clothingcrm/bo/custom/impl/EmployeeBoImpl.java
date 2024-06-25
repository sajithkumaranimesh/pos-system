package edu.icet.clothingcrm.bo.custom.impl;

import edu.icet.clothingcrm.bo.custom.EmployeeBo;
import edu.icet.clothingcrm.dao.DaoFactory;
import edu.icet.clothingcrm.dao.custom.EmployeeDao;
import edu.icet.clothingcrm.dto.Employee;
import edu.icet.clothingcrm.entity.EmployeeEntity;
import edu.icet.clothingcrm.util.DaoType;
import org.modelmapper.ModelMapper;

public class EmployeeBoImpl implements EmployeeBo {

    private EmployeeDao employeeDao = DaoFactory.getInstance().getDao(DaoType.EMPLOYEE);

    @Override
    public boolean saveEmployee(Employee dto) {
        return employeeDao.save(new ModelMapper().map(dto, EmployeeEntity.class));
    }

    @Override
    public boolean deleteEmployeeById(String employeeId) {
        return employeeDao.delete(employeeId);
    }

    @Override
    public Employee searchEmployeeById(String employeeId) {
        return employeeDao.search(employeeId);
    }
}
