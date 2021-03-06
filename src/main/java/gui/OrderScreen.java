package gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import logic.DatabaseAPI;
import logic.Order;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

public class OrderScreen extends AnchorPane {

    private final Timer cancelTimer = new Timer();
    private final Timer estDeliverTimer = new Timer();

    private final Order order;

    private boolean canCancel = true;

    private long estimatedTime = System.currentTimeMillis() + 15 * 60 * 10000;

    public OrderScreen(Order order) throws IOException, SQLException {
        this.order = order;
        this.estimatedTime = DatabaseAPI.getDeliveryTime() - System.currentTimeMillis();
        LoaderFXML.loadComponent(this, "/fxml/orderscreen.fxml");
    }

    @FXML
    void initialize() throws SQLException {
deliveryDriverName.setText(DatabaseAPI.getDriverName());
        cancelTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                canCancel = false;
                int order_id = 0;
                try {
                    order_id = DatabaseAPI.makeOrderAndGetId(order.getCustomer().getAddress().getAddressID(), order.getCustomer().getCustomerID());
                    for (CartEntry entry : order.getCart()) {
                        DatabaseAPI.addOrders(entry, order_id);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Platform.runLater(() -> {
                    orderStatus.setText("OUT FOR DELIVERY");
                });

            }
        }, 5 * 60 * 10000); //5 min


        estDeliverTimer.schedule(new TimerTask() {
            int s = 0;
            int m = 0;
            @Override
            public void run() {
                Platform.runLater(() -> {
                    s += 1;
                    int minutes = (int) ((estimatedTime - m * 60 * 1000) / (60 * 1000));
                    int seconds = (int) (((estimatedTime - 1000 * s) / 1000) % 60);
                    String str = String.format("%d:%02d", minutes, seconds);
                    estimatedDeliveryTime.setText(str);
                    m += s % 60 == 0 ? 1 : 0;
                });
            }
        }, 0, 1000);


        firstNameLabel.setText(order.getCustomer().getFirstName());
        lastNameLabel.setText(order.getCustomer().getLastName());
        streetLabel.setText(order.getAddress().getStreet());
        postcodeLabel.setText(order.getAddress().getPostocde());
        cityLabel.setText(order.getAddress().getCity());
        phoneNumberLabel.setText(order.getCustomer().getPhoneNumber());

        orderStatus.setText("IN PROCESS");
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
        if (canCancel) {
            //execute cancel code
            orderStatus.setText("CANCELLED");
            deliveryDriverName.setText("N/A");
            estimatedDeliveryTime.setText("N/A");
        } else {
            System.out.println("Can't cancel :///");
        }
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
    private Button cancelOrderButton;

}
