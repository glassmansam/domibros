import javax.swing.*;
import java.sql.*;
import java.util.Properties;

public class Main {
    static String username;
    static String password;

    public static void main(String[] args) {
        Connection connection = null;
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "root");
        properties.setProperty("useSSL", "false");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://localhost:8080/domibros", properties);


        } catch (Exception e) {
            e.printStackTrace();
        }
        logIn(connection);
        String query = "SELECT id,username, password FROM customer LIMIT 10";
        ResultSet rs = null;

        try (Statement stmt = connection.createStatement()) {
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                System.out.println("User " + id + " has username " + username + " and password " + password);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void logIn(Connection connection) {
        username = "";
        password = "";
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter your login and password");
        JTextField userName = new JTextField(30);
        JPasswordField passWord = new JPasswordField(30);
        panel.add(label);
        panel.add(userName);
        panel.add(passWord);
        String[] options = new String[]{"Login", "Cancel"};
        int option = JOptionPane.showOptionDialog(null, panel, "Enter your login credentials", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
        if (option == 0) {
            username = userName.getText();
            password = new String(passWord.getPassword());
        }
        String query = "ISERT INTO customer (username, password) VALUES (Cloud956,passwordisGood)";


        try (Statement stmt = connection.createStatement()) {
            stmt.executeQuery(query);
            }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    }

