package edu.icet.clothingcrm.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection instance;

    private Connection connection;

    private DBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clothing_crm","root","2000");
    }

    public Connection getConnection(){
        return connection;
    }

    public static DBConnection getInstance() throws ClassNotFoundException, SQLException {
        if (instance == null) {
            return instance = new DBConnection();
        }
        return instance;
    }
}
