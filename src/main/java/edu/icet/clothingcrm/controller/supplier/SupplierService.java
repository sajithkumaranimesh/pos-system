package edu.icet.clothingcrm.controller.supplier;

import edu.icet.clothingcrm.dto.Supplier;

import java.util.List;

public interface SupplierService {
    List<Supplier> loadSupplier();

    Supplier searchSupplier(String supplierId);

    boolean addSupplier(Supplier supplier);

    boolean deleteSupplier(String supplierId);
}
