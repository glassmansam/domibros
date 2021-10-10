package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MenuItem extends VBox {

    @FXML
    private ImageView productImage;

    @FXML
    private Label productName;

    @FXML
    private Label itemPrice;

    public MenuItem() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menuitem.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        loader.load();
    }

    @FXML
    void initialize() {
        productImage.setImage(new Image("/images/pizza_caprese.png"));
    }

    @FXML
    void addToCart(MouseEvent event) {

    }
}
