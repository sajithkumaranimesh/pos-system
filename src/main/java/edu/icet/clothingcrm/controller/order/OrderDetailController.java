package edu.icet.clothingcrm.controller.order;

import edu.icet.clothingcrm.util.CrudUtil;
import edu.icet.clothingcrm.dto.OrderDetail;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailController {
    private static OrderDetailController instance;

    private OrderDetailController(){}

    public static OrderDetailController getInstance(){
        if (instance == null){
            return instance = new OrderDetailController();
        }
        return instance;
    }

    public boolean addOrderDetail(List<OrderDetail> orderDetailList){
        boolean isAdd = false;
        for (OrderDetail orderDetail : orderDetailList){
            isAdd = addOrderDetail(orderDetail);

        }
        if (isAdd){
            return true;
        }
        return false;
    }

    public boolean addOrderDetail(OrderDetail orderDetail){
        try {
            Object isAdd = CrudUtil.execute(
                    "INSERT INTO orderdetail VALUES(?,?,?,?,?,?)",
                    orderDetail.getOrderId(),
                    orderDetail.getCategoryId(),
                    orderDetail.getProductId(),
                    orderDetail.getQtyCustomer(),
                    orderDetail.getDescount(),
                    orderDetail.getTotalCost()
            );
            return (boolean) isAdd;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
