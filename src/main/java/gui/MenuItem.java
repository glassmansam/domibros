package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import logic.DatabaseAPI;
import logic.Type;

import java.io.IOException;
import java.sql.SQLException;

public class MenuItem extends VBox {

    private final String name;
    private final double price;
    private final String image;
    private final Type type;
    private final int id;
    private final String[] toppings;
    private boolean isVegetarian = false;

    @FXML
    private ImageView productImage;

    @FXML
    private Label productName;

    @FXML
    private Label itemPrice;

    @FXML
    private FlowPane toppingsContainer;

    public MenuItem(String name, double price, String image, Type type, int id) throws IOException {
        this(name, price, image, new String[0], type, id);
    }

    //we dont use the toppings parameter so idk if its worth using
    public MenuItem(String name, double price, String image, String[] toppings, Type type, int id) throws IOException {
        super();
        this.id = id;
        this.type = type;
        this.name = name;
        this.price = price;
        this.image = image;
        this.toppings = toppings;

        LoaderFXML.loadComponent(this, "/fxml/menuitem.fxml");
    }

    @FXML
    void initialize() {
        try {
            productImage.setImage(new Image(image));

            if (type == Type.PIZZA) {
                //TODO: indicate vegetarian status
                for (String topping : DatabaseAPI.getToppings(id))
                    toppingsContainer.getChildren().add(new ToppingLabel(topping));
            } else
                toppingsContainer.setVisible(false);

        } catch (SQLException e) {
            e.printStackTrace();
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

    public void setVegetarian(boolean veg) {
        isVegetarian = veg;
    }
}
