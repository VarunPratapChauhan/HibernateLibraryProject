package com.hibernateLibraryProject.dao;

import com.hibernateLibraryProject.model.Transaction;
import com.hibernateLibraryProject.config.HibernateUtil;
import org.hibernate.Session;
import java.util.List;

public class TransactionDAO {
	public void issueBook(Transaction transaction) {
		org.hibernate.Transaction hTransaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			hTransaction = session.beginTransaction();
			session.save(transaction);
			hTransaction.commit();
		} catch (Exception e) {
			if (hTransaction != null)
				hTransaction.rollback();
			e.printStackTrace();
		}
	}

	public List<Transaction> getTransactions() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from Transaction", Transaction.class).list();
		}
	}

	public List<Transaction> getTransactionsForBookAndMember(int bookId, int memberId) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session
					.createQuery("FROM Transaction t JOIN FETCH t.book b JOIN FETCH t.member m "
							+ "WHERE b.id = :bookId AND m.id = :memberId", Transaction.class)
					.setParameter("bookId", bookId).setParameter("memberId", memberId).list();
		}
	}

	// Update a transaction
	public void updateTransaction(Transaction transaction) {
		org.hibernate.Transaction hTransaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			hTransaction = session.beginTransaction();
			session.update(transaction);
			hTransaction.commit();
		} catch (Exception e) {
			if (hTransaction != null)
				hTransaction.rollback();
			e.printStackTrace();
		}
	}
}