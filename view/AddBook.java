package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import controller.BookDAO;

public class AddBook extends JFrame {
    private JTextField titleField, authorField;
    private JButton addButton;

    public AddBook() {
        setTitle("Add New Book");
        setSize(300, 200);
        setLayout(new GridLayout(3, 2, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        add(new JLabel("Book Title:"));
        titleField = new JTextField();
        add(titleField);

        add(new JLabel("Author Name:"));
        authorField = new JTextField();
        add(authorField);

        addButton = new JButton("Add Book");
        add(new JLabel());
        add(addButton);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText().trim();
                String author = authorField.getText().trim();

                if (title.isEmpty() || author.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Both title and author are required.");
                    return;
                }

                boolean success = BookDAO.addBook(title, author);

                if (success) {
                    JOptionPane.showMessageDialog(null, "Book added successfully!");
                    titleField.setText("");
                    authorField.setText("");
                    titleField.requestFocusInWindow();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to add book.");
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new AddBook();
    }
}

