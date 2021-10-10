package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class CartEntry extends HBox {

    public final String name;
    public double price;

    public CartEntry(String name, double price) throws IOException {
        super();

        this.name = name;
        this.price = price;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/cartentry.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        loader.load();
    }

    @FXML
    private Label removeButton;

    @FXML
    private Label productName;

    @FXML
    private Label productPrice;

    @FXML
    void removeFromCart(MouseEvent event) {
        ApplicationController.APP.removeFromCart(this);
    }

    @FXML
    void initialize() {
        productName.setText(name);
        productPrice.setText(price + "");
    }


}
