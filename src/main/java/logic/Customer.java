package logic;

public class Customer {

    private final int customerID;
    private final String username, firstName, lastName, phoneNumber;
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

    public void incrementOrderAmount() {
        this.amountOrdered++;
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
}
