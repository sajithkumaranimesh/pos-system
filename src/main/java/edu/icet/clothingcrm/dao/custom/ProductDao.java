package edu.icet.clothingcrm.dao.custom;

import edu.icet.clothingcrm.dao.CrudDao;
import edu.icet.clothingcrm.dto.Category;
import edu.icet.clothingcrm.dto.Product;
import edu.icet.clothingcrm.entity.ProductEntity;

public interface ProductDao extends CrudDao<ProductEntity> {
    Product search(String id);
}
