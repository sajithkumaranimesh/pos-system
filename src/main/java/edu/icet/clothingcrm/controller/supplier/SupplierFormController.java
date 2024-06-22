package edu.icet.clothingcrm.controller.supplier;

import com.jfoenix.controls.JFXTextField;
import edu.icet.clothingcrm.bo.BoFactory;
import edu.icet.clothingcrm.bo.custom.SupplierBo;
import edu.icet.clothingcrm.dto.Employee;
import edu.icet.clothingcrm.dto.Supplier;
import edu.icet.clothingcrm.dto.tm.SupplierTable;
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

    public SupplierBo supplierBo = BoFactory.getInstance().getBo(BoType.SUPPLIER);
    public Label lblId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));

        generateSupplierId();
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
                lblId.getText(),
                txtName.getText(),
                txtEmail.getText(),
                txtCompany.getText()
        );

        //boolean isSupplierAdded = SupplierController.getInstance().addSupplier(supplier);
        boolean isSupplierAdded = supplierBo.saveSupplier(supplier);
        if (isSupplierAdded) {
            new Alert(Alert.AlertType.ERROR, "Supplier Not Added").show();
        } else {
            new Alert(Alert.AlertType.CONFIRMATION, "Supplier Added").show();
        }
        loadSupplierTable();
        clearText();
        generateSupplierId();

    }

    public void btnSearchSupplierOnAction(ActionEvent actionEvent) {
        //Supplier supplier = SupplierController.getInstance().searchSupplier(txtId.getText());
        Supplier supplier = supplierBo.searchSupplierById(txtId.getText());
        txtId.setText(String.valueOf(supplier.getId()));
        txtName.setText(supplier.getName());
        txtEmail.setText(supplier.getEmail());
        txtCompany.setText(supplier.getCompany());
    }

    public void btnDeleteSupplierOnAction(ActionEvent actionEvent) {
        //boolean b = SupplierController.getInstance().deleteSupplier(txtId.getText());
        boolean isDeleted = supplierBo.deleteSupplierById(txtId.getText());
        if (isDeleted){
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

    public void generateSupplierId() throws RuntimeException {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT COUNT(*) FROM supplier");
            Integer count = 0;
            while (resultSet.next()) {
                count = resultSet.getInt(1);

            }
            if (count == 0) {
                lblId.setText("S001");
            }
            String lastOrderId = "";
            ResultSet resultSet1 = CrudUtil.execute("SELECT id\n" +
                    "FROM supplier\n" +
                    "ORDER BY id DESC\n" +
                    "LIMIT 1;");
            if (resultSet1.next()) {
                lastOrderId = resultSet1.getString(1);
                Pattern pattern = Pattern.compile("[A-Za-z](\\d+)");
                Matcher matcher = pattern.matcher(lastOrderId);
                if (matcher.find()) {
                    int number = Integer.parseInt(matcher.group(1));
                    number++;
                    lblId.setText(String.format("S%03d", number));
                } else {
                    new Alert(Alert.AlertType.WARNING, "hello").show();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
