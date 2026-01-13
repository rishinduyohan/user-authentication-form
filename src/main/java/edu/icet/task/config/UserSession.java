package edu.icet.task.config;

import edu.icet.task.model.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSession {
    private static UserSession instance;
    private UserDTO loggedUser;

    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }
    public void cleanUserSession() {
        loggedUser = null;
    }
}
