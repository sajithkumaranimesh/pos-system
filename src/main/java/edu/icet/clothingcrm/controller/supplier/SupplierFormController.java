package edu.icet.clothingcrm.controller.supplier;

import com.jfoenix.controls.JFXTextField;
import edu.icet.clothingcrm.crudUtil.CrudUtil;
import edu.icet.clothingcrm.db.DBConnection;
import edu.icet.clothingcrm.dto.Supplier;
import edu.icet.clothingcrm.dto.tm.SupplierTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    
    //private List<Supplier> supplierList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));

        loadSupplierTable();
    }

    private void loadSupplierTable() {
        ObservableList<Supplier> tableData = FXCollections.observableArrayList();

        List<Supplier> supplierList = SupplierController.getInstance().loadSupplier();

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



    public void btnAddSupplierOnAction(ActionEvent actionEvent) {
        Supplier supplier = new Supplier(
                Integer.parseInt(txtId.getText()),
                txtName.getText(),
                txtEmail.getText(),
                txtCompany.getText()
        );

        boolean b = SupplierController.getInstance().addSupplier(supplier);
        if (b) {
            new Alert(Alert.AlertType.ERROR, "Supplier Not Added").show();
        } else {
            new Alert(Alert.AlertType.CONFIRMATION, "Supplier Added").show();
        }

    }

    public void btnSearchSupplierOnAction(ActionEvent actionEvent) {
        Supplier supplier = SupplierController.getInstance().searchSupplier(txtId.getText());
        txtId.setText(String.valueOf(supplier.getId()));
        txtName.setText(supplier.getName());
        txtEmail.setText(supplier.getEmail());
        txtCompany.setText(supplier.getCompany());
    }

    public void btnDeleteSupplierOnAction(ActionEvent actionEvent) {
        boolean b = SupplierController.getInstance().deleteSupplier(txtId.getText());
        if (b){
            System.out.println("Supplier Added");
            loadSupplierTable();
            clearText();
        }else {
            System.out.println("Supplier Not Added");
        }
    }

        private void clearText(){
        txtId.setText(null);
        txtName.setText(null);
        txtEmail.setText(null);
        txtCompany.setText(null);
    }

}
