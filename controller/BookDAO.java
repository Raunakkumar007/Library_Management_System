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
