package edu.icet.clothingcrm.controller.category;

import com.jfoenix.controls.JFXTextField;
import edu.icet.clothingcrm.bo.BoFactory;
import edu.icet.clothingcrm.bo.custom.CategoryBo;
import edu.icet.clothingcrm.dto.Category;
import edu.icet.clothingcrm.dto.tm.CategoryTable;
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

public class CategoryFormController implements Initializable {
    public Label lblId;
    public JFXTextField txtName;
    public JFXTextField txtId;

    public TableView tblCategoryTable;
    public TableColumn colCategoryId;
    public TableColumn colName;

    private CategoryBo categoryBo = BoFactory.getInstance().getBo(BoType.CATEGORY);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCategoryId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));

        generateCategoryId();
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
                lblId.getText(),
                txtName.getText()
        );
        boolean isCategoryAdded = categoryBo.saveCategory(category);
        if (isCategoryAdded) {
            new Alert(Alert.AlertType.ERROR, "Category not Added").show();
        } else {
            new Alert(Alert.AlertType.CONFIRMATION, "Category Added").show();
        }
        loadCategoryTable();
        clearText();
        generateCategoryId();
    }

    public void btnSearchCtegoryOnAction(ActionEvent actionEvent) {
        Category category = categoryBo.searchCategoryById(txtId.getText());
        lblId.setText(String.valueOf(category.getId()));
        txtName.setText(category.getName());
        System.out.println(category);
    }

    public void btnDeleteCtegoryOnAction(ActionEvent actionEvent) {
        boolean isDeleted = categoryBo.deleteCategoryById(txtId.getText());
        if (isDeleted) {
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

    public void generateCategoryId() throws RuntimeException {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT COUNT(*) FROM category");
            Integer count = 0;
            while (resultSet.next()) {
                count = resultSet.getInt(1);

            }
            if (count == 0) {
                lblId.setText("C001");
            }
            String lastOrderId = "";
            ResultSet resultSet1 = CrudUtil.execute("SELECT id\n" +
                    "FROM category\n" +
                    "ORDER BY id DESC\n" +
                    "LIMIT 1;");
            if (resultSet1.next()) {
                lastOrderId = resultSet1.getString(1);
                Pattern pattern = Pattern.compile("[A-Za-z](\\d+)");
                Matcher matcher = pattern.matcher(lastOrderId);
                if (matcher.find()) {
                    int number = Integer.parseInt(matcher.group(1));
                    number++;
                    lblId.setText(String.format("C%03d", number));
                } else {
                    new Alert(Alert.AlertType.WARNING, "hello").show();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
