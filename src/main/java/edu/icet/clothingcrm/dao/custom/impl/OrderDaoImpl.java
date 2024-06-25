package edu.icet.clothingcrm.dao.custom.impl;

import edu.icet.clothingcrm.controller.order.OrderDetailController;
import edu.icet.clothingcrm.controller.product.ProductController;
import edu.icet.clothingcrm.dao.custom.OrderDao;
import edu.icet.clothingcrm.db.DBConnection;
import edu.icet.clothingcrm.dto.Order;
import edu.icet.clothingcrm.entity.OrderEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderDaoImpl implements OrderDao {

    @Override
    public boolean save(OrderEntity entity) {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement psTm = connection.prepareStatement("INSERT INTO orders VALUE(?,?,?,?)");
            psTm.setString(1,entity.getId());
            psTm.setObject(2,entity.getOrderDate());
            psTm.setString(3,entity.getCustomerName());
            psTm.setString(4,entity.getCustomerEmail());

            boolean isOrderAdd = psTm.execute();

            if (!isOrderAdd){
                boolean isOrderDetailAdd = OrderDetailController.getInstance().addOrderDetail(entity.getOrderDetailList());
                if (isOrderDetailAdd){
                    boolean isUpdateStock = ProductController.getInstance().updateStock(entity.getOrderDetailList());
                    if (isUpdateStock){
                        connection.setAutoCommit(true);
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("finally");
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public Order search(String id) {
        return null;
    }
}
