package logic;

import java.sql.*;

public class DatabaseAPI {
    static Connection connection = null;
    static Statement statement=null;
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            statement = connection.createStatement();
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/domibros?user=root&password=root&serverTimezone=Europe/Rome");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ResultSet getAddress(int id) throws SQLException {
        String getAddress = "SELECT * FROM address WHERE address_id = '" + id + "'";
        return  statement.executeQuery(getAddress);

    }
    public static ResultSet getUser(String username, String password) throws SQLException {
        String getUser = "SELECT * FROM customer WHERE username = '" + username + "' AND password = '" + password + "'";
        return statement.executeQuery(getUser);

    }
public static void addUser(String firstname,String lastname,String phoneNumber,String street,String postcode,String city,String password,String username) throws SQLException {
    String insertAddress = "INSERT INTO address (street, post_code, city) values " +
            "('" + street + "','" + postcode + "','" + city + "') ";

    statement.execute(insertAddress);
    ResultSet rs = statement.executeQuery("SELECT LAST_INSERT_ID()");

    //for some reason it complains if we don't call "rs.next()" before trying to read from the result set
    int address_id = rs.next() ? rs.getInt(1) : 0; //if rs.next() is true, then address_id = rs.getInt(), otherwise address_id = 0

    //insert the customer into the customer database
    String query2 = "INSERT INTO customer (username, password, first_name, last_name, phone_number, address_id) VALUES " +
            "('" + username + "', '" + password + "', '" + firstname + "', '" + lastname + "', '" + phoneNumber + "', '" + address_id + "')";
    statement.execute(query2);

}
}
