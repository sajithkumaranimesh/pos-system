package edu.icet.clothingcrm.controller;

import com.jfoenix.controls.JFXTextField;
import edu.icet.clothingcrm.db.DBConnection;
import edu.icet.clothingcrm.dto.Supplier;
import edu.icet.clothingcrm.dto.tm.SupplierTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SupplierFormController implements Initializable {
    public Label lblTime;
    public Label lblDate;


    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtEmail;
    public JFXTextField txtCompany;


    public TableView tblSupplierTable;
    public TableColumn colSupplierId;
    public TableColumn colName;
    public TableColumn colEmail;
    public TableColumn colCompany;
    
    private List<Supplier> supplierList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
        loadSupplier();
        loadSupplierTable();
    }

    private void loadSupplierTable() {
        ObservableList<Supplier> tableData = FXCollections.observableArrayList();
        
        supplierList.forEach(supplier -> {
            SupplierTable supplierTable = new SupplierTable(
                    supplier.getId(),
                    supplier.getName(),
                    supplier.getEmail(),
                    supplier.getCompany()
            );
            tableData.add(supplierTable);
        });
        tblSupplierTable.setItems(tableData);
    }

    private void loadSupplier() {
        supplierList = new ArrayList<>();
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM supplier");
            while (resultSet.next()){
                Supplier supplier = new Supplier(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                );
                System.out.println(supplier);
                supplierList.add(supplier);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnAddSupplierOnAction(ActionEvent actionEvent) {
        Supplier supplier = new Supplier(
                Integer.parseInt(txtId.getText()),
                txtName.getText(),
                txtEmail.getText(),
                txtCompany.getText()
        );
        System.out.println(supplier);

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement("INSERT INTO supplier VALUES (?,?,?,?)");
            psTm.setInt(1,supplier.getId());
            psTm.setString(2,supplier.getName());
            psTm.setString(3,supplier.getEmail());
            psTm.setString(4,supplier.getCompany());
            psTm.execute();

            loadSupplier();
            loadSupplierTable();
            clearText();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSearchSupplierOnAction(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM supplier where id='" + txtId.getText() + "'");
            while (resultSet.next()){
                Supplier supplier = new Supplier(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(3)
                );
                System.out.println(supplier);

                txtId.setText(String.valueOf(supplier.getId()));
                txtName.setText(supplier.getName());
                txtEmail.setText(supplier.getEmail());
                txtCompany.setText(supplier.getCompany());
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnDeleteSupplierOnAction(ActionEvent actionEvent) {
        try {
            boolean execute = DBConnection.getInstance().getConnection().createStatement().execute("DELETE FROM supplier where id='" + txtId.getText() + "'");

            loadSupplier();
            loadSupplierTable();
            clearText();
            if(execute){
                System.out.println("Supplier not deleted");
            }else {
                System.out.println("Supplier deleted");
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearText(){
        txtId.setText(null);
        txtName.setText(null);
        txtEmail.setText(null);
        txtCompany.setText(null);
    }

}
