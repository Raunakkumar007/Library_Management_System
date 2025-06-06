<<<<<<< HEAD
        package controller;

import db.DBConnection;
import model.Book;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;
import java.util.ArrayList;

public class BookDAO {

    // 1. Fetch all books from DB
    public static ArrayList<Book> getAllBooks() {
        ArrayList<Book> books = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM books");
            while (rs.next()) {
                books.add(new Book(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("author")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    // 2. Add a book
    public static boolean addBook(String title, String author) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO books (name, author) VALUES (?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, title);
            pst.setString(2, author);
            int rowsAffected = pst.executeUpdate();
            System.out.println("Book added: " + title + " by " + author);
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("SQL ERROR (addBook): " + e.getMessage());
            return false;
        }
    }

    // 3. Issue book
    public static boolean issueBook(int bookId, int userId) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO issued_books (book_id, user_id, issue_date, status) VALUES (?, ?, CURDATE(), 'Issued')";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, bookId);
            pst.setInt(2, userId);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                String updateQuery = "UPDATE books SET available = FALSE WHERE id = ?";
                PreparedStatement updatePst = con.prepareStatement(updateQuery);
                updatePst.setInt(1, bookId);
                updatePst.executeUpdate();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 4. Return book
    public static boolean returnBook(int bookId, int userId) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "UPDATE issued_books SET return_date = CURDATE(), status = 'Returned' WHERE book_id = ? AND user_id = ? AND status = 'Issued'";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, bookId);
            pst.setInt(2, userId);
            int rowsAffected = pst.executeUpdate();

            if(rowsAffected > 0){
                String updateAvailability = "UPDATE books SET available = TRUE WHERE id = ?";
                PreparedStatement updatePst = con.prepareStatement(updateAvailability);
                updatePst.setInt(1, bookId);
                updatePst.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // IS BOOK ISSUED
    public static boolean isBookIssued(int bookId, int userId) {
        boolean isIssued = false;

        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM issued_books WHERE book_id = ? AND user_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, bookId);
            pst.setInt(2, userId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                isIssued = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isIssued;
    }

    // IS BOOK AVAILABLE
    public static boolean isBookAvailable(int bookId) {
        boolean available = false;

        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT available FROM books WHERE id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, bookId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                available = rs.getBoolean("available");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return available;
    }
    // DELETE BOOK
    public static boolean deleteBook(int id) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "DELETE FROM books WHERE id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 5. Fetch list of books for JComboBox
    public static ArrayList<String[]> getBookList() {
        ArrayList<String[]> books = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT id, name FROM books";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String[] entry = { rs.getString("name"), String.valueOf(rs.getInt("id")) };
                books.add(entry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
    // Available Books
    public static ArrayList<String[]> getAvailableBooks() {
        ArrayList<String[]> books = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT b.id, b.name FROM books b " + "Where b.id NOT IN (SELECT book_id FROM issued_books WHERE status is NULL)";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                books.add(new String[] {rs.getString("name"), rs.getString("id")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    // 6. Fetch list of users for JComboBox
    public static ArrayList<String[]> getUserList() {
        ArrayList<String[]> users = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT id, username FROM users";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String[] entry = { rs.getString("username"), String.valueOf(rs.getInt("id")) };
                users.add(entry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
=======
        package controller;

import db.DBConnection;
import model.Book;
import db.DBConnection;

import java.sql.*;
        import java.util.ArrayList;

public class BookDAO {

    // 1. Get all books from the database
    public static ArrayList<Book> getAllBooks() {
        ArrayList<Book> books = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM books");
            while (rs.next()) {
                books.add(new Book(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("author")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    // 2. Add a new book
    public static void addBook(String name, String author) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO books (name, author) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, author);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3. Delete a book by its ID
    public static void deleteBook(int id) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM books WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
>>>>>>> 134a4af4e4fe4707adae5a3ebfd3e93718f39062
