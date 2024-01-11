package dao.impl;

import dao.Dao;
import model.WebUser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class WebUserDaoImpl implements Dao<WebUser> {
    private final SessionFactory sessionFactory;

    public WebUserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public WebUser findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(WebUser.class, id);
        }
    }

    @Override
    public void save(WebUser webUser) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(webUser);
            transaction.commit();
            session.close();
        }
    }

    @Override
    public void update(WebUser webUser) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(webUser);
            transaction.commit();
            session.close();
        }
    }

    @Override
    public void delete(WebUser webUser) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(webUser);
            transaction.commit();
            session.close();
        }
    }

    @Override
    public List<WebUser> findAll() {
        return (List<WebUser>) sessionFactory.openSession().createQuery("From WebUser").list();
    }
}