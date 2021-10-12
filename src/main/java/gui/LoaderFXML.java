package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import java.io.IOException;

public class LoaderFXML {

    public static void loadComponent(Object controllerAndRoot, String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(LoaderFXML.class.getResource(fxmlPath));
        loader.setController(controllerAndRoot);
        loader.setRoot(controllerAndRoot);
        loader.load();
    }

    public static void loadComponent(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(LoaderFXML.class.getResource(fxmlPath));
        //loader.setController(controllerAndRoot);
        loader.setRoot(new Label());
        loader.load();
    }
}
