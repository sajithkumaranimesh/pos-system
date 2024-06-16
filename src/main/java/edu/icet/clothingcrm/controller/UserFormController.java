package edu.icet.clothingcrm.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.icet.clothingcrm.db.DBConnection;
import edu.icet.clothingcrm.dto.User;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserFormController {
    public Label lblTime;
    public Label lblDate;

    public JFXTextField txtId;
    public JFXTextField txtEmail;
    public JFXTextField txtPassword;
    public JFXTextField txtEmployeeId;
    
    public TableColumn colId;
    public TableColumn colEmail;
    public TableColumn colPassword;
    public TableColumn colUsername;
    public TableColumn colEmployeeId;
    public TableColumn colAction;



    public void btnAddUserOnAction(ActionEvent actionEvent) {
        User user = new User(
                Integer.parseInt(txtId.getText()),
                txtEmail.getText(),
                txtPassword.getText(),
                Integer.parseInt(txtEmployeeId.getText())
        );
        System.out.println(user);

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement("INSERT INTO user VALUES (?,?,?,?)");
            psTm.setInt(1,user.getId());
            psTm.setString(2, user.getEmail());
            psTm.setString(3, user.getPassword());
            psTm.setString(4, String.valueOf(user.getEmployeeId()));
            psTm.execute();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
