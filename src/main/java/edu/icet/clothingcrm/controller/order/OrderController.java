package edu.icet.clothingcrm.controller.order;

import edu.icet.clothingcrm.controller.product.ProductController;
import edu.icet.clothingcrm.db.DBConnection;
import edu.icet.clothingcrm.dto.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderController {

    private static OrderController instance;

    private OrderController(){}

    public static OrderController getInstance(){
        if(instance == null){
            return instance = new OrderController();
        }
        return instance;
    }

    public boolean placeOrder(Order order) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement psTm = connection.prepareStatement("INSERT INTO orders VALUE(?,?,?,?)");
            psTm.setString(1,order.getOrderId());
            psTm.setObject(2,order.getOrderDate());
            psTm.setString(3,order.getCustomerName());
            psTm.setString(4,order.getCustomerEmail());

            boolean isOrderAdd = psTm.execute();

            if (!isOrderAdd){
                boolean isOrderDetailAdd = OrderDetailController.getInstance().addOrderDetail(order.getOrderDetailList());
                if (isOrderDetailAdd){
                    boolean isUpdateStock = ProductController.getInstance().updateStock(order.getOrderDetailList());
                    if (isUpdateStock){
                        connection.setAutoCommit(true);
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;

        } finally {
            System.out.println("finally");
            connection.setAutoCommit(true);
        }
    }
}
