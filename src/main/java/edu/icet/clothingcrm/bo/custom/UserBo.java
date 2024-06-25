package edu.icet.clothingcrm.bo.custom;

import edu.icet.clothingcrm.bo.SuperBo;
import edu.icet.clothingcrm.dto.User;

public interface UserBo extends SuperBo {
    boolean saveUser(User dto);
    boolean deleteUserById(String userId);
    User searchUserById(String userId);
}
