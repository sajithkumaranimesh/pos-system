package edu.icet.clothingcrm.dao.custom.impl;

import edu.icet.clothingcrm.dao.custom.UserDao;
import edu.icet.clothingcrm.dto.Supplier;
import edu.icet.clothingcrm.dto.User;
import edu.icet.clothingcrm.entity.UserEntity;
import edu.icet.clothingcrm.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

public class UserDaoImpl implements UserDao {
    @Override
    public User search(String id) {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM user where id='" + id + "'");
            while (resultSet.next()){
                return new User(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6)
                );
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean save(UserEntity entity) {
        try {
            String sql = "INSERT INTO user VALUES (?,?,?,?,?,?)";
            CrudUtil.execute(
                    sql,
                    entity.getId(),
                    entity.getUsername(),
                    entity.getPassword(),
                    entity.getEmail(),
                    entity.getUserType(),
                    entity.getEmployeeId()
                    );
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
