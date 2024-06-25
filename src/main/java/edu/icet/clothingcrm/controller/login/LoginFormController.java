package edu.icet.clothingcrm.controller.login;

import com.jfoenix.controls.JFXTextField;
import edu.icet.clothingcrm.dto.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {
    public JFXTextField txtUsername;
    public JFXTextField txtPassword;
    public BorderPane oldBorderPane;
    private Parent  newWindow;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void btnLoginOnAction(ActionEvent actionEvent) {
        List<User> userList = UserCoontroller.getInstance().loadUser();
        userList.forEach(users -> {
            if (users.getUsername().equalsIgnoreCase(txtUsername.getText()) && users.getPassword().equalsIgnoreCase(txtPassword.getText())){
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/side-menu.fxml"));
                    newWindow = fxmlLoader.load();
                    // Optionally, show the new window
                    Stage stage = new Stage();
                    stage.setScene(new Scene(newWindow));
                    stage.show();
                    // Close the current window
                    ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else if (!(users.getUsername().equalsIgnoreCase(txtUsername.getText()))){
                new Alert(Alert.AlertType.ERROR, "wrong username").show();
            }else if (!(users.getPassword().equalsIgnoreCase(txtPassword.getText()))){
                new Alert(Alert.AlertType.ERROR, "wrong password").show();
            }else {

            }
        });
    }

    public void btnRegisterOnAction(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/register-form.fxml"));
        Parent newWindow = null;
        try {
            newWindow = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(newWindow));
        stage.show();

        // Close the current window
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }

    public void btnForgetPasswordOnAction(ActionEvent actionEvent) {
    }


}
