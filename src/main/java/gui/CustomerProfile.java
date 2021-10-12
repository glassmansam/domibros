package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import logic.Customer;

import java.io.IOException;

public class CustomerProfile extends AnchorPane {

    private final Customer customer;

    public CustomerProfile(Customer customer) throws IOException {
        super();
        this.customer = customer;
        LoaderFXML.loadComponent(this, "/fxml/customerprofile.fxml");
    }

    @FXML
    void initialize() {
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
    }

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
    void updateCustomer(ActionEvent event) {

    }

}
