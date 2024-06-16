package edu.icet.clothingcrm.controller;

import com.jfoenix.controls.JFXTextField;
import edu.icet.clothingcrm.db.DBConnection;
import edu.icet.clothingcrm.dto.Product;
import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductFormController {
    public JFXTextField txtName;
    public JFXTextField txtPrice;

    public void btnAddProductOnAction(ActionEvent actionEvent) {
        Product product = new Product(
                txtName.getText(),
                txtPrice.getText()
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
}
