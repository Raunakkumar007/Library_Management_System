package controller;

import db.DBConnection;
import java.sql.*;

public class UserDAO {

    public static String validateUser(String username, String password) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT role FROM users WHERE username = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("role");  // returns "admin" or "user"
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;  // invalid credentials
    }
}