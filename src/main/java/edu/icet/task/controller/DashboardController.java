package edu.icet.task.controller;

import edu.icet.task.config.UserSession;
import edu.icet.task.model.dto.UserDTO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    UserDTO userDTO = new UserDTO();
    Stage stage = new Stage();
    @FXML
    private Circle imgLargeProfile;

    @FXML
    private Circle imgNavProfile;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblFullName;

    @FXML
    private Label lblNavName;

    @FXML
    void btnLogoutOnAction(ActionEvent event) {
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/login-form.fxml"))));
            stage.setTitle("Login Form");
            stage.show();
        }catch (IOException _){}
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userDTO = UserSession.getInstance().getLoggedUser();

        lblFullName.setText(userDTO.getFirstName());
        lblNavName.setText(userDTO.getFirstName());
        lblEmail.setText(userDTO.getEmail());

        try {
            String imagePath = userDTO.getImageUrl();
            if (imagePath != null && !imagePath.isEmpty()) {
                Image profileImg = new Image(imagePath, true);
                //for load image to circle
                profileImg.progressProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue.doubleValue() == 1.0) { // when 100% complete
                        Platform.runLater(() -> {
                            ImagePattern pattern = new ImagePattern(profileImg);
                            imgLargeProfile.setFill(pattern);
                            imgNavProfile.setFill(pattern);
                        });
                    }
                });
            }
        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
}
