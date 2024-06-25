package edu.icet.clothingcrm.bo.custom;

import edu.icet.clothingcrm.bo.SuperBo;
import edu.icet.clothingcrm.dto.Order;

public interface OrderBo extends SuperBo {
    boolean saveOrder(Order dto);
    boolean deleteOrderById(String orderId);
    Order searchOrderById(String orderId);
}
