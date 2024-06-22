package edu.icet.clothingcrm.controller.employee;

import com.jfoenix.controls.JFXTextField;
import edu.icet.clothingcrm.bo.BoFactory;
import edu.icet.clothingcrm.bo.custom.EmployeeBo;
import edu.icet.clothingcrm.dto.Employee;
import edu.icet.clothingcrm.dto.tm.EmployeeTable;
import edu.icet.clothingcrm.util.BoType;
import edu.icet.clothingcrm.util.CrudUtil;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public TableView tblEmployeeTable;

    public EmployeeBo employeeBo = BoFactory.getInstance().getBo(BoType.EMPLOYEE);
    public Label lblId;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));


        loadEmoloyeeTable();
        generateEmployeeId();
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
                lblId.getText(),
                txtName.getText(),
                txtEmail.getText(),
                txtAddress.getText()
        );

        boolean isEmployeeAdded = employeeBo.saveEmployee(employee);
        if (isEmployeeAdded) {
            new Alert(Alert.AlertType.ERROR, "Employee Not Added").show();
        } else {
            new Alert(Alert.AlertType.CONFIRMATION, "Employee Added").show();
        }

        loadEmoloyeeTable();
        clearText();
        generateEmployeeId();
    }


    public void btnsearchOnAction(ActionEvent actionEvent) {
        Employee employee = employeeBo.searchEmployeeById(txtId.getText());
        lblId.setText(String.valueOf(employee.getId()));
        txtEmail.setText(employee.getEmail());
        txtAddress.setText(employee.getAddress());
        txtName.setText(employee.getName());
        System.out.println(employee);
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        boolean isDeleted = employeeBo.deleteEmployeeById(txtId.getText());
        if (isDeleted) {
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

    public void generateEmployeeId() throws RuntimeException {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT COUNT(*) FROM employee");
            Integer count = 0;
            while (resultSet.next()) {
                count = resultSet.getInt(1);

            }
            if (count == 0) {
                lblId.setText("E001");
            }
            String lastOrderId = "";
            ResultSet resultSet1 = CrudUtil.execute("SELECT id\n" +
                    "FROM employee\n" +
                    "ORDER BY id DESC\n" +
                    "LIMIT 1;");
            if (resultSet1.next()) {
                lastOrderId = resultSet1.getString(1);
                Pattern pattern = Pattern.compile("[A-Za-z](\\d+)");
                Matcher matcher = pattern.matcher(lastOrderId);
                if (matcher.find()) {
                    int number = Integer.parseInt(matcher.group(1));
                    number++;
                    lblId.setText(String.format("E%03d", number));
                } else {
                    new Alert(Alert.AlertType.WARNING, "hello").show();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
