package edu.icet.clothingcrm.controller.login;

import edu.icet.clothingcrm.dto.User;
import edu.icet.clothingcrm.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserCoontroller {
    private List<User> userList;

    private static UserCoontroller instance;

    private UserCoontroller(){}

    public static UserCoontroller getInstance(){
        if (instance == null){
            return instance = new UserCoontroller();
        }
        return instance;
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
