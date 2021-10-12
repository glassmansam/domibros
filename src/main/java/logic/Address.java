package logic;

public class Address {

    private final int addressID;


    private String street, postocde, city;

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

    public void setStreet(String street) {
        this.street = street;
    }

    public void setPostocde(String postocde) {
        this.postocde = postocde;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
