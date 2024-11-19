package com.hibernateLibraryProject;

import com.hibernateLibraryProject.model.Book;
import com.hibernateLibraryProject.model.Member;
import com.hibernateLibraryProject.model.BorrowRecord;
import com.hibernateLibraryProject.service.BookService;
import com.hibernateLibraryProject.service.MemberService;
import com.hibernateLibraryProject.service.BorrowRecordService;
import com.hibernateLibraryProject.service.FeedbackService;

import com.hibernateLibraryProject.config.HibernateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        BookService bookService = new BookService();
        MemberService memberService = new MemberService();
        BorrowRecordService borrowRecordService = new BorrowRecordService();
        FeedbackService feedbackService = new FeedbackService();

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        try {
            while (!exit) {
                System.out.println("\nWelcome Library Administrator, Please select an option from the menu below:");
                System.out.println("1. Add a new book");
                System.out.println("2. Add a new member");
                System.out.println("3. Issue a book");
                System.out.println("4. Return a book");
                System.out.println("5. Check for a fine by borrowRecord ID");
                System.out.println("6. Add feedback for a book");
                System.out.println("7. Show all books");
                System.out.println("8. Show all members");
                System.out.println("9. Show all borrow records");
                System.out.println("10. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter book name: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter writer name: ");
                        String writer = scanner.nextLine();
                        System.out.print("Enter department: ");
                        String department = scanner.nextLine();

                        Book book = new Book();
                        book.setTitle(title);
                        book.setWriter(writer);
                        book.setDepartment(department);
                        bookService.addBook(book);

                        System.out.println("Book added successfully: " + book);
                        break;

                    case 2:
                        System.out.print("Enter member name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter member email: ");
                        String email = scanner.nextLine();

                        Member member = new Member();
                        member.setName(name);
                        member.setEmail(email);
                        memberService.registerMember(member);

                        System.out.println("Member registered successfully: " + member);
                        break;

                    case 3:
                        System.out.print("Enter book ID to issue: ");
                        int bookId = scanner.nextInt();
                        System.out.print("Enter member ID to issue the book to: ");
                        int memberId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        while (true) {
                            System.out.print("Enter due date (yyyy-MM-dd): ");
                            String dueDateString = scanner.nextLine();

                            try {
                                Date dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(dueDateString);
                                Date currentDate = new Date();

                                if (dueDate.before(currentDate)) {
                                    System.out.println("The due date cannot be a past date. Please enter a valid due date.");
                                } else {
                                    borrowRecordService.issueBook(bookId, memberId, dueDate);
                                    System.out.println("Book issued successfully with due date: " + dueDate);
                                    break; // Exit the loop when a valid date is entered
                                }
                            } catch (ParseException e) {
                                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                            }
                        }
                        break;

                    case 4:
                        System.out.print("Enter book ID to return: ");
                        int returnBookId = scanner.nextInt();
                        System.out.print("Enter member ID who is returning the book: ");
                        int returnMemberId = scanner.nextInt();

//                        BorrowRecord borrowRecord = borrowRecordService.getBorrowRecordByBookIdAndMemberId(returnBookId, returnMemberId);

                        List<BorrowRecord> borrowRecords = borrowRecordService.getBorrowRecordByBookIdAndMemberId(returnBookId, returnMemberId);
                        BorrowRecord borrowRecord = (borrowRecords != null && !borrowRecords.isEmpty()) ? borrowRecords.get(0) : null;

                        if (borrowRecord != null) {
                            borrowRecordService.returnBook(returnBookId, returnMemberId);
                            System.out.println("Book returned successfully.");

                            borrowRecordService.checkAndIssueFine(borrowRecord.getId());
                            System.out.println("Checked for overdue fines.");

                            System.out.print("Would you like to add feedback for the book? (yes/no): ");
                            scanner.nextLine(); // Consume newline
                            String feedbackChoice = scanner.nextLine().toLowerCase();

                            if (feedbackChoice.equals("yes")) {
                                System.out.print("Enter feedback (text): ");
                                String feedbackText = scanner.nextLine();
                                int rating = 0;
                                while (rating < 1 || rating > 5) {
                                    System.out.print("Enter rating (1 to 5): ");
                                    try {
                                        rating = Integer.parseInt(scanner.nextLine());
                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid input. Please enter a number between 1 and 5.");
                                    }
                                }

                                feedbackService.addFeedback(returnBookId, returnMemberId, feedbackText, rating);
                                System.out.println("Feedback added successfully.");
                            }
                        } else {
                            System.out.println("Borrow record not found for the given book and member.");
                        }
                        break;

                    case 5:
                        System.out.print("Enter borrowRecord ID to check fine: ");
                        int fineBorrowRecordId = scanner.nextInt();
                        borrowRecordService.checkAndIssueFine(fineBorrowRecordId);
                        System.out.println("Fine checked and issued successfully.");
                        break;

                    case 6:
                        System.out.print("Enter book ID to add feedback for: ");
                        int feedbackBookId = scanner.nextInt();
                        System.out.print("Enter member ID to provide feedback: ");
                        int feedbackMemberId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        System.out.print("Enter your feedback: ");
                        String feedbackText = scanner.nextLine();
                        System.out.print("Enter rating (1 to 5): ");
                        int feedbackRating = scanner.nextInt();

                        feedbackService.addFeedback(feedbackBookId, feedbackMemberId, feedbackText, feedbackRating);
                        System.out.println("Feedback added successfully.");
                        break;

                    case 7:
                        System.out.println("List of all books:");
                        bookService.getAllBooks().forEach(System.out::println);
                        break;

                    case 8:
                        System.out.println("List of all members:");
                        memberService.getAllMembers().forEach(System.out::println);
                        break;

                    case 9:
                        System.out.println("List of all borrow records:");
                        borrowRecordService.getAllBorrowRecords().forEach(System.out::println);
                        break;

                    case 10:
                        System.out.println("Exiting the system...");
                        exit = true;
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } finally {
            HibernateUtil.shutdown();
            scanner.close();
            System.out.println("Application terminated.");
        }
    }
}
