package dao.impl;

import dao.Dao;
import model.Review;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class ReviewDaoImpl implements Dao<Review> {
    private final SessionFactory sessionFactory;

    public ReviewDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Review findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Review.class, id);
        }
    }

    @Override
    public void save(Review review) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(review);
            transaction.commit();
            session.close();
        }
    }

    @Override
    public void update(Review review) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(review);
            transaction.commit();
            session.close();
        }
    }

    @Override
    public void delete(Review review) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(review);
            transaction.commit();
            session.close();
        }
    }

    @Override
    public List<Review> findAll() {
        Session session = sessionFactory.openSession();
        return (List<Review>) session.createQuery("From Review").list();
    }
}
