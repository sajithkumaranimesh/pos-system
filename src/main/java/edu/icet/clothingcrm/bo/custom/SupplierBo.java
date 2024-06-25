package edu.icet.clothingcrm.bo.custom;

import edu.icet.clothingcrm.bo.SuperBo;
import edu.icet.clothingcrm.dto.Supplier;

public interface SupplierBo extends SuperBo {
    boolean saveSupplier(Supplier dto);
    boolean deleteSupplierById(String supplierId);
    Supplier searchSupplierById(String supplierId);
}
