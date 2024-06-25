package edu.icet.clothingcrm.bo.custom.impl;

import edu.icet.clothingcrm.bo.custom.SupplierBo;
import edu.icet.clothingcrm.dao.DaoFactory;
import edu.icet.clothingcrm.dao.custom.SupplierDao;
import edu.icet.clothingcrm.dto.Supplier;
import edu.icet.clothingcrm.entity.SupplierEntity;
import edu.icet.clothingcrm.util.DaoType;
import org.modelmapper.ModelMapper;

public class SupplierBoImpl implements SupplierBo {

    private SupplierDao supplierDao = DaoFactory.getInstance().getDao(DaoType.SUPPLIER);

    @Override
    public boolean saveSupplier(Supplier dto) {
        return supplierDao.save(new ModelMapper().map(dto, SupplierEntity.class));
    }

    @Override
    public boolean deleteSupplierById(String supplierId) {
        return supplierDao.delete(supplierId);
    }

    @Override
    public Supplier searchSupplierById(String supplierId) {
        return supplierDao.search(supplierId);
    }
}
