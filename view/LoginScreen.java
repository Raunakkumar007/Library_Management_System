<<<<<<< HEAD
package view;

import controller.UserDAO;

import javax.swing.*;
import java.awt.*;

public class LoginScreen {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginScreen() {
        frame = new JFrame("Login - Library Management System");
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(30, 30, 80, 25);
        frame.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(120, 30, 160, 25);
        frame.add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 70, 80, 25);
        frame.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(120, 70, 160, 25);
        frame.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(120, 110, 100, 30);
        frame.add(loginButton);

        loginButton.addActionListener(e -> checkLogin());

        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);
    }

    private void checkLogin() {
        String username = usernameField.getText().trim();
        String password = String.valueOf(passwordField.getPassword()).trim();

        String role = UserDAO.validateUser(username, password);

        if (role != null) {
            JOptionPane.showMessageDialog(frame, "Login successful! Role: " + role);
            frame.dispose(); // Close login window
            new LibraryGUI(role); // Open main UI with role-based access
        } else {
            JOptionPane.showMessageDialog(frame, "X Invalid username or password.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginScreen::new);
    }
=======
package view;

import controller.UserDAO;

import javax.swing.*;
import java.awt.*;

public class LoginScreen {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginScreen() {
        frame = new JFrame("Login - Library Management System");
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(30, 30, 80, 25);
        frame.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(120, 30, 160, 25);
        frame.add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 70, 80, 25);
        frame.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(120, 70, 160, 25);
        frame.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(120, 110, 100, 30);
        frame.add(loginButton);

        loginButton.addActionListener(e -> checkLogin());

        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);
    }

    private void checkLogin() {
        String username = usernameField.getText().trim();
        String password = String.valueOf(passwordField.getPassword()).trim();

        String role = UserDAO.validateUser(username, password);

        if (role != null) {
            JOptionPane.showMessageDialog(frame, "Login successful! Role: " + role);
            frame.dispose(); // Close login window
            new LibraryGUI(role); // Open main UI with role-based access
        } else {
            JOptionPane.showMessageDialog(frame, "X Invalid username or password.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginScreen::new);
    }
>>>>>>> 134a4af4e4fe4707adae5a3ebfd3e93718f39062
}