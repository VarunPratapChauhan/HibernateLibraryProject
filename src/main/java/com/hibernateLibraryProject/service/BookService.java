package com.hibernateLibraryProject.service;

import java.util.List;

import com.hibernateLibraryProject.dao.BookDAO;
import com.hibernateLibraryProject.model.Book;

public class BookService {
    private final BookDAO bookDAO = new BookDAO();

    public void addBook(Book book) {
        bookDAO.saveBook(book);
    }

    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    public void updateBookAvailability(int bookId, boolean availability) {
        Book book = bookDAO.getBookById(bookId);
        if (book != null) {
            book.setAvailable(availability);
            bookDAO.updateBook(book);
        }
    }
}