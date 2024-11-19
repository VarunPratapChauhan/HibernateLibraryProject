package com.hibernateLibraryProject.dto;

import com.hibernateLibraryProject.model.Book;
import com.hibernateLibraryProject.model.Member;
import com.hibernateLibraryProject.model.BorrowRecord;

public class BorrowRecordDTO {

    private BorrowRecord borrowRecord;
    private Book book;
    private Member member;

    public BorrowRecordDTO(BorrowRecord borrowRecord, Book book, Member member) {
        this.borrowRecord = borrowRecord;
        this.book = book;
        this.member = member;
    }

    // Getters and setters
    public BorrowRecord getTransaction() {
        return borrowRecord;
    }

    public void setTransaction(BorrowRecord transaction) {
        this.borrowRecord = transaction;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
