package dao.impl;

import dao.Dao;
import model.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class BookDaoImpl implements Dao<Book> {

    private final SessionFactory sessionFactory;

    public BookDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Book findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Book.class, id);
        }
    }

    @Override
    public void save(Book book) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(book);
            transaction.commit();
            session.close();
        }
    }

    @Override
    public void update(Book book) {
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            session.update(book);
            transaction.commit();
            session.close();
        }
    }

    @Override
    public void delete(Book book) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(book);
            transaction.commit();
            session.close();
        }
    }

    @Override
    public List<Book> findAll() {
        Session session = sessionFactory.openSession();
        return (List<Book>) session.createQuery("From Book").list();
    }
}
