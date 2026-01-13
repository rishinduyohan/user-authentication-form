package edu.icet.task.service.impl;

import edu.icet.task.model.dto.UserDTO;
import edu.icet.task.model.entity.User;
import edu.icet.task.repository.UserRepository;
import edu.icet.task.repository.impl.UserRepositoryImpl;
import edu.icet.task.service.UserService;
import javafx.scene.control.Alert;
import org.mindrot.jbcrypt.BCrypt;

public class UserServiceImpl implements UserService {
    UserRepository userRepository = new UserRepositoryImpl();
    User newUser = new User();

    @Override
    public boolean createNewAccount(UserDTO userDTO) {
        newUser = new User();
        try {
            newUser.setFirstName(userDTO.getFirstName());
            newUser.setLastName(userDTO.getLastName());
            newUser.setEmail(userDTO.getEmail());
            newUser.setPassword(getEncryptedPassword(userDTO.getPassword()));
            newUser.setImageUrl(userDTO.getImageUrl());
            return userRepository.addNewUser(newUser);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return false;
    }

    @Override
    public boolean checkPassword(String password, String confirmPassword) {
        try {
            if (!confirmPassword.isEmpty()) {
                return password.equals(confirmPassword);
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return false;
    }

    @Override
    public boolean checkEmail(String email) {
        try {
            return email.endsWith("@gmail.com");
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return false;
    }

    @Override
    public UserDTO getUser(String email) {
        User user = userRepository.getUser(email);
        try {
            if (user != null) {
                return new UserDTO(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getImageUrl());
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return null;
    }

    private String getEncryptedPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
