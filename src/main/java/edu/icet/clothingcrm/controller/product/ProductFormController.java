package edu.icet.clothingcrm.controller.product;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.icet.clothingcrm.controller.category.CategoryController;
import edu.icet.clothingcrm.controller.supplier.SupplierController;
import edu.icet.clothingcrm.db.DBConnection;
import edu.icet.clothingcrm.dto.Category;
import edu.icet.clothingcrm.dto.Product;
import edu.icet.clothingcrm.dto.Supplier;
import edu.icet.clothingcrm.dto.tm.ProductTable;
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

    public TableView tblProductTable;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colSize;
    public TableColumn colPrice;
    public TableColumn colQuantityOnHand;
    public TableColumn colSupplierId;
    public TableColumn colCategoryId;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQuantityOnHand.setCellValueFactory(new PropertyValueFactory<>("quantityOnHand"));
        colCategoryId.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));

        loadSupplierIds();
        loadCategoryIds();
        loadProductTable();
    }

    private void loadProductTable() {
        ObservableList<Product> tableData = FXCollections.observableArrayList();

        List<Product> productList = ProductController.getInstance().loadProduct();

        productList.forEach(product -> {
            ProductTable productTable = new ProductTable(
                    product.getId(),
                    product.getName(),
                    product.getSize(),
                    product.getPrice(),
                    product.getQuantityOnHand(),
                    product.getCategoryId(),
                    product.getSupplierId()
            );
            tableData.add(productTable);
        });
        tblProductTable.setItems(tableData);
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
                Integer.parseInt(String.valueOf(cmbCategoryId.getValue())),
                Integer.parseInt(String.valueOf(cmbSupplierId.getValue()))
        );
        System.out.println(product);

        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement("INSERT INTO product VALUES (?,?,?,?,?,?,?)");
            psTm.setInt(1,product.getId());
            psTm.setString(2,product.getName());
            psTm.setString(3,product.getSize());
            psTm.setString(4,product.getPrice());
            psTm.setString(5,product.getQuantityOnHand());
            psTm.setInt(6,product.getCategoryId());
            psTm.setInt(7,product.getSupplierId());
            psTm.execute();

            loadProductTable();
            clearText();

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSearchProductOnAction(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM product where id='" + txtId.getText() + "'");
            while (resultSet.next()){
                Product product = new Product(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getInt(6),
                        resultSet.getInt(7)
                );
                System.out.println(product);

                txtId.setText(String.valueOf(product.getId()));
                txtName.setText(product.getName());
                txtSize.setText(product.getSize());
                txtPrice.setText(product.getPrice());
                txtQuantityOnHand.setText(product.getQuantityOnHand());
                cmbCategoryId.setValue(product.getCategoryId());
                cmbSupplierId.setValue(product.getSupplierId());
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnDeleteProductOnAction(ActionEvent actionEvent) {
        try {
            boolean execute = DBConnection.getInstance().getConnection().createStatement().execute("DELETE FROM product where id='" + txtId.getText() + "'");

            //loadEmployees();
            loadProductTable();
            clearText();

            if(execute){
                System.out.println("Employee not deleted");
            }else {
                System.out.println("Employee deleted");
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearText(){
        txtId.setText(null);
        txtName.setText(null);
        txtSize.setText(null);
        txtPrice.setText(null);
        txtQuantityOnHand.setText(null);
        cmbCategoryId.setValue(null);
        cmbSupplierId.setValue(null);
    }

}
