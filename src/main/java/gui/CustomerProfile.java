package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import logic.Customer;
import logic.DatabaseAPI;

import java.io.IOException;
import java.sql.SQLException;

public class CustomerProfile extends AnchorPane {

    private final Customer customer;

    public CustomerProfile(Customer customer) throws IOException {
        super();
        this.customer = customer;
        LoaderFXML.loadComponent(this, "/fxml/customerprofile.fxml");
    }

    @FXML
    void initialize() throws SQLException {
        AnchorPane.setTopAnchor(this, 0.0);
        AnchorPane.setBottomAnchor(this, 0.0);
        AnchorPane.setLeftAnchor(this, 0.0);
        AnchorPane.setRightAnchor(this, 0.0);

        customerNameLabel.setText(customer.getFirstName());
        firstNameInput.setText(customer.getFirstName());
        lastNameInput.setText(customer.getLastName());
        streetInput.setText(customer.getAddress().getStreet());
        postcodeInput.setText(customer.getAddress().getPostocde());
        cityInput.setText(customer.getAddress().getCity());
        phoneNumberInput.setText(customer.getPhoneNumber());

        numOrderUntilCoupon.setText((customer.getAmountOrdered() % 10) + "");

        for (String code : DatabaseAPI.getCodes(customer.getCustomerID())) {
            TextField field = new TextField(code);
            field.setEditable(false);
            couponContainer.getChildren().add(field);
        }
    }

    @FXML
    private Label numOrderUntilCoupon;

    @FXML
    private VBox couponContainer;

    @FXML
    private Label customerNameLabel;

    @FXML
    private TextField firstNameInput;

    @FXML
    private TextField lastNameInput;

    @FXML
    private TextField streetInput;

    @FXML
    private TextField postcodeInput;

    @FXML
    private TextField cityInput;

    @FXML
    private TextField phoneNumberInput;

    @FXML
    private Button updateButton;

    @FXML
    private VBox orderHistoryContainer;

    @FXML
    void updateCustomer(ActionEvent event) throws SQLException {
        customer.setFirstName(firstNameInput.getText());
        customer.setLastName(lastNameInput.getText());
        customer.setPhoneNumber(phoneNumberInput.getText());

        customer.getAddress().setCity(cityInput.getText());
        customer.getAddress().setStreet(streetInput.getText());
        customer.getAddress().setPostocde(postcodeInput.getText());

        DatabaseAPI.updateCustomerInfo(customer);
    }

}
