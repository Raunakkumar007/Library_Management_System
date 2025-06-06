
📚 Library Management System

This is a desktop application built using Java Swing for managing library operations. It uses a MySQL database for storing data and provides a clean GUI for both admins and regular users.

✨ Key Features

User Registration and Login (with roles: Admin and User)
Add, Search, and Delete Books (Admin only)
View complete list of books
Issue and Return Books
Export book list as a CSV file
Role-based access control for features

🔧 Technologies Used

Java (JDK 8 or above)
Java Swing (for GUI)
MySQL (as the database)
JDBC (for database connection)

🗂️ Project Structure

controller: Data access logic (e.g., BookDAO, UserDAO)
db: Database connection utility
model: Data models like Book and User
view: GUI components (LoginScreen, RegisterScreen, LibraryGUI, etc.)

⚙️ Setup Instructions

Clone the repository from GitHub:
git clone https://github.com/Raunakkumar007/LibraryManagementSystem.git

Set up the MySQL database:
Create a new database named librarydb
Create the following tables: books, users, and issued_books
Insert an admin account manually (example: username = admin, password = admin123, role = admin)
Open DBConnection.java and update the database URL, username, and password according to your system.
Open the project in your favorite Java IDE (e.g., IntelliJ, Eclipse, or NetBeans)
Run the application starting from LoginScreen.java

HOW TO RUN

Start from LoginScreen.java
Register a user or login as admin (admin/admin)
Admin can manage books and export CSV
Any logged-in user can issue or return books

✅ Possible Future Enhancements

Encrypt passwords for better security
Allow uploading book cover images
Add filtering and sorting options for books
Implement a dark mode for the UI

📄 License
This project is open-source under the MIT License.