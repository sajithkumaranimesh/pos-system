package edu.icet.clothingcrm.controller.login;

import com.jfoenix.controls.JFXTextField;
import edu.icet.clothingcrm.bo.BoFactory;
import edu.icet.clothingcrm.bo.custom.UserBo;
import edu.icet.clothingcrm.dto.User;
import edu.icet.clothingcrm.util.BoType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterFormController {
    public JFXTextField txtUsername;
    public JFXTextField txtPassword;
    public JFXTextField txtEmail;
    private Parent newWindow;

    private static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    public UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);

    public void btnRegisterOnAction(ActionEvent actionEvent) {
        List<User> userList = UserCoontroller.getInstance().loadUser();
        boolean usernameExists = false;

        // Check if the username already exists
        for (User user : userList) {
            if (user.getUsername().equalsIgnoreCase(txtUsername.getText())) {
                usernameExists = true;
                break;
            }
        }

        if (usernameExists) {
            new Alert(Alert.AlertType.ERROR, "Already existing username. Try another name.").show();
        } else {
            if (isValidPassword(txtPassword.getText())){
                // If username is unique, attempt to add the new user
                User newUser = new User(
                        "1",
                        txtUsername.getText(),
                        txtPassword.getText(),
                        txtEmail.getText(),
                        "owner",
                        "E002"
                );

                boolean isUserAdded = userBo.saveUser(newUser);

                if (!isUserAdded) {  // Assuming isUserAdded returns true if the user is added successfully

                    new Alert(Alert.AlertType.CONFIRMATION, "User Added").show();
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/side-menu.fxml"));
                        Parent newWindow = fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(newWindow));
                        stage.show();

                        // Close the current window
                        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "User Not Added").show();
                }
            }else {
                new Alert(Alert.AlertType.ERROR, "Invalid Password Try again. (8characters or more, including numbers, letters, and symbols). ").show();
            }
        }
    }

    public static boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }


}
