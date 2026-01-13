package edu.icet.task.controller;

import edu.icet.task.config.CloudinaryUtil;
import edu.icet.task.config.PasswordValidateUtil;
import edu.icet.task.config.UserSession;
import edu.icet.task.model.dto.UserDTO;
import edu.icet.task.service.UserService;
import edu.icet.task.service.impl.UserServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    UserService userService = new UserServiceImpl();
    Stage stage = new Stage();
    private boolean isLoginView = true;
    private File selectedImageFile;
    private String strongStyle = "-fx-border-color: #22c55e; -fx-border-width: 2; -fx-border-radius: 8;";
    private String weekStyle = "-fx-border-color: #ef4444; -fx-border-width: 2; -fx-border-radius: 8;";
    private String imgUrl;
    private String email;

    @FXML
    private Button btnLogin;

    @FXML
    private CheckBox chkRememberMe;

    @FXML
    private Circle imgSignupPreview;

    @FXML
    private Label lblPasswordStatus;

    @FXML
    private Label lblFooterText;

    @FXML
    private Label lblSubtitle;

    @FXML
    private Label lblTitle;

    @FXML
    private Hyperlink lnkForgot;

    @FXML
    private Hyperlink lnkToggle;

    @FXML
    private AnchorPane mainContent;

    @FXML
    private TextField txtLoginEmail;

    @FXML
    private PasswordField txtLoginPassword;

    @FXML
    private PasswordField txtSignupConfirmPassword;

    @FXML
    private TextField txtSignupEmail;

    @FXML
    private TextField txtSignupFirstName;

    @FXML
    private TextField txtSignupLastName;

    @FXML
    private PasswordField txtSignupPassword;

    @FXML
    private VBox vboxLogin;

    @FXML
    private VBox vboxSignup;

    @FXML
    void btnLogInPasswordOnAction(ActionEvent event) {
        btnLoginOnAction(event);
    }

    @FXML
    void btnLoginOnAction(ActionEvent event) {
        String emailUser = txtLoginEmail.getText();
        String inputPassword = txtLoginPassword.getText();

        UserDTO user = userService.getUser(emailUser);
        if (user != null) {
            if (BCrypt.checkpw(inputPassword, user.getPassword())) {
                UserSession.getInstance().setLoggedUser(user);
                try {
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"))));
                    stage.setTitle("Dashboard Form");
                    stage.show();
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.close();
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid Password!").show();
                clearLoginPage();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "User not found!").show();
        }
    }

    private void clearLoginPage() {
        txtLoginEmail.setText("");
        txtLoginPassword.setText("");
    }

    @FXML
    void btnSelectImageOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        selectedImageFile = fileChooser.showOpenDialog(mainContent.getScene().getWindow());

        if (selectedImageFile != null) {
            Image image = new Image(selectedImageFile.toURI().toString());
            imgSignupPreview.setFill(new ImagePattern(image));
        }
    }

    @FXML
    void btnSignUpPasswordOnAction(ActionEvent event) {
        btnSignupOnAction(event);
    }

    @FXML
    void btnSignupOnAction(ActionEvent event) {
        String password = txtSignupPassword.getText();

        if(!PasswordValidateUtil.isValid(password)) {
            new Alert(Alert.AlertType.ERROR, PasswordValidateUtil.getRequirements()).show();
        }
        if (null!=selectedImageFile){
            imgUrl = CloudinaryUtil.uploadImage(selectedImageFile);
        }
        if (!userService.checkEmail(txtSignupEmail.getText())){
            new Alert(Alert.AlertType.ERROR, "Invalid Email Address!").show();
            return;
        }
        UserDTO userDTO = new UserDTO(txtSignupFirstName.getText(),txtSignupLastName.getText(),email,password,imgUrl);
        if(userService.checkPassword(password,txtSignupConfirmPassword.getText())){
            if (userService.createNewAccount(userDTO)){
                new Alert(Alert.AlertType.INFORMATION, "Registration Successful!").show();
                clearSignUpPage();
                lnkToggleOnAction(null);
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Password and Confirm Password do not match!").show();
        }
    }

    private void clearSignUpPage() {
        txtSignupEmail.setText("");
        txtSignupFirstName.setText("");
        txtSignupLastName.setText("");
        txtSignupPassword.setText("");
        txtSignupConfirmPassword.setText("");
        txtSignupEmail.setStyle("");
        txtSignupPassword.setStyle("");
        txtSignupConfirmPassword.setStyle("");

    }

    @FXML
    void lnkForgotOnAction(ActionEvent event) {
        //forgot email
    }

    @FXML
    void lnkToggleOnAction(ActionEvent event) {
        if (isLoginView) {
            vboxLogin.setVisible(false);
            vboxLogin.setManaged(false);
            vboxSignup.setVisible(true);
            vboxSignup.setManaged(true);
            lblTitle.setText("Create a new account");
            lblFooterText.setText("Already have an account?");
            lnkToggle.setText("Login");
        } else {
            vboxSignup.setVisible(false);
            vboxSignup.setManaged(false);
            vboxLogin.setVisible(true);
            vboxLogin.setManaged(true);
            lblTitle.setText("Sign in to your account");
            lblFooterText.setText("Don't have an account?");
            lnkToggle.setText("Sign Up");
        }
        isLoginView = !isLoginView;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkEmailAndPasswordComplexity();
    }

    private void checkEmailAndPasswordComplexity() {
        txtSignupEmail.textProperty().addListener((observable ,oldValue,newVale)->{
            validateEmail();
        });
        txtSignupPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            validateRealTime();
        });
        txtSignupConfirmPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            validateRealTime();
        });
    }

    private void validateEmail() {
        email = txtSignupEmail.getText();
        if (userService.checkEmail(email)){
            txtSignupEmail.setStyle(strongStyle);
        }else{
            txtSignupEmail.setStyle(weekStyle);
        }
    }

    private void validateRealTime() {
        String password = txtSignupPassword.getText();
        String confirmPassword = txtSignupConfirmPassword.getText();

        if (PasswordValidateUtil.isValid(password)) {
            txtSignupPassword.setStyle(strongStyle);
            lblPasswordStatus.setText("Strong Password");
            lblPasswordStatus.setStyle("-fx-text-fill: #22c55e;");
        } else {
            txtSignupPassword.setStyle(weekStyle);
            lblPasswordStatus.setText("Weak Password");
            lblPasswordStatus.setStyle("-fx-text-fill: #ef4444;");
        }

        if (userService.checkPassword(password, confirmPassword)) {
            txtSignupConfirmPassword.setStyle(strongStyle);
        } else {
            txtSignupConfirmPassword.setStyle(weekStyle);
        }
    }
}
