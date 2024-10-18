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
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.User;

/**
 * FXML Controller class
 *
 * @author Kelian and Enzo
 */
public class SignUpViewController {

    /**
     * TextField for email
     */
    @FXML
    private TextField emailTxf;

    /**
     * TextField for full name
     */
    @FXML
    private TextField fullNameTxf;

    /**
     * TextField for password (visible)
     */
    @FXML
    private TextField passwordTxf;

    /**
     * PasswordField for password (hidden)
     */
    @FXML
    private PasswordField passwordPwdf;

    /**
     * TextField for retry password (visible)
     */
    @FXML
    private TextField retryPasswordTxf;

    /**
     * PasswordField for retry password (hidden)
     */
    @FXML
    private PasswordField repeatPasswordPwdf;

    /**
     * TextField for street
     */
    @FXML
    private TextField streetTxf;

    /**
     * TextField for city
     */
    @FXML
    private TextField cityTxf;

    /**
     * TextField for zip
     */
    @FXML
    private TextField zipTxf;

    /**
     * Button to show password
     */
    @FXML
    private Button buttonEye = new Button();

    /**
     * Button to show password in retry password
     */
    @FXML
    private Button retryButtonEye = new Button();

    /**
     * CheckBox to know if the user is active or not
     */
    @FXML
    private CheckBox checkActive;

    /**
     * Button to sign up
     */
    @FXML
    private Button signUpButton;

    /**
     * Button to return to the main window
     */
    @FXML
    private Button returnButton;
    
    
    @FXML
    private final Image passwd = new Image(getClass().getResourceAsStream("/resources/SinVerContraseña.png"));
    
    @FXML
    private final Image showingPasswd = new Image(getClass().getResourceAsStream("/resources/ViendoContraseña.png"));

    @FXML
    private ImageView buttonImgView;

    @FXML
    private ImageView repeatbuttonImgView;

    /**
     * Logger to show the steps of the application in the console
     */
    private Logger logger = Logger.getLogger(SignUpViewController.class.getName());

    /**
     * Stage for the view
     */
    private Stage stage;
    
    private boolean passwordVisible = false;
    
    private boolean repeatpasswordVisible = false;

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

        buttonImgView = new ImageView(passwd);
        repeatbuttonImgView = new ImageView(passwd);
        
        //set window's events handlesrs
        stage.setOnShowing(this::handleWindowShowing);
        //show primary window
        stage.show();
    }

    /**
     * Method that handles the events that occur before the window opens
     *
     * @param event
     */
    public void handleWindowShowing(WindowEvent event) {

        logger.info("Beginning SignUpViewController::handleWindowShowing");
        // hide the password textfields
        passwordTxf.setVisible(false);
        passwordTxf.setManaged(false);
        retryPasswordTxf.setVisible(false);
        retryPasswordTxf.setManaged(false);
        // to write in both passwordFields and textFields at the same time
        passwordTxf.textProperty().bindBidirectional(passwordPwdf.textProperty());
        retryPasswordTxf.textProperty().bindBidirectional(repeatPasswordPwdf.textProperty());
        //set the ImageView un the button
        buttonEye.setGraphic(buttonImgView);
        retryButtonEye.setGraphic(repeatbuttonImgView);

    }

    public void handleButtonAction(ActionEvent event) {
        try {
            User user = SocketFactory.getSignable().signUp();

            // Set IDs for the fields (this may depend on how you're using them)
            emailTxf.setId("email");
            fullNameTxf.setId("fullName");
            passwordTxf.setId("password");
            passwordPwdf.setId("password");
            retryPasswordTxf.setId("password");
            repeatPasswordPwdf.setId("password");
            streetTxf.setId("street");
            cityTxf.setId("city");
            zipTxf.setId("zip");
            checkActive.setId("active");

        } catch (Exception e) {
            logger.severe("Error during sign-up: " + e.getMessage());
            // Handle the error appropriately (show an alert, log it, etc.)
        }
    }

    public void showPassword(ActionEvent event) {

        if (!passwordVisible) {
            buttonImgView.setImage(showingPasswd);
            passwordPwdf.setVisible(false);
            passwordPwdf.setManaged(false);
            passwordTxf.setVisible(true);
            passwordTxf.setManaged(true);
            passwordVisible = true;
        } else {
            buttonImgView.setImage(passwd);
            passwordTxf.setVisible(false);
            passwordTxf.setManaged(false);
            passwordPwdf.setVisible(true);
            passwordPwdf.setManaged(true);
            passwordVisible = false;
        }
    }

    public void retryShowPassword(ActionEvent event) {

        if (!repeatpasswordVisible) {
            repeatbuttonImgView.setImage(showingPasswd);
            repeatPasswordPwdf.setVisible(false);
            repeatPasswordPwdf.setManaged(false);
            retryPasswordTxf.setVisible(true);
            retryPasswordTxf.setManaged(true);
            passwordVisible = true;
        } else {
            repeatbuttonImgView.setImage(passwd);
            retryPasswordTxf.setVisible(false);
            retryPasswordTxf.setManaged(false);
            repeatPasswordPwdf.setVisible(true);
            repeatPasswordPwdf.setManaged(true);
            passwordVisible = false;
        }
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
