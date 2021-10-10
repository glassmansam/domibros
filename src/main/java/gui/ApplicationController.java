package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import logic.Customer;
import logic.DatabaseAPI;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ApplicationController {

    public static ApplicationController APP = null;

    private final Customer customer;

    public ApplicationController(Customer customer) {
        this.customer = customer;
        APP = this;
    }

    public void addToCart(CartEntry item) {
        ordersList.getChildren().add(item);
        double total = Double.parseDouble(orderTotal.getText());
        total += item.price;
        orderTotal.setText(total + "");
    }

    public void removeFromCart(CartEntry item) {
        ordersList.getChildren().remove(item);
        double total = Double.parseDouble(orderTotal.getText());
        total -= item.price;
        orderTotal.setText(total + "");
    }

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
    void initialize() throws IOException, SQLException {
        viewProfileButton.setText(customer.getUsername());
        openPizzaMenu(new ActionEvent());

    }

    @FXML
    void makeOrder(ActionEvent event) {

    }

    @FXML
    void openDessertMenu(ActionEvent event) throws SQLException, IOException {
menuProductContainer.getChildren().removeAll(menuProductContainer.getChildren());
        ResultSet desserts = DatabaseAPI.getDesserts();
        while(desserts.next()){
            menuProductContainer.getChildren().add(new MenuItem(desserts.getString("name"),desserts.getDouble("price"),desserts.getString("image")));
        }
    }

    @FXML
    void openDrinksMenu(ActionEvent event) throws SQLException, IOException {
        menuProductContainer.getChildren().removeAll(menuProductContainer.getChildren());
        ResultSet drinks = DatabaseAPI.getDrinks();
        while(drinks.next()){
            menuProductContainer.getChildren().add(new MenuItem(drinks.getString("name"),drinks.getDouble("price"),drinks.getString("image")));
        }
    }

    @FXML
    void openPizzaMenu(ActionEvent event) throws SQLException, IOException {
        menuProductContainer.getChildren().removeAll(menuProductContainer.getChildren());
        ResultSet pizzas = DatabaseAPI.getPizzas();
        while(pizzas.next()){
            menuProductContainer.getChildren().add(new MenuItem(pizzas.getString("name"),pizzas.getDouble("price"),pizzas.getString("image"), DatabaseAPI.getToppings(pizzas.getInt("pizza_id"))));
        }
    }

    @FXML
    void viewProfile(ActionEvent event) {

    }

}
