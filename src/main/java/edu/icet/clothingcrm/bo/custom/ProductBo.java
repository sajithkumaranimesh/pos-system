package edu.icet.clothingcrm.bo.custom;

import edu.icet.clothingcrm.bo.SuperBo;
import edu.icet.clothingcrm.dto.Product;

public interface ProductBo extends SuperBo {
    boolean saveProduct(Product dto);
    boolean deleteProductById(String productId);
    Product searchProductById(String productId);
}
