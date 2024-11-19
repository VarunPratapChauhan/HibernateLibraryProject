package com.hibernateLibraryProject.dao;

import com.hibernateLibraryProject.config.HibernateUtil;
import com.hibernateLibraryProject.model.BorrowRecord;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class BorrowRecordDAO {

    // Fetch a BorrowRecord by its ID
    public BorrowRecord getBorrowRecordById(int borrowRecordId) {
        BorrowRecord borrowRecord = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            borrowRecord = session.get(BorrowRecord.class, borrowRecordId); // Fetch BorrowRecord by ID
        } catch (Exception e) {
            System.err.println("Error fetching BorrowRecord by ID: " + borrowRecordId);
            e.printStackTrace();
        }
        return borrowRecord;
    }

    // Fetch a BorrowRecord by bookId and memberId
//    public List<BorrowRecord> getBorrowRecordsByBookIdAndMemberId(int bookId, int memberId) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            String hql = "FROM BorrowRecord WHERE book.id = :bookId AND member.id = :memberId";
//            Query<BorrowRecord> query = session.createQuery(hql, BorrowRecord.class);
//            query.setParameter("bookId", bookId);
//            query.setParameter("memberId", memberId);
//            return query.list();
//        }
//    }

    public List<BorrowRecord> getBorrowRecordByBookIdAndMemberId(int bookId, int memberId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM BorrowRecord br WHERE br.book.id = :bookId AND br.member.id = :memberId";
            Query<BorrowRecord> query = session.createQuery(hql, BorrowRecord.class);
            query.setParameter("bookId", bookId);
            query.setParameter("memberId", memberId);
            return query.getResultList();
        }
    }

    // Issue a book (save a BorrowRecord)
    public void issueBook(BorrowRecord borrowRecord) {
        Transaction hTransaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            hTransaction = session.beginTransaction();
            session.save(borrowRecord);
            hTransaction.commit();
        } catch (Exception e) {
            if (hTransaction != null) {
                hTransaction.rollback();
            }
            System.err.println("Error issuing book with BorrowRecord: " + borrowRecord);
            e.printStackTrace();
        }
    }

    // Fetch all BorrowRecords
    public List<BorrowRecord> getBorrowRecords() {
        List<BorrowRecord> borrowRecords = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            borrowRecords = session.createQuery("FROM BorrowRecord", BorrowRecord.class).list();
        } catch (Exception e) {
            System.err.println("Error fetching all BorrowRecords");
            e.printStackTrace();
        }
        return borrowRecords;
    }

    // Fetch BorrowRecords for a specific book and member
    public List<BorrowRecord> getBorrowRecordsForBookAndMember(int bookId, int memberId) {
        List<BorrowRecord> borrowRecords = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM BorrowRecord br JOIN FETCH br.book b JOIN FETCH br.member m " +
                         "WHERE b.id = :bookId AND m.id = :memberId";
            borrowRecords = session.createQuery(hql, BorrowRecord.class)
                    .setParameter("bookId", bookId)
                    .setParameter("memberId", memberId)
                    .list();
        } catch (Exception e) {
            System.err.println("Error fetching BorrowRecords for bookId: " + bookId + " and memberId: " + memberId);
            e.printStackTrace();
        }
        return borrowRecords;
    }

    // Update a BorrowRecord
    public void updateBorrowRecord(BorrowRecord borrowRecord) {
        Transaction hTransaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            hTransaction = session.beginTransaction();
            session.update(borrowRecord);
            hTransaction.commit();
        } catch (Exception e) {
            if (hTransaction != null) {
                hTransaction.rollback();
            }
            System.err.println("Error updating BorrowRecord: " + borrowRecord);
            e.printStackTrace();
        }
    }
}
