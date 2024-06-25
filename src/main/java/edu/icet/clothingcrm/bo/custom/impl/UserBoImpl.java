package edu.icet.clothingcrm.bo.custom.impl;

import edu.icet.clothingcrm.bo.custom.UserBo;
import edu.icet.clothingcrm.dao.DaoFactory;
import edu.icet.clothingcrm.dao.custom.UserDao;
import edu.icet.clothingcrm.dto.User;
import edu.icet.clothingcrm.entity.UserEntity;
import edu.icet.clothingcrm.util.CrudUtil;
import edu.icet.clothingcrm.util.DaoType;
import org.modelmapper.ModelMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBoImpl implements UserBo {

    private UserDao userDao = DaoFactory.getInstance().getDao(DaoType.USER);

    private List<User> userList;

    @Override
    public boolean saveUser(User dto) {
        return userDao.save(new ModelMapper().map(dto, UserEntity.class));
    }

    @Override
    public boolean deleteUserById(String userId) {
        return userDao.delete(userId);
    }

    @Override
    public User searchUserById(String userId) {
        return userDao.search(userId);
    }

    public List<User> loadUser(){
        userList = new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM user");
            while (resultSet.next()){
                User user = new User(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6)
                );
                userList.add(user);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }
}
