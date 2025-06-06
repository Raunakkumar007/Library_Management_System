package controller;

import db.DBConnection;
import model.Book;

import java.sql.*;
import java.util.ArrayList;

public class BookDAO {

    // 1. Fetch all books from DB
    public static ArrayList<Book> getAllBooks() {
        ArrayList<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Book book = new Book(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("author")
                );
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    // 2. Add a new book
    public static boolean addBook(String title, String author) {
        String sql = "INSERT INTO books (name, author, available) VALUES (?, ?, TRUE)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, title);
            pst.setString(2, author);
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("SQL ERROR (addBook): " + e.getMessage());
            return false;
        }
    }

    // 3. Issue book
    public static boolean issueBook(int bookId, int userId) {
        String issueSql = "INSERT INTO issued_books (book_id, user_id, issue_date, status) VALUES (?, ?, CURDATE(), 'Issued')";
        String updateAvailability = "UPDATE books SET available = FALSE WHERE id = ?";
        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false); // Transaction start

            try (PreparedStatement issuePst = con.prepareStatement(issueSql);
                 PreparedStatement updatePst = con.prepareStatement(updateAvailability)) {

                issuePst.setInt(1, bookId);
                issuePst.setInt(2, userId);
                int issuedRows = issuePst.executeUpdate();

                updatePst.setInt(1, bookId);
                int updatedRows = updatePst.executeUpdate();

                if (issuedRows > 0 && updatedRows > 0) {
                    con.commit();
                    return true;
                } else {
                    con.rollback();
                }
            } catch (SQLException ex) {
                con.rollback();
                throw ex;
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 4. Return book
    public static boolean returnBook(int bookId, int userId) {
        String returnSql = "UPDATE issued_books SET return_date = CURDATE(), status = 'Returned' WHERE book_id = ? AND user_id = ? AND status = 'Issued'";
        String updateAvailability = "UPDATE books SET available = TRUE WHERE id = ?";
        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement returnPst = con.prepareStatement(returnSql);
                 PreparedStatement updatePst = con.prepareStatement(updateAvailability)) {

                returnPst.setInt(1, bookId);
                returnPst.setInt(2, userId);
                int returnRows = returnPst.executeUpdate();

                updatePst.setInt(1, bookId);
                int updateRows = updatePst.executeUpdate();

                if (returnRows > 0 && updateRows > 0) {
                    con.commit();
                    return true;
                } else {
                    con.rollback();
                }
            } catch (SQLException ex) {
                con.rollback();
                throw ex;
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 5. Delete a book
    public static boolean deleteBook(int id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 6. Get book list for JComboBox (id and name)
    public static ArrayList<String[]> getBookList() {
        ArrayList<String[]> books = new ArrayList<>();
        String sql = "SELECT id, name FROM books";
        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                books.add(new String[]{rs.getString("name"), String.valueOf(rs.getInt("id"))});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    // 7. Get only available books for JComboBox
    public static ArrayList<String[]> getAvailableBooks() {
        ArrayList<String[]> books = new ArrayList<>();
        String sql = "SELECT id, name FROM books WHERE available = TRUE";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                books.add(new String[]{rs.getString("name"), String.valueOf(rs.getInt("id"))});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    // 8. Get all users for JComboBox (id and username)
    public static ArrayList<String[]> getUserList() {
        ArrayList<String[]> users = new ArrayList<>();
        String sql = "SELECT id, username FROM users";
        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                users.add(new String[]{rs.getString("username"), String.valueOf(rs.getInt("id"))});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // 9. Check if book is available
    public static boolean isBookAvailable(int bookId) {
        String sql = "SELECT available FROM books WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, bookId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("available");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 10. Check if book is issued to user
    public static boolean isBookIssued(int bookId, int userId) {
        String sql = "SELECT * FROM issued_books WHERE book_id = ? AND user_id = ? AND status = 'Issued'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, bookId);
            pst.setInt(2, userId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
