package logic;

import gui.MenuItem;

import java.io.IOException;

public class Address {

    private final int addressID;
    private final String street, postocde, city;

    public Address(int addressID, String street, String postcode, String city) {
        this.addressID = addressID;
        this.street = street;
        this.postocde = postcode;
        this.city = city;
    }

    public int getAddressID() {
        return addressID;
    }

    public String getStreet() {
        return street;
    }

    public String getPostocde() {
        return postocde;
    }

    public String getCity() {
        return city;
    }
}
