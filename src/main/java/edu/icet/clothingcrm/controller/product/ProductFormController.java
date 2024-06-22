package edu.icet.clothingcrm.controller.product;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.icet.clothingcrm.bo.BoFactory;
import edu.icet.clothingcrm.bo.custom.ProductBo;
import edu.icet.clothingcrm.controller.category.CategoryController;
import edu.icet.clothingcrm.controller.supplier.SupplierController;
import edu.icet.clothingcrm.dto.Category;
import edu.icet.clothingcrm.dto.Product;
import edu.icet.clothingcrm.dto.Supplier;
import edu.icet.clothingcrm.dto.tm.ProductTable;
import edu.icet.clothingcrm.util.BoType;
import edu.icet.clothingcrm.util.CrudUtil;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public Label lblCategoryName;
    public Label lblSupplierName;
    public Label lblSupplierCompany;
    public Label lblSupplierEmail;

    public Label lblId;

    private ProductBo productBo = BoFactory.getInstance().getBo(BoType.PRODUCT);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQuantityOnHand.setCellValueFactory(new PropertyValueFactory<>("quantityOnHand"));
        colCategoryId.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));

        generateProductId();
        loadSupplierIds();
        loadCategoryIds();
        loadProductTable();

        cmbCategoryId.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue) -> {
            setCategoryDataForLbl(String.valueOf(newValue));
        });

        cmbSupplierId.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue) ->{
            setSupplierDataForLbl(String.valueOf(newValue));
        });
    }

    private void setSupplierDataForLbl(String supplierId) {
        Supplier supplier = SupplierController.getInstance().searchSupplier(supplierId);
        lblSupplierName.setText(supplier.getName());
        lblSupplierCompany.setText(supplier.getCompany());
        lblSupplierEmail.setText(supplier.getEmail());
    }

    private void setCategoryDataForLbl(String categoryId) {
        Category category = CategoryController.getInstance().searchCategory(categoryId);
        lblCategoryName.setText(category.getName());
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

        ObservableList<String> ids = FXCollections.observableArrayList();

        suppliers.forEach(supplier -> {
            ids.add(supplier.getId());
        });
        System.out.println(ids);
        cmbSupplierId.setItems(ids);
    }

    private void loadCategoryIds() {
        List<Category> categories = CategoryController.getInstance().loadCategory();

        ObservableList<String> ids = FXCollections.observableArrayList();

        categories.forEach(category -> {
            ids.add(category.getId());
        });
        System.out.println(ids);
        cmbCategoryId.setItems(ids);
    }

    public void btnAddProductOnAction(ActionEvent actionEvent) {
        Product product = new Product(
                lblId.getText(),
                txtName.getText(),
                txtSize.getText(),
                txtPrice.getText(),
                txtQuantityOnHand.getText(),
                String.valueOf(cmbCategoryId.getValue()),
                String.valueOf(cmbSupplierId.getValue())
        );
        //boolean isProductAdded = ProductController.getInstance().addProduct(product);
        boolean isProductAdded = productBo.saveProduct(product);
        if (isProductAdded) {
            new Alert(Alert.AlertType.ERROR, "Product Not Added").show();
        } else {
            new Alert(Alert.AlertType.CONFIRMATION, "Product Added").show();
        }
        loadProductTable();
        generateProductId();
        clearText();

    }

    public void btnSearchProductOnAction(ActionEvent actionEvent) {
        //Product product = ProductController.getInstance().searchProduct(txtId.getText());
        Product product = productBo.searchProductById(txtId.getText());
        txtId.setText(String.valueOf(product.getId()));
        txtName.setText(product.getName());
        txtSize.setText(product.getSize());
        txtPrice.setText(product.getPrice());
        txtQuantityOnHand.setText(product.getQuantityOnHand());
        cmbCategoryId.setValue(product.getCategoryId());
        cmbSupplierId.setValue(product.getSupplierId());

    }

    public void btnDeleteProductOnAction(ActionEvent actionEvent) {
        //boolean isProductDeleted = ProductController.getInstance().deleteProduct(txtId.getText());
        boolean isProductDeleted = productBo.deleteProductById(txtId.getText());
        if (isProductDeleted){
            System.out.println("Product Added");
            loadProductTable();
            clearText();
        }else {
            System.out.println("Product Not Added");
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

        lblCategoryName.setText(null);
        lblSupplierName.setText(null);
        lblSupplierCompany.setText(null);
        lblSupplierEmail.setText(null);
    }

    public void generateProductId() throws RuntimeException {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT COUNT(*) FROM product");
            Integer count = 0;
            while (resultSet.next()) {
                count = resultSet.getInt(1);

            }
            if (count == 0) {
                lblId.setText("E001");
            }
            String lastOrderId = "";
            ResultSet resultSet1 = CrudUtil.execute("SELECT id\n" +
                    "FROM product\n" +
                    "ORDER BY id DESC\n" +
                    "LIMIT 1;");
            if (resultSet1.next()) {
                lastOrderId = resultSet1.getString(1);
                Pattern pattern = Pattern.compile("[A-Za-z](\\d+)");
                Matcher matcher = pattern.matcher(lastOrderId);
                if (matcher.find()) {
                    int number = Integer.parseInt(matcher.group(1));
                    number++;
                    lblId.setText(String.format("P%03d", number));
                } else {
                    new Alert(Alert.AlertType.WARNING, "hello").show();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
