package edu.icet.clothingcrm.controller;

import com.jfoenix.controls.JFXTextField;
import edu.icet.clothingcrm.db.DBConnection;
import edu.icet.clothingcrm.dto.Category;
import edu.icet.clothingcrm.dto.tm.CategoryTable;
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
        System.out.println(category);

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement("INSERT INTO category VALUES (?,?)");
            psTm.setInt(1,category.getId());
            psTm.setString(2, category.getName());
            psTm.execute();

            //loadCategory();
            loadCategoryTable();
            clearText();

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSearchCtegoryOnAction(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM category where id='" + txtId.getText() + "'");
            while (resultSet.next()){
                Category category = new Category(
                        resultSet.getInt(1),
                        resultSet.getString(2)
                );
                System.out.println(category);

                txtId.setText(String.valueOf(category.getId()));
                txtName.setText(category.getName());
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnDeleteCtegoryOnAction(ActionEvent actionEvent) {
        try {
            boolean execute = DBConnection.getInstance().getConnection().createStatement().execute("DELETE FROM category where id='" + txtId.getText() + "'");

            //loadCategory();
            loadCategoryTable();
            clearText();

            if(execute){
                System.out.println("Category not deleted");
            }else {
                System.out.println("Category deleted");
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearText(){
        txtId.setText(null);
        txtName.setText(null);
    }


}
