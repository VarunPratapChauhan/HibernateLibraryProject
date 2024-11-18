# Hibernate Library Project 📚

A **Library Management System** built using **Java**, **Hibernate**, and **MySQL**, designed to efficiently manage library operations such as adding books, registering members, issuing books, and returning books.

---

## Features 🚀

1. **Add New Books**: Maintain a catalog of books in the library.
2. **Register New Members**: Track library members and their details.
3. **Issue Books**: Allow members to borrow books from the library.
4. **Return Books**: Manage the return process and update the availability of books.
5. **View All Books**: Display all books in the library with their details.
6. **View All Members**: Display all registered members and their information.
7. **View All Transactions**: Show the history of all book issues and returns.

---

## Technologies Used 🛠️

### Backend
- **Java**: Core programming language for business logic and operations.
- **Hibernate**: ORM framework for mapping Java objects to database tables.
- **MySQL**: Relational database management system for storing data.

### Architecture
- **Model-View-Controller (MVC)**: 
  - **Model**: Represents the entities like `Book`, `Member`, and `Transaction`.
  - **DAO Layer**: Handles database operations using Hibernate.
  - **Service Layer**: Implements business logic for issuing/returning books and managing entities.
  - **UI**: Console-based, menu-driven interface for user interaction.

---

## Project Structure 🗂️
src/main/java/com/hibernateLibraryProject/ <br>
                             ├── config/ # Hibernate utility configuration <br>
                             ├── dao/ # Data Access Objects (DAO) for database operations<br> 
                             ├── dto/ # Data Transfer Objects (if applicable) <br>
                             ├── model/ # Entity classes (Book, Member, Transaction) <br>
                             ├── service/ # Business logic and service classes <br>
                             └── App.java # Main class for running the application<br>

---

## How to Run the Project ▶️

1. **Clone the repository**:
   ```bash
   git clone https://github.com/VarunPratapChauhan/HibernateLibraryProject.git
   cd HibernateLibraryProject
   
2.  **Set up the database:**

Install MySQL and create a database named "hiber_lib".
Run the following SQL script to create and use hiber_lib and don't worry about tables, they will create automatically when we run the application:

```
create DATABASE hiber_lib;
use hiber_lib;
```

3.  **Configure Hibernate:**

Update the hibernate.cfg.xml file with your MySQL credentials:
```
<property name="connection.url">jdbc:mysql://localhost:3306/hiber_lib</property>
<property name="connection.username">your-username</property>
<property name="connection.password">your-password</property>
```
4.  **Run the application:**

Compile and run the App.java file using your favorite IDE or terminal:
```
javac App.java
java App
Interact with the menu:
```
5.  **Interact with the menu:**

```
Library Management System Menu:
1. Add a new book
2. Register a new member
3. Issue a book
4. Return a book
5. Show all books
6. Show all members
7. Show all transactions
8. Exit
```