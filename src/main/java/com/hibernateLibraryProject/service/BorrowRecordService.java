package com.hibernateLibraryProject.service;

import com.hibernateLibraryProject.dao.BookDAO;
import com.hibernateLibraryProject.dao.MemberDAO;
import com.hibernateLibraryProject.dao.BorrowRecordDAO;
import com.hibernateLibraryProject.model.Book;
import com.hibernateLibraryProject.model.Member;
import com.hibernateLibraryProject.model.BorrowRecord;

import java.util.Date;
import java.util.List;

public class BorrowRecordService {

	private final BorrowRecordDAO borrowRecordDAO;
	private final BookDAO bookDAO;
	private final MemberDAO memberDAO;

	// Method to check for overdue fines and issue them
	public void checkAndIssueFine(int borrowRecordId) {
		// Retrieve the transaction from the database using the transactionId
		BorrowRecord borrowRecord = borrowRecordDAO.getBorrowRecordById(borrowRecordId);

		if (borrowRecord != null) {
			// Calculate overdue days
			long overdueDays = (new Date().getTime() - borrowRecord.getDueDate().getTime()) / (1000 * 60 * 60 * 24);
			if (overdueDays > 0) {
				// Calculate fine amount
				double fineAmount = overdueDays * 100.0; // 100 rupees per day
				borrowRecord.setFineAmount(fineAmount);

				// Save the updated transaction with the fine in the database
				borrowRecordDAO.updateBorrowRecord(borrowRecord);

				System.out.println("Fine of $" + fineAmount + " has been issued for borrowRecord ID: " + borrowRecordId);
			} else {
				System.out.println("No fine due for transaction ID: " + borrowRecordId);
			}
		} else {
			System.out.println("Transaction not found.");
		}
	}

	public List<BorrowRecord> getBorrowRecordByBookIdAndMemberId(int bookId, int memberId) {
		return borrowRecordDAO.getBorrowRecordByBookIdAndMemberId(bookId, memberId);
	}

	public BorrowRecordService() {
		borrowRecordDAO = new BorrowRecordDAO();
		bookDAO = new BookDAO();
		memberDAO = new MemberDAO();
	}

	// Issue a book to a member
	public boolean issueBook(int bookId, int memberId, Date dueDate) {
		Book book = bookDAO.getBookById(bookId);
		Member member = memberDAO.getMemberById(memberId);

		if (book != null && member != null && book.isAvailable()) {
			// Create a transaction entry for issuing the book
			BorrowRecord transaction = new BorrowRecord();
			transaction.setBook(book);
			transaction.setMember(member);
			transaction.setIssueDate(new Date()); // Current date
			transaction.setDueDate(dueDate); // Set the due date

			// Save the transaction
			borrowRecordDAO.issueBook(transaction);

			// Mark the book as not available
			book.setAvailable(false);
			bookDAO.updateBook(book);
			return true;
		} else {
			System.out.println("Either the book or the member doesn't exist or the book is already issued.");
			return false;
		}
	}

	// Return a book
	public void returnBook(int bookId, int memberId) {
		Book book = bookDAO.getBookById(bookId);
		Member member = memberDAO.getMemberById(memberId);

		if (book != null && member != null) {
			// Get all transactions for the book and member
			List<BorrowRecord> borrowRecords = borrowRecordDAO.getBorrowRecordByBookIdAndMemberId(bookId, memberId);

			if (borrowRecords != null && !borrowRecords.isEmpty()) {
				boolean returned = false;

				// Process each transaction to find any that haven't been returned
				for (BorrowRecord borrowRecord : borrowRecords) {
					if (borrowRecord.getReturnDate() == null) { // Check if it's an open transaction
						// Mark the transaction as returned
						borrowRecord.setReturnDate(new java.util.Date());
						borrowRecordDAO.updateBorrowRecord(borrowRecord);
						returned = true;
					}
				}

				if (returned) {
					// Mark the book as available again if at least one transaction was returned
					book.setAvailable(true);
					bookDAO.updateBook(book);
//					System.out.println("Book returned successfully.");
				} else {
					System.out.println("No outstanding transaction found for this book and member.");
				}
			} else {
				System.out.println("No borrow records found for this book and member.");
			}
		} else {
			System.out.println("Either the book or the member doesn't exist.");
		}
	}

	// Get all transactions
	public List<BorrowRecord> getAllBorrowRecords() {
		return borrowRecordDAO.getBorrowRecords();
	}
}