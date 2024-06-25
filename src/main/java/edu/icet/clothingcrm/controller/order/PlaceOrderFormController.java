package edu.icet.clothingcrm.controller.order;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.icet.clothingcrm.bo.BoFactory;
import edu.icet.clothingcrm.bo.custom.OrderBo;
import edu.icet.clothingcrm.controller.category.CategoryController;
import edu.icet.clothingcrm.controller.product.ProductController;
import edu.icet.clothingcrm.util.BoType;
import edu.icet.clothingcrm.util.CrudUtil;
import edu.icet.clothingcrm.db.DBConnection;
import edu.icet.clothingcrm.dto.Category;
import edu.icet.clothingcrm.dto.Order;
import edu.icet.clothingcrm.dto.OrderDetail;
import edu.icet.clothingcrm.dto.Product;
import edu.icet.clothingcrm.dto.tm.CartTable;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaceOrderFormController implements Initializable {
    public Label lblDate;
    public Label lblTime;

    public Label lblOrderId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerEmail;
    public JFXTextField txtQtyCustomer;

    public JFXComboBox cmbCategoryId;
    public Label lblCategoryName;

    public JFXComboBox cmbProductId;
    public Label lblProductName;
    public Label lblProductSize;
    public Label lblProductUnitPrice;
    public Label lblProductQtyOnHand;

    public TableView tblCartTable;
    public TableColumn colCategoryId;
    public TableColumn colProductId;
    public TableColumn colQtyCustomer;
    public TableColumn colUnitPrice;
    public TableColumn colTotal;

    public Label lblNetTotal;


    public OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDER);



    ObservableList<CartTable> cartData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCategoryId.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colQtyCustomer.setCellValueFactory(new PropertyValueFactory<>("qtyCustomer"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        loadCategoryIds();
        generateOrderId();
        loadDateAndTime();

        cmbCategoryId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setCategoryDataForLbl(String.valueOf(newValue));
            loadProductIds(String.valueOf(newValue));
        });

        cmbProductId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            setProductDataForLbl(String.valueOf(newValue));
        });
    }


    private void setCategoryDataForLbl(String categoryId) {
        Category category = CategoryController.getInstance().searchCategory(categoryId);
        lblCategoryName.setText(category.getName());
    }

    private void setProductDataForLbl(String productId) {
        Product product = ProductController.getInstance().searchProduct(productId);
        lblProductName.setText(product.getName());
        lblProductSize.setText(product.getSize());
        lblProductUnitPrice.setText(product.getPrice());
        lblProductQtyOnHand.setText(product.getQuantityOnHand());
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

    private void loadProductIds(String categoryId) {
        List<Product> products = ProductController.getInstance().loadProduct();

        ObservableList<String> ids = FXCollections.observableArrayList();

        products.forEach(product -> {
            if (categoryId.equalsIgnoreCase(String.valueOf(product.getCategoryId()))) {
                ids.add(product.getId());
            }
        });
        cmbProductId.setItems(ids);
    }

    public void btnAddToCartOnAction(ActionEvent actionEvent) {

        String categoryId = String.valueOf(cmbCategoryId.getValue());
        String productId = String.valueOf(cmbProductId.getValue());
        Integer qtyCustomer = Integer.valueOf(txtQtyCustomer.getText());
        Double unitPrice = Double.valueOf(lblProductUnitPrice.getText());
        Double total = (qtyCustomer*unitPrice);

        CartTable cartTable = new CartTable(
                categoryId,
                productId,
                qtyCustomer,
                unitPrice,
                total,
                0.0
        );
        System.out.println(cartTable);

        int qtyStock = Integer.parseInt(lblProductQtyOnHand.getText());
        if(qtyStock<qtyCustomer){
            new Alert(Alert.AlertType.WARNING, "Invalid Quantity").show();
            return;
        }

        cartData.add(cartTable);

        tblCartTable.setItems(cartData);

        calcNetTotal();
    }

    public void calcNetTotal(){
        double ttl = 0;
        for(CartTable cartTable : cartData){
            ttl+=cartTable.getTotal();
        }
        lblNetTotal.setText((ttl)+" /=");
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(simpleDateFormat.format(date));

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime time = LocalTime.now();
            lblTime.setText(
                    time.getHour() + " : " + time.getMinute() + " : " + time.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws RuntimeException {
        try {
            String orderId = lblOrderId.getText();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date orderDate = format.parse(lblDate.getText());
            String customerName = txtCustomerName.getText();
            String customerEmail = txtCustomerEmail.getText();

            List<OrderDetail> orderDetailList = new ArrayList<>();

            for (CartTable cartTable : cartData){
                String oId = lblOrderId.getText();
                String categoryId = String.valueOf(cartTable.getCategoryId());
                String productId = String.valueOf(cartTable.getProductId());
                Integer qtyCustomer = cartTable.getQtyCustomer();
                Double descount = cartTable.getDiscount();
                Double totalCost = cartTable.getTotal();
                //String paymentType

                orderDetailList.add(new OrderDetail(oId,categoryId,productId,qtyCustomer,descount,totalCost));
            }

            Order order = new Order(orderId, orderDate, customerName, customerEmail, orderDetailList);

            boolean isOrderPlace = OrderController.getInstance().placeOrder(order);
            if (isOrderPlace){
                generateOrderId();
                new Alert(Alert.AlertType.INFORMATION,"Order Placed").show();
                clearText();
                generateOrderId();
            }

            System.out.println(order);


        } catch (ParseException | SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void btnClearCartonAction(ActionEvent actionEvent) {
        colCategoryId.setText(null);
        colProductId.setText(null);
        colQtyCustomer.setText(null);
        colUnitPrice.setText(null);
        colTotal.setText(null);
    }

    public void generateOrderId() throws RuntimeException {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT COUNT(*) FROM orders");
            Integer count = 0;
            while (resultSet.next()) {
                count = resultSet.getInt(1);

            }
            if (count == 0) {
                lblOrderId.setText("D001");
            }
            String lastOrderId = "";
            ResultSet resultSet1 = CrudUtil.execute("SELECT id\n" +
                    "FROM orders\n" +
                    "ORDER BY id DESC\n" +
                    "LIMIT 1;");
            if (resultSet1.next()) {
                lastOrderId = resultSet1.getString(1);
                Pattern pattern = Pattern.compile("[A-Za-z](\\d+)");
                Matcher matcher = pattern.matcher(lastOrderId);
                if (matcher.find()) {
                    int number = Integer.parseInt(matcher.group(1));
                    number++;
                    lblOrderId.setText(String.format("D%03d", number));
                } else {
                    new Alert(Alert.AlertType.WARNING, "hello").show();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnCommitOnAction(ActionEvent actionEvent) {
        try {
            System.out.println("commit");
            Connection connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(true);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnRollBackOnAction(ActionEvent actionEvent) {
        try {
            System.out.println("roll back");
            Connection connection = DBConnection.getInstance().getConnection();
            connection.rollback();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearText(){
        lblOrderId.setText(null);
        txtCustomerName.setText(null);
        txtCustomerEmail.setText(null);
        txtQtyCustomer.setText(null);

        cmbCategoryId.setValue(null);
        lblCategoryName.setText(null);

        cmbProductId.setValue(null);
        lblProductName.setText(null);
        lblProductSize.setText(null);
        lblProductUnitPrice.setText(null);
        lblProductQtyOnHand.setText(null);
    }
}
