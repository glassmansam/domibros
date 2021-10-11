package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import logic.Order;

import java.io.IOException;

public class OrderScreen extends AnchorPane {

    private final Order order;

    public OrderScreen(Order order) throws IOException {
        this.order = order;
        LoaderFXML.loadComponent(this, "/fxml/orderscreen.fxml");
    }

    @FXML
    void initialize() {
        firstNameLabel.setText(order.getCustomer().getFirstName());
        lastNameLabel.setText(order.getCustomer().getLastName());
        streetLabel.setText(order.getAddress().getStreet());
        postcodeLabel.setText(order.getAddress().getPostocde());
        cityLabel.setText(order.getAddress().getCity());
        phoneNumberLabel.setText(order.getCustomer().getPhoneNumber());

        orderStatus.setText(order.getStatus().toString());
        numOrderUntilCoupon.setText((order.getCustomer().getAmountOrdered() % 10) + "");

        for (CartEntry cartEntry : order.getCart()) {
            cartContainer.getChildren().add(cartEntry);
            cartEntry.setDisable(true);
        }

        AnchorPane.setTopAnchor(this, 0.0);
        AnchorPane.setBottomAnchor(this, 0.0);
        AnchorPane.setLeftAnchor(this, 0.0);
        AnchorPane.setRightAnchor(this, 0.0);
    }

    @FXML
    void cancelOrder(ActionEvent event) {
        //check if time elapsed < 5 min
    }

    @FXML
    private TextField firstNameLabel;

    @FXML
    private TextField lastNameLabel;

    @FXML
    private TextField streetLabel;

    @FXML
    private TextField postcodeLabel;

    @FXML
    private TextField cityLabel;

    @FXML
    private TextField phoneNumberLabel;

    @FXML
    private VBox cartContainer;

    @FXML
    private Label orderStatus;

    @FXML
    private Label estimatedDeliveryTime;

    @FXML
    private Label deliveryDriverName;

    @FXML
    private Label numOrderUntilCoupon;

    @FXML
    private VBox orderHistoryContainer;

    @FXML
    private Button cancelOrderButton;

}
