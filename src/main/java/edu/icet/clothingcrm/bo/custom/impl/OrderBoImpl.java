package edu.icet.clothingcrm.bo.custom.impl;

import edu.icet.clothingcrm.bo.custom.OrderBo;
import edu.icet.clothingcrm.dto.Order;

public class OrderBoImpl implements OrderBo {
    @Override
    public boolean saveOrder(Order dto) {
        return false;
    }

    @Override
    public boolean deleteOrderById(String orderId) {
        return false;
    }

    @Override
    public Order searchOrderById(String orderId) {
        return null;
    }
}
