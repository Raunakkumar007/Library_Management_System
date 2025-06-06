package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionTest {
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/library_db",  // Database URL
                    "root",                                       // MySQL username
                    "raj@07"                                          // MySQL password
            );
            System.out.println("Connected to MySQL!");
            conn.close();
        } catch (SQLException e) {
            System.out.println("X Error: " + e.getMessage());
        }
    }
}