package com.hibernateLibraryProject.dao;

import com.hibernateLibraryProject.model.Feedback;
import com.hibernateLibraryProject.config.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class FeedbackDAO {
	
	public void addFeedback(Feedback feedback) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(feedback); // Save the Feedback entity
            transaction.commit();
            System.out.println("Feedback saved: '" + feedback.getFeedbackText() + "' with rating: " + feedback.getRating());
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public List<Feedback> getFeedbackForBook(int bookId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Feedback f WHERE f.book.id = :bookId", Feedback.class)
                    .setParameter("bookId", bookId)
                    .list();
        }
    }

    public List<Feedback> getFeedbackForMember(int memberId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Feedback f WHERE f.member.id = :memberId", Feedback.class)
                    .setParameter("memberId", memberId)
                    .list();
        }
    }
}
