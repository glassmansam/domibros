package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import logic.Type;

import java.io.IOException;

public class MenuItem extends VBox {

    private final String name;
    private final double price;
    private final String image;
    private final Type type;
    private final int id;
    String[] toppings;

    @FXML
    private ImageView productImage;

    @FXML
    private Label productName;

    @FXML
    private Label itemPrice;

    public MenuItem(String name, double price, String image, Type type, int id) throws IOException {
        this(name, price, image, new String[0], type, id);
    }

    public MenuItem(String name, double price, String image, String[] toppings, Type type, int id) throws IOException {
        super();
        this.id = id;
        this.type = type;
        this.name = name;
        this.price = price;
        this.image = image;
        this.toppings = toppings;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menuitem.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        loader.load();
    }

    @FXML
    void initialize() {
        try {
            productImage.setImage(new Image(image));
        } catch (Exception e) {
            productImage.setImage(new Image("/images/pizzas/pizza_caprese.png"));
        }

        productName.setText(name);
        itemPrice.setText(price + "");
    }

    @FXML
    void addToCart(MouseEvent event) throws IOException {
        CartEntry entry = new CartEntry(name, price, type, id);
        ApplicationController.APP.addToCart(entry);
    }
}
