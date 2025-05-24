package view;

import controller.BookDAO;
import model.Book;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.util.ArrayList;

public class LibraryGUI {

    private DefaultListModel<Book> bookListModel;
    private JList<Book> bookList;
    private JTextField searchField;

    public LibraryGUI(String role) {
        JFrame frame = new JFrame("Library Management System — Logged in as: " + role.toUpperCase());
        frame.setSize(600, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Search Panel
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(25);
        JButton searchButton = new JButton("Search");

        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        frame.add(searchPanel, BorderLayout.NORTH);

        // Book List
        bookListModel = new DefaultListModel<>();
        refreshBookList();
        bookList = new JList<>(bookListModel);
        JScrollPane scrollPane = new JScrollPane(bookList);
        frame.add(scrollPane, BorderLayout.CENTER);

        // + - Button Panel
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton(" Add Book");
        JButton deleteButton = new JButton("Delete Book");
        JButton exportButton = new JButton("Export CSV");

        //  Access Control
        if (!role.equals("admin")) {
            addButton.setEnabled(false);
            deleteButton.setEnabled(false);
            addButton.setToolTipText("X Only admin can add books.");
            deleteButton.setToolTipText("X Only admin can delete books.");
        }

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(exportButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Add Book Logic
        addButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter book name:");
            String author = JOptionPane.showInputDialog("Enter author name:");

            if (name != null && author != null && !name.isBlank() && !author.isBlank()) {
                BookDAO.addBook(name.trim(), author.trim());
                refreshBookList();
            } else {
                JOptionPane.showMessageDialog(frame, "X Book name and author cannot be empty.");
            }
        });

        //  Delete Book Logic
        deleteButton.addActionListener(e -> {
            Book selected = bookList.getSelectedValue();
            if (selected != null) {
                BookDAO.deleteBook(selected.getId());
                refreshBookList();
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a book to delete.");
            }
        });

        // Export to CSV Logic
        exportButton.addActionListener(e -> {
            try {
                FileWriter writer = new FileWriter("books_export.csv");
                writer.write("ID,Title,Author\n");
                for (Book b : BookDAO.getAllBooks()) {
                    writer.write(b.getId() + "," + b.getName() + "," + b.getAuthor() + "\n");
                }
                writer.close();
                JOptionPane.showMessageDialog(frame, " Book list exported as books_export.csv");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "X Error exporting file: " + ex.getMessage());
            }
        });

        // Search Logic
        searchButton.addActionListener(e -> {
            String keyword = searchField.getText().toLowerCase().trim();
            bookListModel.clear();
            for (Book b : BookDAO.getAllBooks()) {
                if (b.getName().toLowerCase().contains(keyword) || b.getAuthor().toLowerCase().contains(keyword)) {
                    bookListModel.addElement(b);
                }
            }
        });

        frame.setVisible(true);
    }

    private void refreshBookList() {
        bookListModel.clear();
        ArrayList<Book> books = BookDAO.getAllBooks();
        for (Book b : books) {
            bookListModel.addElement(b);
        }
    }

    // Optional main method for direct testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibraryGUI("admin"));  // or"user"
    }
}