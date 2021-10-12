package logic;

import java.sql.SQLException;

public class Customer {

    private final int customerID;


    private String username, firstName, lastName, phoneNumber;
    private final Address address;
    private int amountOrdered;

    public Customer(int customerID, String username, String firstName, String lastName, String phoneNumber, int amountOrdered, Address address) {
        this.customerID = customerID;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.amountOrdered = amountOrdered;
    }

    public void incrementOrderAmount() throws SQLException {
       DatabaseAPI.increase_Number(this.customerID);
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public int getAmountOrdered() {
        return amountOrdered;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
