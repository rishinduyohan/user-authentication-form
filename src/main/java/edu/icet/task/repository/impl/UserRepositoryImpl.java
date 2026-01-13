package edu.icet.task.repository.impl;

import edu.icet.task.config.HibernateUtil;
import edu.icet.task.model.entity.User;
import edu.icet.task.repository.UserRepository;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class UserRepositoryImpl implements UserRepository {
    Session session = HibernateUtil.getSessionFactory().openSession();

    @Override
    public boolean addNewUser(User user) {
        Transaction transaction = session.beginTransaction();
        session.persist(user);
        transaction.commit();
        return true;
    }

    @Override
    public User getUser(String email) {
        Query<User> query = session.createQuery("FROM User WHERE email = :userEmail", User.class);
        query.setParameter("userEmail", email);
        return query.uniqueResult();

    }
}
