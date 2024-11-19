package com.hibernateLibraryProject.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "BorrowRecord")
public class BorrowRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	private Date issueDate;
	private Date returnDate;

	private double fineAmount = 0.0;

	public double getFineAmount() {
		return fineAmount;
	}

	public void setFineAmount(double fineAmount) {
		this.fineAmount = fineAmount;
	}

	private Date dueDate;

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
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

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	@Override
	public String toString() {
		return "BorrowRecord [id=" + id + ", book=" + book + ", member=" + member + ", issueDate=" + issueDate
				+ ", returnDate=" + returnDate + ", fineAmount=" + fineAmount + ", dueDate="
				+ dueDate + "]";
	}

}
