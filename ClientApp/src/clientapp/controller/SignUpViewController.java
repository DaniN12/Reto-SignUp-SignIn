/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controller;

import clientapp.model.SocketFactory;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.User;
import clientapp.exceptions.EmptyFieldException;
import clientapp.exceptions.IncorrectPasswordException;
import clientapp.exceptions.IncorrectPatternException;
import exceptions.UserAlreadyExistException;
import exceptions.UserDoesntExistExeption;
import java.io.IOException;
import java.util.logging.Level;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * FXML Controller class of the signUp window
 *
 * @author Kelian and Enzo
 */
public class SignUpViewController {

    /**
     * textField for email
     */
    @FXML
    private TextField emailTxf;

    /**
     * textField for full name
     */
    @FXML
    private TextField fullNameTxf;

    /**
     * textField for password
     */
    @FXML
    private TextField passwordTxf;

    /**
     * passwordField for password
     */
    @FXML
    private PasswordField passwordPwdf;

    /**
     * textField for retrypassword
     */
    @FXML
    private TextField retryPasswordTxf;

    /**
     * passwordField for retry password
     */
    @FXML
    private PasswordField repeatPasswordPwdf;

    /**
     * textField for street
     */
    @FXML
    private TextField streetTxf;

    /**
     * textField for city
     */
    @FXML
    private TextField cityTxf;

    /**
     * textField for zip
     */
    @FXML
    private TextField zipTxf;

    /**
     * button to show password
     */
    @FXML
    private Button buttonEye;

    /**
     * button to show password in retry password
     */
    @FXML
    private Button retryButtonEye;

    /**
     * checkBox to know if the user is active or not
     */
    @FXML
    private CheckBox checkActive;

    /**
     * button to signUp
     */
    @FXML
    private Button signUpButton;

    /**
     * button to return to the main window
     */
    @FXML
    private Button returnButton;

    /**
     * logger to show the steps of the application by console
     */
    private Logger logger = Logger.getLogger(SignUpViewController.class.getName());

    /**
     * stage for the view
     */
    private Stage stage;

    /**
     * Initializes the controller class.
     */
    public void initialize(Parent root) {

        logger.info("Initializing SignUp stage.");
        //create a scene associated the node graph root
        Scene scene = new Scene(root);
        //Associate scene to primaryStage(Window)
        stage.setScene(scene);
        //set window properties
        stage.setTitle("Sign Up");
        stage.setResizable(false);
        //set window's events handlesrs
        stage.setOnShowing(this::handleWindowShowing);
        //show primary window
        stage.show();
    }

    /**
     * method that handles the events that occur before the window opens
     *
     * @param event triggers an action, in this case a window opening
     */
    public void handleWindowShowing(WindowEvent event) {

        logger.info("Beginning SignUpViewController::handleWindowShowing");
        // hide the password textfields
        passwordTxf.setVisible(false);
        retryPasswordTxf.setVisible(false);
        // to write in both passwordFields and textFields at the same time
        passwordTxf.textProperty().bindBidirectional(passwordPwdf.textProperty());
        retryPasswordTxf.textProperty().bindBidirectional(repeatPasswordPwdf.textProperty());

    }

    /**
     * This method handles the event that occurs when the button signUp is
     * clicked and makes sure that all the conditions to register a user are met
     *
     * @param event triggers the action, in this case a button click
     * @throws UserAlreadyExistException checks if the user already exits
     * @throws UserDoesntExistExeption checks if the user doesn't exist
     */
    @FXML
    public void handleButtonAction(ActionEvent event) throws UserAlreadyExistException, UserDoesntExistExeption {

        try {
            User user = SocketFactory.getSignable().signUp();

            emailTxf.setId("email");
            fullNameTxf.setId("fullName");
            passwordTxf.setId("password");
            passwordPwdf.setId("password");
            streetTxf.setId("street");
            cityTxf.setId("city");
            zipTxf.setId("zip");
            checkActive.setId("active");

            if (emailTxf.getText().isEmpty() || fullNameTxf.getText().isEmpty() || passwordTxf.getText().isEmpty() || passwordPwdf.getText().isEmpty() || retryPasswordTxf.getText().isEmpty() || repeatPasswordPwdf.getText().isEmpty() || streetTxf.getText().isEmpty() || cityTxf.getText().isEmpty()) {

                throw new EmptyFieldException("Fields are empty, all filds need to be filled");

            } else if (!passwordTxf.getText().equalsIgnoreCase(retryPasswordTxf.getText()) && !passwordPwdf.getText().equalsIgnoreCase(repeatPasswordPwdf.getText())) {

                throw new IncorrectPasswordException("The password fields do not match");

            } else if (!emailTxf.getText().matches("^[A-Za-z0-9._%+-]+@gmail\\.com$")) {

                throw new IncorrectPatternException("The email has to have a email format, don't forget the @");
            } else if (!zipTxf.getText().matches("\\d+")) {

                throw new IncorrectPatternException("The zip has to be an Integer");
            }

        } catch (IncorrectPasswordException ex) {
            Logger.getLogger(SignUpViewController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK).showAndWait();
        } catch (IncorrectPatternException ex) {
            Logger.getLogger(SignUpViewController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK).showAndWait();
        } catch (EmptyFieldException ex) {
            Logger.getLogger(SignUpViewController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK).showAndWait();
        }

    }

    @FXML
    public void backButtonAction(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/clientapp/view/SignInView.fxml"));

            Parent root = (Parent) loader.load();

            SignInController controller = loader.getController();
            if (controller == null) {
                throw new RuntimeException("Failed to load SignInController");
            }

            if (stage == null) {
                throw new RuntimeException("Stage is not initialized");
            }

            controller.setStage(stage);
            controller.initialize(root);

        } catch (IOException ex) {
            Logger.getLogger(SignUpViewController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error loading SignInView.fxml", ButtonType.OK).showAndWait();
        } catch (RuntimeException ex) {
            Logger.getLogger(SignUpViewController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
        }
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
