package edu.icet.clothingcrm.controller.menu;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.net.URL;

public class FxmlLoader {
    private Pane view;

    public Pane getView(String fileName){
        try {
            URL fileUrl = SideMenuController.class.getResource("/view/" + fileName);
            if (fileUrl == null){
                throw new java.io.FileNotFoundException("FXML file can't be found");
            }
            view = new FXMLLoader().load(fileUrl);
        }catch (Exception e){
            System.out.println("No page " + fileName + ". Please check FxmlLoader.");
            e.printStackTrace();
        }
        return view;
    }
}
