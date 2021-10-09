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

import java.io.IOException;
import java.sql.*;

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

            Statement statement = connection.createStatement();

            //first insert the address since we need the address ID for the customer
            String insertAddress = "INSERT INTO address (street, post_code, city) values " +
                    "('" + street + "','" + postcode + "','" + city + "') ";

            statement.execute(insertAddress);

            //now lets get the latest address ID, which is the auto-incremented value of the last insert query that we just did
            ResultSet rs = statement.executeQuery("SELECT LAST_INSERT_ID()");

            //for some reason it complains if we don't call "rs.next()" before trying to read from the result set
            int address_id = rs.next() ? rs.getInt(1) : 0; //if rs.next() is true, then address_id = rs.getInt(), otherwise address_id = 0

            //insert the customer into the customer database
            String query2 = "INSERT INTO customer (username, password, first_name, last_name, phone_number, address_id) VALUES " +
                    "('" + username + "', '" + password + "', '" + firstname + "', '" + lastname + "', '" + phoneNumber + "', '" + address_id + "')";
            statement.execute(query2);

            //login after registration
            login(event);

        } catch (Exception a) {
            a.printStackTrace();
        }

    }

    @FXML
    void login(ActionEvent event) throws IOException {

        //ideally we use an "API" class that just has SQL methods that lets us do what we gotta do

        String password = passwordField.getText();
        String username = usernameField.getText();

        //very hacky below, maybe better to use a join query but I didn't feel like googling the syntax
        try {
            Statement statement = connection.createStatement();
            String getUser = "SELECT * FROM customer WHERE username = '" + username + "' AND password = '" + password + "'";
            ResultSet results = statement.executeQuery(getUser);

            if (results.next()) {
                int customerID = results.getInt("id");
                String firstName = results.getString("first_name");
                String lastName = results.getString("last_name");
                String phoneNumber = results.getString("phone_number");
                int amountOrdered = results.getInt("amount_ordered");

                int addressID = results.getInt("address_id");

                String getAddress = "SELECT * FROM address WHERE address_id = '" + addressID + "'";
                ResultSet addressResult = statement.executeQuery(getAddress);

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
    }


}

