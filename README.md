# Library_Management_System

Library Management System (Java + MySQL + Swing)

A GUI-based Java project for managing books and users in a library using MySQL for persistent storage. This project supports secure login, user registration, role-based access, and standard book operations (add, delete, search, export).


Features:
Login & Registration
Role-based Access:
Admin: Add, delete, search, export
User: View, search, export
GUI built using Java Swing
MySQL database integration with JDBC
Book list displayed using JList
Data export to CSV file

Technologies Used:
Java (JDK )
IntelliJ IDEA
Java Swing (GUI)
MySQL (Workbench)
JDBC


Project Structure

src 
  model
            Book.java
            
 controller
           BookDAO.java
           UserDAO.java
           
 view
           LoginScreen.java
           RegisterScreen.java
           LibraryGUI.java
           
  db. 
           DBConnection.java


Database Schema

Database: library_db

Table: books

Column	Type

id	INT, PRIMARY KEY, AUTO_INCREMENT
name	VARCHAR(100)
author	VARCHAR(100)

Table: users

Column	Type

id	INT, PRIMARY KEY, AUTO_INCREMENT
username	VARCHAR(100), UNIQUE
password	VARCHAR(100)
role	VARCHAR(20)


How to Run

1. Clone this repository:git clone https://github.com/yourusername/library-management-system.git
2. Import into IntelliJ IDEA
3. Set up your MySQL database using the provided schema
4. Update DBConnection.java with your MySQL credentials
5. Run LoginScreen.java


Default Credentials

Role	our Username	 our Password

Admin	admin	1234
User	user	guest

This project is open-source.
