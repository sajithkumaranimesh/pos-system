package edu.icet.clothingcrm.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.icet.clothingcrm.bo.BoFactory;
import edu.icet.clothingcrm.bo.custom.UserBo;
import edu.icet.clothingcrm.controller.employee.EmployeeController;
import edu.icet.clothingcrm.controller.login.UserCoontroller;
import edu.icet.clothingcrm.controller.supplier.SupplierController;
import edu.icet.clothingcrm.dto.Employee;
import edu.icet.clothingcrm.dto.Supplier;
import edu.icet.clothingcrm.dto.User;
import edu.icet.clothingcrm.dto.tm.UserTable;
import edu.icet.clothingcrm.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserFormController implements Initializable {
    public Label lblId;
    public JFXTextField txtUsername;
    public JFXTextField txtPassword;
    public JFXTextField txtEmail;
    public JFXComboBox cmbEmployeeId;

    public TableView tblUserTable;
    public TableColumn colUserId;
    public TableColumn colUsername;
    public TableColumn colPassword;
    public TableColumn colEmail;
    public TableColumn colUserType;
    public TableColumn colEmployeeId;

    public JFXTextField txtId;

    private UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userType"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));

        loadUserTable();
        loadEmployeeIds();
    }

    private void loadUserTable(){
        ObservableList<User> tableData = FXCollections.observableArrayList();
        List<User> userList = UserCoontroller.getInstance().loadUser();

        userList.forEach(user -> {
            UserTable userTable = new UserTable(
                    user.getId(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getUserType(),
                    user.getEmployeeId()
            );
            tableData.add(userTable);
        });
        tblUserTable.setItems(tableData);
    }

    private void loadEmployeeIds() {
        List<Employee> employees = EmployeeController.getInstance().loadEmployees();

        ObservableList<String> ids = FXCollections.observableArrayList();

        employees.forEach(employee -> {
            ids.add(employee.getId());
        });
        System.out.println(ids);
        cmbEmployeeId.setItems(ids);
    }


    public void btnAddUserOnAction(ActionEvent actionEvent) {
        User user = new User(
                lblId.getText(),
                txtUsername.getText(),
                txtPassword.getText(),
                txtEmail.getText(),
                "employee",
                cmbEmployeeId.getId()
        );
        boolean isAdded = userBo.saveUser(user);
        System.out.println(isAdded);
    }

    public void btnSearchUserOnAction(ActionEvent actionEvent) {
        User user = userBo.searchUserById(txtId.getId());
        txtId.setText(user.getId());
        txtUsername.setText(user.getUsername());
        txtPassword.setText(user.getPassword());
        txtEmail.setText(user.getEmail());
        cmbEmployeeId.setValue(user.getEmployeeId());
    }

    public void btnDeleteUserOnAction(ActionEvent actionEvent) {
    }


}
