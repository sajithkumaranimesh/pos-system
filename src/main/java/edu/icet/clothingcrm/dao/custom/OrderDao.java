package edu.icet.clothingcrm.dao.custom;

import edu.icet.clothingcrm.dao.CrudDao;
import edu.icet.clothingcrm.dto.Order;
import edu.icet.clothingcrm.entity.OrderEntity;

public interface OrderDao extends CrudDao<OrderEntity> {
    Order search(String id);
}
