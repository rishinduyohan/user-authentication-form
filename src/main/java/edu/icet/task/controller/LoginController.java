package edu.icet.task.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

public class LoginController {

    private boolean isLoginView = true;

    @FXML
    private Button btnLogin;

    @FXML
    private CheckBox chkRememberMe;

    @FXML
    private Circle imgSignupPreview;

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
        //login password
    }

    @FXML
    void btnLoginOnAction(ActionEvent event) {
        //login button
    }

    @FXML
    void btnSelectImageOnAction(ActionEvent event) {
        //image select
    }

    @FXML
    void btnSignUpPasswordOnAction(ActionEvent event) {
        //btn signup password
    }

    @FXML
    void btnSignupOnAction(ActionEvent event) {
        //sign up button
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

}
