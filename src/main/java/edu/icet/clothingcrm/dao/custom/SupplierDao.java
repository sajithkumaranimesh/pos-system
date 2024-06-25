package edu.icet.clothingcrm.dao.custom;

import edu.icet.clothingcrm.dao.CrudDao;
import edu.icet.clothingcrm.dto.Category;
import edu.icet.clothingcrm.dto.Supplier;
import edu.icet.clothingcrm.entity.SupplierEntity;

public interface SupplierDao extends CrudDao<SupplierEntity> {
    Supplier search(String id);
}
