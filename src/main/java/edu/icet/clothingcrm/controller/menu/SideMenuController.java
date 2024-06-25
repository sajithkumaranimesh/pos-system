package edu.icet.clothingcrm.controller.menu;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;


public class SideMenuController implements Initializable {
    public BorderPane mainPane;
    public Label lblTime;
    public Label lblDate;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateAndTime();
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

    public void btnDashboardOnAction(ActionEvent actionEvent) {
    }

    public void btnUserOnAction(ActionEvent actionEvent) {
        System.out.println("user");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getView("user-form.fxml");
        mainPane.setCenter(view);
    }

    public void btnCategoryOnAction(ActionEvent actionEvent) {
        System.out.println("category");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getView("category-form.fxml");
        mainPane.setCenter(view);
    }


    public void btnSupplierOnAction(ActionEvent actionEvent) {
        System.out.println("supplier");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getView("supplier-form.fxml");
        mainPane.setCenter(view);
    }

    public void btnProductOnAction(ActionEvent actionEvent) {
        System.out.println("product");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getView("product-form.fxml");
        mainPane.setCenter(view);
    }

    public void btnOrderOnAction(ActionEvent actionEvent) {
        System.out.println("order");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getView("place-order-form.fxml");
        mainPane.setCenter(view);
    }

    public void btnInventoryOnAction(ActionEvent actionEvent) {
    }

    public void btnEmployeeOnAction(ActionEvent actionEvent) {
        System.out.println("employee");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getView("employee-form.fxml");
        mainPane.setCenter(view);
    }

}
