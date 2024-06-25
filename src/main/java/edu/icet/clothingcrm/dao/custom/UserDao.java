package edu.icet.clothingcrm.dao.custom;

import edu.icet.clothingcrm.dao.CrudDao;
import edu.icet.clothingcrm.dto.User;
import edu.icet.clothingcrm.entity.UserEntity;

public interface UserDao extends CrudDao<UserEntity> {
    User search(String id);
}
