package edu.icet.clothingcrm.controller.employee;

import com.jfoenix.controls.JFXTextField;
import edu.icet.clothingcrm.db.DBConnection;
import edu.icet.clothingcrm.dto.Employee;
import edu.icet.clothingcrm.dto.tm.EmployeeTable;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeFormController implements Initializable {
    public Label lblTime;
    public Label lblDate;

    public JFXTextField txtId;
    public JFXTextField txtEmail;
    public JFXTextField txtAddress;
    public JFXTextField txtName;

    public TableColumn colId;
    public TableColumn colEmail;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colAction;
    public TableView tblEmployeeTable;

    //private List<Employee> employeeList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        loadDateAndTime();

        loadEmoloyeeTable();
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(simpleDateFormat.format(date));

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime time = LocalTime.now();
            lblTime.setText(
                    time.getHour() + " : " + time.getMinute() + " : " + time.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void loadEmoloyeeTable() {
        ObservableList<Employee> tableData = FXCollections.observableArrayList();
        List<Employee> employeeList = EmployeeController.getInstance().loadEmployees();

        employeeList.forEach(employee -> {
            EmployeeTable employeeTable = new EmployeeTable(
                    employee.getId(),
                    employee.getName(),
                    employee.getEmail(),
                    employee.getAddress()
            );
            tableData.add(employeeTable);
        });
        tblEmployeeTable.setItems(tableData);
    }


    public void btnAddEmployeeOnAction(ActionEvent actionEvent) {
        Employee employee = new Employee(
                Integer.parseInt(txtId.getText()),
                txtName.getText(),
                txtEmail.getText(),
                txtAddress.getText()
        );

        boolean b = EmployeeController.getInstance().addEmployee(employee);
        if (b) {
            new Alert(Alert.AlertType.ERROR, "Employee Not Added").show();
        } else {
            new Alert(Alert.AlertType.CONFIRMATION, "Employee Added").show();
        }

        loadEmoloyeeTable();
    }


    public void btnsearchOnAction(ActionEvent actionEvent) {
        Employee employee = EmployeeController.getInstance().searchEmployee(txtId.getText());
        txtId.setText(String.valueOf(employee.getId()));
        txtEmail.setText(employee.getEmail());
        txtAddress.setText(employee.getAddress());
        txtName.setText(employee.getName());
        System.out.println(employee);
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        boolean execute = EmployeeController.getInstance().deleteEmployee(txtId.getText());
        if (execute) {
            System.out.println("Employee deleted");
            loadEmoloyeeTable();
            clearText();
        } else {
            System.out.println("Employee Not  deleted");

        }
    }

    private void clearText() {
        txtId.setText(null);
        txtEmail.setText(null);
        txtAddress.setText(null);
        txtName.setText(null);
    }
}
