package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import logic.Customer;

import java.io.IOException;

public class ApplicationController {

    private final Customer customer;

    public ApplicationController(Customer customer) {
        this.customer = customer;
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
    void initialize() throws IOException {
        viewProfileButton.setText(customer.getUsername());

        menuProductContainer.getChildren().add(new MenuItem());
        menuProductContainer.getChildren().add(new MenuItem());
        menuProductContainer.getChildren().add(new MenuItem());
        menuProductContainer.getChildren().add(new MenuItem());
        menuProductContainer.getChildren().add(new MenuItem());
        menuProductContainer.getChildren().add(new MenuItem());
        menuProductContainer.getChildren().add(new MenuItem());
        menuProductContainer.getChildren().add(new MenuItem());
        menuProductContainer.getChildren().add(new MenuItem());
        menuProductContainer.getChildren().add(new MenuItem());
        menuProductContainer.getChildren().add(new MenuItem());
        menuProductContainer.getChildren().add(new MenuItem());
        menuProductContainer.getChildren().add(new MenuItem());
        menuProductContainer.getChildren().add(new MenuItem());
        menuProductContainer.getChildren().add(new MenuItem());
        menuProductContainer.getChildren().add(new MenuItem());
        menuProductContainer.getChildren().add(new MenuItem());
    }

    @FXML
    void makeOrder(ActionEvent event) {

    }

    @FXML
    void openDessertMenu(ActionEvent event) {

    }

    @FXML
    void openDrinksMenu(ActionEvent event) {

    }

    @FXML
    void openPizzaMenu(ActionEvent event) {

    }

    @FXML
    void viewProfile(ActionEvent event) {

    }

}
