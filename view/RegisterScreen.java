package view;

import db.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterScreen {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public RegisterScreen() {
        frame = new JFrame("Register - Library System");
        frame.setSize(350, 250);
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

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(120, 110, 100, 30);
        frame.add(registerButton);

        JButton backButton = new JButton("Back to Login");
        backButton.setBounds(100, 150, 130, 30);
        frame.add(backButton);

        registerButton.addActionListener(e -> registerUser());

        backButton.addActionListener(e -> {
            frame.dispose();
            new LoginScreen();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void registerUser() {
        String username = usernameField.getText().trim();
        String password = String.valueOf(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "X Username and password can't be empty.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, 'user')";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(frame, "Registration successful! You can now log in.");
            frame.dispose();
            new LoginScreen();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "X Registration failed: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RegisterScreen::new);
    }
}
