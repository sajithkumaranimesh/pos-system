package edu.icet.clothingcrm.controller;

import com.jfoenix.controls.JFXTextField;
import edu.icet.clothingcrm.db.DBConnection;
import edu.icet.clothingcrm.dto.RegisterUser;
import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterFormController {

    public JFXTextField txtUsername;
    public JFXTextField txtPassword;
    public JFXTextField txtEmail;

    public void btnRegisterOnAction(ActionEvent actionEvent) {
        RegisterUser registerUser = new RegisterUser(
                txtUsername.getText(),
                txtPassword.getText(),
                txtEmail.getText()
        );
        System.out.println(registerUser);

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement("INSERT INTO user VALUES (?,?,?)");
            psTm.setString(1,registerUser.getUsername());
            psTm.setString(2,registerUser.getPassword());
            psTm.setString(3,registerUser.getEmail());
            psTm.execute();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
