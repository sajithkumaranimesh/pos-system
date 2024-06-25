package edu.icet.clothingcrm.controller.product;

import edu.icet.clothingcrm.dto.Product;

import java.util.List;

public interface ProductService {
    List<Product> loadProduct();

    Product searchProduct(String productId);

    boolean addProduct(Product product);

    boolean deleteProduct(String productId);
}
