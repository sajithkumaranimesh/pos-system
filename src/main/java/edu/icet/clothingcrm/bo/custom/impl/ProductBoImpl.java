package edu.icet.clothingcrm.bo.custom.impl;

import edu.icet.clothingcrm.bo.BoFactory;
import edu.icet.clothingcrm.bo.custom.ProductBo;
import edu.icet.clothingcrm.dao.DaoFactory;
import edu.icet.clothingcrm.dao.custom.ProductDao;
import edu.icet.clothingcrm.dto.Product;
import edu.icet.clothingcrm.entity.ProductEntity;
import edu.icet.clothingcrm.util.DaoType;
import org.modelmapper.ModelMapper;

public class ProductBoImpl implements ProductBo {

    private ProductDao productDao = DaoFactory.getInstance().getDao(DaoType.PRODUCT);

    @Override
    public boolean saveProduct(Product dto) {
        return productDao.save(new ModelMapper().map(dto, ProductEntity.class));
    }

    @Override
    public boolean deleteProductById(String productId) {
        return productDao.delete(productId);
    }
    @Override
    public Product searchProductById(String productId) {
        return productDao.search(productId);
    }
}
