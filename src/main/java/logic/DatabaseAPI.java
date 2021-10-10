package logic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseAPI {

    public ResultSet getAddress(Connection connection,int id) throws SQLException {
        String getAddress = "SELECT * FROM address WHERE address_id = '" + id + "'";
        Statement statement = connection.createStatement();

        return  statement.executeQuery(getAddress);

    }
    public ResultSet getUser(Connection connection,String username, String password) throws SQLException {
        String getUser = "SELECT * FROM customer WHERE username = '" + username + "' AND password = '" + password + "'";
        Statement statement = connection.createStatement();
        return statement.executeQuery(getUser);
    }

}
