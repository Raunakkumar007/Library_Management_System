package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import controller.BookDAO;

public class IssueBook extends JFrame {

    private JComboBox<String> bookComboBox;
    private JComboBox<String> userComboBox;
    private HashMap<String, Integer> bookMap;
    private HashMap<String, Integer> userMap;
    private JButton issueButton;

    public IssueBook() {
        bookComboBox = new JComboBox<>();
        userComboBox = new JComboBox<>();
        bookMap = new HashMap<>();
        userMap = new HashMap<>();

        setTitle("Issue Book");
        setSize(400, 300);
        setLayout(new GridLayout(4, 2, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(240, 248, 255));

        add(new JLabel("Select Book:"));
        add(bookComboBox);

        add(new JLabel("Select User:"));
        add(userComboBox);

        issueButton = new JButton("Issue Book");
        add(new JLabel()); // For spacing
        add(issueButton);

        populateDropdowns(); // Fill dropdowns with book/user data

        // Event Handling
        issueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedBook = (String) bookComboBox.getSelectedItem();
                String selectedUser = (String) userComboBox.getSelectedItem();

                if (selectedBook == null || selectedUser == null) {
                    JOptionPane.showMessageDialog(null, "Please select both a book and a user.");
                    return;
                }

                int bookId = bookMap.get(selectedBook);
                int userId = userMap.get(selectedUser);
                boolean isAvailable = BookDAO.isBookAvailable(bookId);
                if(!isAvailable){
                    JOptionPane.showMessageDialog(null, "This book is already issued and not available");
                    return;
                }

                try {
                    boolean success = BookDAO.issueBook(bookId, userId);
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Book issued successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to issue book.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Database Error: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        setVisible(true);
    }

    private void populateDropdowns() {
        // Get books and populate
        ArrayList<String[]> books = BookDAO.getAvailableBooks();// assuming [title, id]
        for (String[] book : books) {
            String title = book[0];
            int id = Integer.parseInt(book[1]);
            bookComboBox.addItem(title);
            bookMap.put(title, id);
        }

        // Get users and populate
        ArrayList<String[]> users = BookDAO.getUserList(); // assuming [username, id]
        for (String[] user : users) {
            String username = user[0];
            int id = Integer.parseInt(user[1]);
            userComboBox.addItem(username);
            userMap.put(username, id);
        }
    }

    public static void main(String[] args) {
        new IssueBook();
    }
}