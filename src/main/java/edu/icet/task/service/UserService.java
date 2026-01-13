package edu.icet.task.service;

import edu.icet.task.model.dto.UserDTO;

public interface UserService {
    boolean createNewAccount(UserDTO userDTO);
    boolean checkPassword(String password, String conformPassword);
    boolean checkEmail(String email);
    UserDTO getUser(String email);
}
