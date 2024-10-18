/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controller;

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

/**
 * FXML Controller class
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
     * @param event
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

    public void handleButtonAction(ActionEvent event) {
 

    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
