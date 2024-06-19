package edu.icet.clothingcrm.controller.category;

import com.jfoenix.controls.JFXTextField;
import edu.icet.clothingcrm.db.DBConnection;
import edu.icet.clothingcrm.dto.Category;
import edu.icet.clothingcrm.dto.tm.CategoryTable;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class CategoryFormController implements Initializable {
    public Label lblTime;
    public Label lblDate;

    public JFXTextField txtId;
    public JFXTextField txtName;

    public TableView tblCategoryTable;
    public TableColumn colCategoryId;
    public TableColumn colName;
    public TableColumn colAction;

    //private List<Category> categoryList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCategoryId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));

        loadCategoryTable();
    }

    private void loadCategoryTable() {
        ObservableList<Category> tableData = FXCollections.observableArrayList();
        List<Category> categoryList = CategoryController.getInstance().loadCategory();
        categoryList.forEach(category -> {
            CategoryTable categoryTable = new CategoryTable(
                    category.getId(),
                    category.getName()
            );
            tableData.add(categoryTable);
        });
        tblCategoryTable.setItems(tableData);
    }


    public void btnAddCategoryOnAction(ActionEvent actionEvent) {
        Category category = new Category(
                Integer.parseInt(txtId.getText()),
                txtName.getText()
        );

        boolean b = CategoryController.getInstance().addCategory(category);
        if (b) {
            new Alert(Alert.AlertType.ERROR, "Category not Added").show();
        } else {
            new Alert(Alert.AlertType.CONFIRMATION, "Category Added").show();
        }
        loadCategoryTable();
    }

    public void btnSearchCtegoryOnAction(ActionEvent actionEvent) {
        Category category = CategoryController.getInstance().searchCategory(txtId.getText());
        txtId.setText(String.valueOf(category.getId()));
        txtName.setText(category.getName());
        System.out.println(category);
    }

    public void btnDeleteCtegoryOnAction(ActionEvent actionEvent) {

        boolean execute = CategoryController.getInstance().deleteCategory(txtId.getText());
        if (execute) {
            System.out.println("Category  deleted");
            loadCategoryTable();
            clearText();
        } else {
            System.out.println("Category not deleted");
        }

    }

    private void clearText() {
        txtId.setText(null);
        txtName.setText(null);
    }


}
