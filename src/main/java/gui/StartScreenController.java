package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.Address;
import logic.Customer;
import logic.DatabaseAPI;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StartScreenController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField streetField;

    @FXML
    private TextField postcodeField;

    @FXML
    private TextField cityField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private Button enterButton;

    @FXML
    private Label addressMsg;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginMsg;

    Connection connection = null;

    @FXML
    void initialize() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/domibros?user=root&password=root&serverTimezone=Europe/Rome");
        } catch (Exception e) {
            loginMsg.setText("Unable to connect to database");
            e.printStackTrace();
        }

    }

    @FXML
    void enter(ActionEvent event) {
        try {
            String firstname = firstNameField.getText();
            String lastname = lastNameField.getText();
            String phoneNumber = phoneNumberField.getText();

            String street = streetField.getText();
            String postcode = postcodeField.getText();
            String city = cityField.getText();

            //yeah to register you need to fill out the login form lol.
            //Probably should find a better solution for this eventually
            String password = passwordField.getText();
            String username = usernameField.getText();

            DatabaseAPI.addUser(firstname,lastname,phoneNumber,street,postcode,city,password,username);

            login(event);

        } catch (Exception a) {
            a.printStackTrace();
        }

    }

    @FXML
    void login(ActionEvent event) throws IOException {

        String password = passwordField.getText();
        String username = usernameField.getText();

        try {
            ResultSet results = DatabaseAPI.getUser(username,password);

            if (results.next()) {
                int customerID = results.getInt("id");
                String firstName = results.getString("first_name");
                String lastName = results.getString("last_name");
                String phoneNumber = results.getString("phone_number");
                int amountOrdered = results.getInt("amount_ordered");

                int addressID = results.getInt("address_id");

                ResultSet addressResult = DatabaseAPI.getAddress(addressID);

                if (addressResult.next()) {
                    String street = addressResult.getString("street");
                    String postCode = addressResult.getString("post_code");
                    String city = addressResult.getString("city");

                    Address address = new Address(addressID, street, postCode, city);
                    Customer customer = new Customer(customerID, username, firstName, lastName, phoneNumber, amountOrdered, address);

                    goToApplication(customer);
                } else {
                    loginMsg.setText("No address for this user");
                }

            } else {
                loginMsg.setText("No user with this input");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void goToApplication(Customer customer) throws IOException {
        ApplicationController appController = new ApplicationController(customer);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/applicationscreen.fxml"));
        loader.setController(appController);

        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setMinWidth(875);
        stage.setMinHeight(620);
    }


}

