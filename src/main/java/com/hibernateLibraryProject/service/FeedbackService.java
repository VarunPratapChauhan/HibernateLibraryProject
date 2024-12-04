package com.hibernateLibraryProject.service;

import com.hibernateLibraryProject.dao.BookDAO;
import com.hibernateLibraryProject.dao.FeedbackDAO;
import com.hibernateLibraryProject.dao.MemberDAO;
import com.hibernateLibraryProject.model.Book;
import com.hibernateLibraryProject.model.Feedback;
import com.hibernateLibraryProject.model.Member;

import java.util.List;

public class FeedbackService {

    private FeedbackDAO feedbackDAO = new FeedbackDAO();
    private BookDAO bookDAO = new BookDAO();
    private MemberDAO memberDAO = new MemberDAO();
    
    public void addFeedback(int returnBookId, int returnMemberId, String feedbackText, int rating) {
        // Validate rating range
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }

        // Validate the IDs
        if (returnBookId <= 0 || returnMemberId <= 0) {
            throw new IllegalArgumentException("Book ID and Member ID must be positive integers.");
        }

        // Fetch Book and Member entities from the database
        Book book = bookDAO.getBookById(returnBookId);
        Member member = memberDAO.getMemberById(returnMemberId);

        if (book == null || member == null) {
            throw new IllegalArgumentException("Book and Member must exist in the database.");
        }

        // Create a Feedback object and save it
        Feedback feedback = new Feedback(book, member, feedbackText, rating);
        feedbackDAO.addFeedback(feedback);
    }

    public List<Feedback> getFeedbackForBook(int bookId) {
        return feedbackDAO.getFeedbackForBook(bookId);
    }

    public List<Feedback> getFeedbackForMember(int memberId) {
        return feedbackDAO.getFeedbackForMember(memberId);
    }
}
