package edu.icet.clothingcrm.controller.product;

import edu.icet.clothingcrm.util.CrudUtil;
import edu.icet.clothingcrm.dto.OrderDetail;
import edu.icet.clothingcrm.dto.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductController implements ProductService{

    private static ProductController instance;

    private ProductController(){}

    public static ProductController getInstance(){
        if (instance == null){
            instance = new ProductController();
        }
        return instance;
    }

    private List<Product> productList;

    public List<Product> loadProduct(){
        productList = new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM product");
            while (resultSet.next()){
                Product product = new Product(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7)
                );
                System.out.println(product);
                productList.add(product);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }

    public Product searchProduct(String productId){
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM product where id='" + productId + "'");
            while (resultSet.next()){
                return new Product(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7)
                );

            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean addProduct(Product product){
        try {
            String sql = "INSERT INTO product VALUES (?,?,?,?,?,?,?)";
            CrudUtil.execute(
                    sql,
                    product.getId(),
                    product.getName(),
                    product.getSize(),
                    product.getPrice(),
                    product.getQuantityOnHand(),
                    product.getCategoryId(),
                    product.getSupplierId()
            );
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean deleteProduct(String productId){
        try {
            String sql = "DELETE FROM product where id= ? ";
            return CrudUtil.execute(sql,productId);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateStock(List<OrderDetail> orderDetailList) {
        boolean isUpdate = false;
        for (OrderDetail orderDetail1 : orderDetailList){
            isUpdate =  updateStock(orderDetail1);
        }
        if (isUpdate){
            return true;
        }
        return false;
    }

    public boolean updateStock(OrderDetail orderDetail) {
        try {
            Object isUpdate = CrudUtil.execute(
                    "UPDATE product SET qtyOnHand=qtyOnHand-? WHERE id=?",
                    orderDetail.getQtyCustomer(),
                    orderDetail.getProductId()
            );
            return (boolean) isUpdate;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
