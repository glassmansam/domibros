package logic;

import gui.CartEntry;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseAPI {
    static Connection connection = null;
    static Statement statement = null;
    static String[] codes = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V"};

    static {
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            //Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/domibros?useSSL=false&user=root&password=root&serverTimezone=Europe/Rome");
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ResultSet getAddress(int id) throws SQLException {
        String getAddress = "SELECT * FROM address WHERE address_id = '" + id + "'";
        return statement.executeQuery(getAddress);

    }

    public static ResultSet getPizzas() throws SQLException {
        String query = "SELECT * FROM pizza";
        return statement.executeQuery(query);
    }
    public static long getDeliveryTime() throws SQLException {
        String query = "SELECT * FROM driver ORDER BY last_left ASC LIMIT 1";
        ResultSet rs = statement.executeQuery(query);
        rs.next();
        long time = rs.getLong("last_left");
        if(time+1800000>System.currentTimeMillis()){
            long time_left = time+1800000-System.currentTimeMillis();
            long put_in_time = time+time_left;
            long EDT = put_in_time+900000;
                    String query2 = "UPDATE driver SET last_left = '"+put_in_time+"' WHERE driver_id = '"+rs.getInt("driver_id")+"'";
                            statement.execute(query2);
                            return EDT;
        }
        else{
            String query3 = "UPDATE driver SET last_left = '"+System.currentTimeMillis()+"' WHERE driver_id = '"+rs.getInt("driver_id")+"'";
            statement.execute(query3);
            return System.currentTimeMillis()+900000;
        }
    }

    public static int makeOrderAndGetId(int address_id, int customer_id) throws SQLException {
        String query = "INSERT INTO orders (address_id,customer_id) VALUES"+"('"+address_id+"','"+customer_id+"')";
        statement.execute(query);
        ResultSet rs = statement.executeQuery("SELECT LAST_INSERT_ID()");
        int order_id = rs.next() ? rs.getInt(1):0;
        return order_id;
    }
    public static void increase_Number(int customer_id) throws SQLException {
        String query = "UPDATE customer SET amount_ordered = amount_ordered+1 WHERE id ="+"'"+customer_id+"'";
        statement.execute(query);
        ResultSet rs = statement.executeQuery("SELECT amount_ordered FROM customer WHERE id ="+"'"+customer_id+"'");
        int currentNumber = rs.getInt("amount_ordered");
        if(currentNumber%10==0){
            StringBuilder sb = new StringBuilder();
            for(int i =0;i<7;i++){
                int current = (int) (Math.random() * codes.length);
                sb.append(codes[current]);
            }
            String query3 = "INSERT INTO discounts (code,customer_id) VALUES ('"+sb.toString()+"','"+customer_id+"')";
            statement.execute(query3);


        }
    }
    public static String[] getCodes(int customer_id) throws SQLException {
        String query = "SELECT * FROM discounts WHERE customer_id='"+customer_id+"'";
        ResultSet rs = statement.executeQuery(query);
        ArrayList<String> result = new ArrayList();
        while(rs.next()){
            result.add(rs.getString("code"));
        }
        return result.toArray(new String[0]);
    }

    public static void addOrders(CartEntry item,int order_id) throws SQLException {

        if(item.getType()==Type.PIZZA){
            String query = "INSERT INTO order_pizza (order_id,pizza_id) VALUES" + "('"+order_id+"','"+item.getIdentifier()+"')";
           statement.execute(query);
        }
        if(item.getType()==Type.DESSERT){
           String query = "INSERT INTO order_dessert (order_id,dessert_id) VALUES" + "('"+order_id+"','"+item.getIdentifier()+"')";
            statement.execute(query);
        }
        if(item.getType()==Type.DRINK){
            String query = "INSERT INTO order_drink (order_id,drink_id) VALUES" + "('"+order_id+"','"+item.getIdentifier()+"')";
            statement.execute(query);
        }

    }

    public static String[] getToppings(int id) throws SQLException {
        ArrayList<String> toppings = new ArrayList<>();
        statement = connection.createStatement();
        String query = "SELECT * FROM topping INNER JOIN pizza_topping ON topping.topping_id = pizza_topping.topping_id WHERE pizza_topping.pizza_id='" + id + "'";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            toppings.add(rs.getString("name"));
        }
        return toppings.toArray(new String[0]);
    }

    public static ResultSet getDrinks() throws SQLException {
        String query = "SELECT * FROM drink";
        return statement.executeQuery(query);
    }

    public static ResultSet getDesserts() throws SQLException {
        String query = "SELECT * FROM dessert";
        return statement.executeQuery(query);
    }

    public static ResultSet getUser(String username, String password) throws SQLException {
        String getUser = "SELECT * FROM customer WHERE username = '" + username + "' AND password = '" + password + "'";
        return statement.executeQuery(getUser);

    }

    public static void addUser(String firstname, String lastname, String phoneNumber, String street, String postcode, String city, String password, String username) throws SQLException {
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
