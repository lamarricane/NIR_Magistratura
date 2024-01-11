package dao.impl;

import dao.Dao;
import model.Publisher;
import org.hibernate.*;

import java.util.List;

public class PublisherDaoImpl implements Dao<Publisher> {
    private final SessionFactory sessionFactory;

    public PublisherDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Publisher findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Publisher.class, id);
        }
    }

    @Override
    public void save(Publisher publisher) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(publisher);
            transaction.commit();
            session.close();
        }
    }

    @Override
    public void update(Publisher publisher) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(publisher);
            transaction.commit();
            session.close();
        }
    }

    @Override
    public void delete(Publisher publisher) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(publisher);
            transaction.commit();
            session.close();
        }
    }

    @Override
    public List<Publisher> findAll() {
        return (List<Publisher>) sessionFactory.openSession().createQuery("From Publisher").list();
    }
}
