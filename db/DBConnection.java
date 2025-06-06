<<<<<<< HEAD
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection{

    // This method opens a connection to the MySQL database
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/library_db";  // Your database name
        String user = "root";                     // Your MySQL username
        String password = "XYZ";                 // Your MySQL password (if any)

        return DriverManager.getConnection(url, user, password);
    }
=======
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection{

    // This method opens a connection to the MySQL database
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/library_db";  // Your database name
        String user = "root";                     // Your MySQL username
        String password = "xyz";                 // Your MySQL password (if any)

        return DriverManager.getConnection(url, user, password);
    }
>>>>>>> 134a4af4e4fe4707adae5a3ebfd3e93718f39062
}