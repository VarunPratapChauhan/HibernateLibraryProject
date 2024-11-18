package com.hibernateLibraryProject.dao;

import com.hibernateLibraryProject.model.Member;
import com.hibernateLibraryProject.config.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
public class MemberDAO {
    public void saveMember(Member member) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(member);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Member getMemberById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Member.class, id);
        }
    }

    public List<Member> getAllMembers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Member", Member.class).list();
        }
    }
}
