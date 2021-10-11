package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import logic.Customer;
import logic.DatabaseAPI;
import logic.Order;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static logic.Type.*;

public class ApplicationController {

    public static ApplicationController APP = null;

    private final Customer customer;

    public ApplicationController(Customer customer) {
        this.customer = customer;
        APP = this;
    }

    public void addToCart(CartEntry item) {
        ordersList.getChildren().remove(emptyCartHint);

        ordersList.getChildren().add(item);
        double total = Double.parseDouble(orderTotal.getText());
        total += item.price;
        orderTotal.setText(total + "");

        if (item.getType() == PIZZA)
            orderMessage.setText("");
    }

    public void removeFromCart(CartEntry item) {
        ordersList.getChildren().remove(item);
        double total = Double.parseDouble(orderTotal.getText());
        total -= item.price;
        orderTotal.setText(total + "");

        if (ordersList.getChildren().size() == 0) {
            ordersList.getChildren().add(emptyCartHint);
        }
    }

    @FXML
    private AnchorPane container;

    @FXML
    private Button pizzaMenuButton;

    @FXML
    private Button dessertMenuButton;

    @FXML
    private Button drinkMenuButton;

    @FXML
    private FlowPane menuProductContainer;

    @FXML
    private Button viewProfileButton;

    @FXML
    private VBox ordersList;

    @FXML
    private Label orderTotal;

    @FXML
    private Button orderButton;

    @FXML
    private Label orderMessage;

    @FXML
    private Label emptyCartHint;

    @FXML
    void initialize() throws IOException, SQLException {
        viewProfileButton.setText(customer.getUsername());
        openPizzaMenu(new ActionEvent());

    }

    @FXML
    void makeOrder(ActionEvent event) throws SQLException, IOException {
        boolean correctOrder = false;

        CartEntry[] entries = ordersList.getChildren().toArray(new CartEntry[0]);
        for (CartEntry entry : entries) {
            if (entry.getType() == PIZZA) {
                correctOrder = true;
                break;
            }
        }
        if (correctOrder) {
            int order_id = DatabaseAPI.makeOrderAndGetId(customer.getAddress().getAddressID(), customer.getCustomerID());
            for (CartEntry entry : entries) {
                DatabaseAPI.addOrders(entry, order_id);
            }
            //DO ALL ORDER STUFF WITH IT!!
            container.setVisible(true);
            container.getChildren().add(new OrderScreen(new Order(customer, entries)));


        } else {
            orderMessage.setText("You must have at least one pizza per order!");
        }
    }

    @FXML
    void openDessertMenu(ActionEvent event) throws SQLException, IOException {
        menuProductContainer.getChildren().removeAll(menuProductContainer.getChildren());
        ResultSet desserts = DatabaseAPI.getDesserts();
        while (desserts.next()) {
            menuProductContainer.getChildren().add(new MenuItem(desserts.getString("name"), desserts.getDouble("price"), desserts.getString("image"), DESSERT, desserts.getInt("dessert_id")));
        }
    }

    @FXML
    void openDrinksMenu(ActionEvent event) throws SQLException, IOException {
        menuProductContainer.getChildren().removeAll(menuProductContainer.getChildren());
        ResultSet drinks = DatabaseAPI.getDrinks();
        while (drinks.next()) {
            menuProductContainer.getChildren().add(new MenuItem(drinks.getString("name"), drinks.getDouble("price"), drinks.getString("image"), DRINK, drinks.getInt("drink_id")));
        }
    }

    @FXML
    void openPizzaMenu(ActionEvent event) throws SQLException, IOException {
        menuProductContainer.getChildren().removeAll(menuProductContainer.getChildren());
        ResultSet pizzas = DatabaseAPI.getPizzas();
        while (pizzas.next()) {
            MenuItem pizza = new MenuItem(pizzas.getString("name"), pizzas.getDouble("price"), pizzas.getString("image"), DatabaseAPI.getToppings(pizzas.getInt("pizza_id")), PIZZA, pizzas.getInt("pizza_Id"));
            pizza.setVegetarian(pizzas.getBoolean("vegetarian"));
            menuProductContainer.getChildren().add(pizza);
        }
    }

    @FXML
    void viewProfile(ActionEvent event) {

    }

}
