package edu.icet.task.repository;

import edu.icet.task.model.entity.User;

public interface UserRepository {
    boolean addNewUser(User user);
    User getUser(String id);
}
