import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    static String username;
    static String password;

    public static void main(String[] args) {
        Connection connection = null;
//        Properties properties = new Properties();
//        properties.setProperty("user", "root");
//        properties.setProperty("password", "root");
//        properties.setProperty("useSSL", "false");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/domibros?user=root&password=root&serverTimezone=Europe/Rome");
        } catch (Exception e) {
            e.printStackTrace();
        }

        logOption(connection);
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

    public static void logOption(Connection connection) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Log in or register now!");
        panel.add(label);
        String[] options = {"Log in!", "Register!"};
        int option = JOptionPane.showOptionDialog(null, panel, "Enter your login credentials", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
        if (option == 0) {
            logIn(connection);
        }
        if (option == 1) {
            register(connection);
        }
    }

    public static void register(Connection connection) {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Put in your data please!");
        panel.add(label);
        JTextField first_nameR = new JTextField(30);
        JTextField last_nameR = new JTextField(30);
        JTextField usernameR = new JTextField(30);
        JPasswordField passwordR = new JPasswordField(30);
        JTextField phone_numberR = new JTextField(14);
        JButton button = new JButton("Click me!");
        panel.add(first_nameR);
        panel.add(last_nameR);
        panel.add(usernameR);
        panel.add(passwordR);
        panel.add(phone_numberR);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String first_name = first_nameR.getText();
                String last_name = last_nameR.getText();
                String username = usernameR.getText();
                String password = String.copyValueOf(passwordR.getPassword());
                int phone_number = Integer.parseInt(phone_numberR.getText());
                String query = "INSERT INTO customer (username, password,first_name,last_name,phone_number) VALUES ('" + username + "','" + password + "','" + first_name + "','" + last_name + "','" + phone_number + "')";
                try (Statement stmt = connection.createStatement()) {
                    stmt.execute(query);
                } catch (Exception a) {
                    a.printStackTrace();
                }
            }
        });
        panel.add(button);
        frame.add(panel);
        frame.setSize(400,400);
        frame.setVisible(true);
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

    }
}

