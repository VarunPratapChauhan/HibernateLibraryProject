package com.hibernateLibraryProject.model;

import javax.persistence.*;

@Entity
@Table(name = "Feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String feedbackText;
    private int rating; // Rating from 1 to 5

    public Feedback() {
    }

    public Feedback(Book book, Member member, String feedbackText, int rating) {
        this.book = book;
        this.member = member;
        this.feedbackText = feedbackText;
        this.rating = rating;
    }

    public String getFeedbackText() {
        return feedbackText;  // Returns the feedback text
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", book=" + book +
                ", member=" + member +
                ", comments='" + feedbackText + '\'' +
                ", rating=" + rating +
                '}';
    }
}