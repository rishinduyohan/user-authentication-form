package edu.icet.task.repository.impl;

import edu.icet.task.config.HibernateUtil;
import edu.icet.task.model.entity.User;
import edu.icet.task.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserRepositoryImpl implements UserRepository {
    Session session = HibernateUtil.getSessionFactory().openSession();

    @Override
    public boolean addNewUser(User user) {
        Transaction transaction = session.beginTransaction();
        session.persist(user);
        transaction.commit();
        return true;
    }
}
