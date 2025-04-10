import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Represents a book in the library
class Book {
    private String title;
    private String author;
    private String isbn;
    private String category;
    private boolean isBorrowed;

    public Book(String title, String author, String isbn, String category) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.isBorrowed = false;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getCategory() {
        return category;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", ISBN: " + isbn + ", Category: " + category +
               (isBorrowed ? " (Borrowed)" : " (Available)");
    }
}

// Represents a user of the library
class User {
    private String userId;
    private String password;
    private String name;
    private List<Book> borrowedBooks;

    public User(String userId, String password, String name) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    // Getters
    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    // Method to check password (for simplicity, we store in plain text - in a real system, hash and salt!)
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }

    @Override
    public String toString() {
        return "User ID: " + userId + ", Name: " + name + ", Borrowed Books: " + borrowedBooks.size();
    }
}

// Manages the library system
class Library {
    private List<Book> books;
    private Map<String, User> users;

    public Library() {
        this.books = new ArrayList<>();
        this.users = new HashMap<>();
    }

    // Add a new book to the library
    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added: " + book.getTitle());
    }

    // Register a new user
    public void registerUser(User user) {
        if (!users.containsKey(user.getUserId())) {
            users.put(user.getUserId(), user);
            System.out.println("User registered: " + user.getName());
        } else {
            System.out.println("User ID already exists.");
        }
    }

    // Authenticate a user
    public User authenticateUser(String userId, String password) {
        User user = users.get(userId);
        if (user != null && user.checkPassword(password)) {
            return user;
        }
        System.out.println("Authentication failed.");
        return null;
    }

    // Borrow a book
    public void borrowBook(User user, String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn) && !book.isBorrowed()) {
                book.setBorrowed(true);
                user.borrowBook(book);
                System.out.println(user.getName() + " borrowed: " + book.getTitle());
                return;
            } else if (book.getIsbn().equals(isbn) && book.isBorrowed()) {
                System.out.println("Book with ISBN " + isbn + " is already borrowed.");
                return;
            }
        }
        System.out.println("Book with ISBN " + isbn + " not found.");
    }

    // Return a book
    public void returnBook(User user, String isbn) {
        for (Book book : user.getBorrowedBooks()) {
            if (book.getIsbn().equals(isbn)) {
                book.setBorrowed(false);
                user.returnBook(book);
                System.out.println(user.getName() + " returned: " + book.getTitle());
                return;
            }
        }
        System.out.println("Book with ISBN " + isbn + " was not borrowed by " + user.getName() + ".");
    }

    // List all available books
    public void listAvailableBooks() {
        System.out.println("\n--- Available Books ---");
        for (Book book : books) {
            if (!book.isBorrowed()) {
                System.out.println(book);
            }
        }
        System.out.println("----------------------");
    }

    // List borrowed books by a user
    public void listBorrowedBooks(User user) {
        System.out.println("\n--- Books Borrowed by " + user.getName() + " ---");
        if (user.getBorrowedBooks().isEmpty()) {
            System.out.println("No books borrowed.");
        } else {
            for (Book book : user.getBorrowedBooks()) {
                System.out.println(book);
            }
        }
        System.out.println("----------------------------------------");
    }

    // List books by category
    public void listBooksByCategory(String category) {
        System.out.println("\n--- Books in Category: " + category + " ---");
        boolean found = false;
        for (Book book : books) {
            if (book.getCategory().equalsIgnoreCase(category)) {
                System.out.println(book);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No books found in this category.");
        }
        System.out.println("----------------------------------");
    }
}

public class ELibrarySystem {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        // Sample books
        library.addBook(new Book("The Lord of the Rings", "J.R.R. Tolkien", "978-0618260274", "Fantasy"));
        library.addBook(new Book("Pride and Prejudice", "Jane Austen", "978-0141439518", "Romance"));
        library.addBook(new Book("The Hitchhiker's Guide to the Galaxy", "Douglas Adams", "978-0345391803", "Science Fiction"));
        library.addBook(new Book("To Kill a Mockingbird", "Harper Lee", "978-0061120084", "Fiction"));
        library.addBook(new Book("Foundation", "Isaac Asimov", "978-0553803717", "Science Fiction"));

        // Sample users
        User user1 = new User("user123", "pass123", "Alice Smith");
        User user2 = new User("bob456", "securePass", "Bob Johnson");
        library.registerUser(user1);
        library.registerUser(user2);

        User currentUser = null;

        while (true) {
            if (currentUser == null) {
                System.out.println("\n--- Library System ---");
                System.out.println("1. Login");
                System.out.println("2. Register");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        System.out.print("Enter User ID: ");
                        String loginId = scanner.nextLine();
                        System.out.print("Enter Password: ");
                        String loginPassword = scanner.nextLine();
                        currentUser = library.authenticateUser(loginId, loginPassword);
                        if (currentUser != null) {
                            System.out.println("Welcome, " + currentUser.getName() + "!");
                        }
                        break;
                    case "2":
                        System.out.print("Enter new User ID: ");
                        String newUserId = scanner.nextLine();
                        System.out.print("Enter new Password: ");
                        String newPassword = scanner.nextLine();
                        System.out.print("Enter your Name: ");
                        String newName = scanner.nextLine();
                        library.registerUser(new User(newUserId, newPassword, newName));
                        break;
                    case "3":
                        System.out.println("Exiting system. Goodbye!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } else {
                System.out.println("\n--- Library Menu ---");
                System.out.println("1. List Available Books");
                System.out.println("2. List Borrowed Books");
                System.out.println("3. Borrow Book");
                System.out.println("4. Return Book");
                System.out.println("5. List Books by Category");
                System.out.println("6. Logout");
                System.out.print("Enter your choice: ");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        library.listAvailableBooks();
                        break;
                    case "2":
                        library.listBorrowedBooks(currentUser);
                        break;
                    case "3":
                        System.out.print("Enter ISBN of the book to borrow: ");
                        String borrowIsbn = scanner.nextLine();
                        library.borrowBook(currentUser, borrowIsbn);
                        break;
                    case "4":
                        System.out.print("Enter ISBN of the book to return: ");
                        String returnIsbn = scanner.nextLine();
                        library.returnBook(currentUser, returnIsbn);
                        break;
                    case "5":
                        System.out.print("Enter category to search: ");
                        String category = scanner.nextLine();
                        library.listBooksByCategory(category);
                        break;
                    case "6":
                        System.out.println("Logged out.");
                        currentUser = null;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }
}