package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class CartEntry extends HBox {

    private String name;
    private String price;

    public CartEntry(String name, String price) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menuitem.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        loader.load();

        this.name = name;
        this.price = price;
    }

    @FXML
    private Label removeButton;

    @FXML
    private Label productName;

    @FXML
    private Label productPrice;

    @FXML
    void removeFromCart(MouseEvent event) {

    }

    @FXML
    void initialize() {

    }


}
