package edu.icet.clothingcrm.dao.custom;

import edu.icet.clothingcrm.dao.CrudDao;
import edu.icet.clothingcrm.dto.Category;
import edu.icet.clothingcrm.dto.Employee;
import edu.icet.clothingcrm.entity.EmployeeEntity;

public interface EmployeeDao extends CrudDao<EmployeeEntity> {
    Employee search(String id);
}
