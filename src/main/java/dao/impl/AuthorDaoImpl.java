package dao.impl;

import dao.Dao;
import model.Author;
import org.hibernate.*;

import java.util.List;

public class AuthorDaoImpl implements Dao<Author> {

    private final SessionFactory sessionFactory;

    public AuthorDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Author findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Author.class, id);
        }
    }

    @Override
    public void save(Author author) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(author);
            transaction.commit();
            session.close();
        }
    }

    @Override
    public void update(Author author) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(author);
            transaction.commit();
            session.close();
        }
    }

    @Override
    public void delete(Author author) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(author);
            transaction.commit();
            session.close();
        }
    }

    @Override
    public List<Author> findAll() {
        return (List<Author>) sessionFactory.openSession().createQuery("From Author").list();
    }
}
