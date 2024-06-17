package edu.icet.clothingcrm.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.icet.clothingcrm.db.DBConnection;
import edu.icet.clothingcrm.dto.Category;
import edu.icet.clothingcrm.dto.Product;
import edu.icet.clothingcrm.dto.Supplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProductFormController implements Initializable {
    public Label lblTime;
    public Label lblDate;

    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtSize;
    public JFXTextField txtPrice;
    public JFXTextField txtQuantityOnHand;
    public JFXComboBox cmbCategoryId;
    public JFXComboBox cmbSupplierId;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadSupplierIds();
        loadCategoryIds();
    }

    private void loadSupplierIds() {
        List<Supplier> suppliers = SupplierController.getInstance().loadSupplier();

        ObservableList<Integer> ids = FXCollections.observableArrayList();

        suppliers.forEach(supplier -> {
            ids.add(supplier.getId());
        });
        System.out.println(ids);
        cmbSupplierId.setItems(ids);
    }

    private void loadCategoryIds() {
        List<Category> categories = CategoryController.getInstance().loadCategory();

        ObservableList<Integer> ids = FXCollections.observableArrayList();

        categories.forEach(category -> {
            ids.add(category.getId());
        });
        System.out.println(ids);
        cmbCategoryId.setItems(ids);
    }

    public void btnAddProductOnAction(ActionEvent actionEvent) {
        Product product = new Product(
                Integer.parseInt(txtId.getText()),
                txtName.getText(),
                txtSize.getText(),
                txtPrice.getText(),
                txtQuantityOnHand.getText(),
                Integer.parseInt((String) cmbCategoryId.getValue()),
                Integer.parseInt((String) cmbSupplierId.getValue())
        );
        System.out.println(product);

        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement("INSERT INTO product VALUES (?,?)");
            psTm.setString(1,product.getName());
            psTm.setString(2,product.getPrice());
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSearchProductOnAction(ActionEvent actionEvent) {
    }

    public void btnDeleteProductOnAction(ActionEvent actionEvent) {
    }


}
