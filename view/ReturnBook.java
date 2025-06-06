package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import controller.BookDAO;

public class ReturnBook extends JFrame {

    private JTextField bookIdField, userIdField;
    private JButton returnButton;

    public ReturnBook() {
        setTitle("Return Book");
        setSize(300, 200);
        setLayout(new GridLayout(3, 2, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Book ID
        add(new JLabel("Book ID:"));
        bookIdField = new JTextField();
        add(bookIdField);

        // User ID
        add(new JLabel("User ID:"));
        userIdField = new JTextField();
        add(userIdField);

        // Return Button
        add(new JLabel()); // Empty label for spacing
        returnButton = new JButton("Return Book");
        add(returnButton);

        // Action listener for return button
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookIdText = bookIdField.getText().trim();
                String userIdText = userIdField.getText().trim();

                if (bookIdText.isEmpty() || userIdText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Both Book ID and User ID are required.");
                    return;
                }

                try {
                    int bookId = Integer.parseInt(bookIdText);
                    int userId = Integer.parseInt(userIdText);

                    Boolean isIssued = BookDAO.isBookIssued(bookId, userId);
                    if (!isIssued){
                        JOptionPane.showMessageDialog(null, "This book was not issued to this user or has already been returned");
                        return;
                    }

                    boolean success = BookDAO.returnBook(bookId, userId);

                    if (success) {
                        JOptionPane.showMessageDialog(null, "Book returned successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to return book.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter valid numeric IDs.");
                    ex.printStackTrace();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Unexpected Error: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new ReturnBook();
    }
}
