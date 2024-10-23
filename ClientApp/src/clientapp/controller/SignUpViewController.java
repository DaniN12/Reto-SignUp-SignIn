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
import clientapp.exceptions.EmptyFieldException;
import clientapp.exceptions.IncorrectPasswordException;
import clientapp.exceptions.IncorrectPatternException;
import exceptions.ConnectionErrorException;
import exceptions.UserAlreadyExistException;
import exceptions.UserDoesntExistExeption;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Signable;

/**
 * FXML Controller class of the signUp window
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

    private Signable sign;

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
        passwordTxf.setVisible(false);
        passwordTxf.setManaged(false);
        retryPasswordTxf.setVisible(false);
        retryPasswordTxf.setManaged(false);
        passwordTxf.textProperty().bindBidirectional(passwordPwdf.textProperty());
        retryPasswordTxf.textProperty().bindBidirectional(repeatPasswordPwdf.textProperty());
        //put the images in the imageviews
        buttonImgView.setImage(new Image(getClass().getResourceAsStream("/resources/SinVerContraseña.png")));
        repeatbuttonImgView.setImage(new Image(getClass().getResourceAsStream("/resources/SinVerContraseña.png")));
        //set window's events handlers
        //on showing doesn't work (stage.setOnShowing(this::handleWindowShowing);)
        stage.setOnCloseRequest(this::onCloseRequest);
        //show primary window
        stage.show();
    }

    /**
     * Method that handles the events that occur before the window opens
     *
     * @param event triggers an action, in this case a window opening
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
        //put the images in the imageviews
        buttonImgView.setImage(new Image(getClass().getResourceAsStream("/resources/SinVerContraseña.png")));
        repeatbuttonImgView.setImage(new Image(getClass().getResourceAsStream("/resources/SinVerContraseña.png")));
    }

    /**
     * This method handles the event that occurs when the button signUp is
     * clicked and makes sure that all the conditions to register a user are met
     *
     * @param event triggers the action, in this case a button click
     * @throws UserAlreadyExistException checks if the user already exits
     * @throws ConnectionErrorException checks if there was an error while
     * connecting with the server
     */
    @FXML
    public void handleButtonAction(ActionEvent event) throws UserAlreadyExistException, ConnectionErrorException {
        try {

            if (emailTxf.getText().isEmpty() || fullNameTxf.getText().isEmpty() || passwordTxf.getText().isEmpty() || passwordPwdf.getText().isEmpty() || retryPasswordTxf.getText().isEmpty() || repeatPasswordPwdf.getText().isEmpty() || streetTxf.getText().isEmpty() || cityTxf.getText().isEmpty()) {

                throw new EmptyFieldException("Fields are empty, all fields need to be filled");

            } else if (!passwordTxf.getText().equalsIgnoreCase(retryPasswordTxf.getText()) && !passwordPwdf.getText().equalsIgnoreCase(repeatPasswordPwdf.getText())) {

                throw new IncorrectPasswordException("The password fields do not match");

            } else if (!emailTxf.getText().matches("^[A-Za-z0-9._%+-]+@gmail\\.com$")) {

                throw new IncorrectPatternException("The email has to have a email format, don't forget the @");
            } else if (!zipTxf.getText().matches("\\d+")) {

                throw new IncorrectPatternException("The zip has to be an Integer");
            } else {
                User user = new User();

                user.setEmail(emailTxf.getText());
                user.setFullName(fullNameTxf.getText());
                user.setPassword(passwordTxf.getText());
                user.setStreet(streetTxf.getText());
                user.setCity(cityTxf.getText());
                user.setZip(Integer.parseInt(zipTxf.getText()));
                user.setActive(checkActive.isSelected());

                SocketFactory socket = new SocketFactory();
                sign = socket.getSignable();
                sign.signUp(user);
                //Create an alert to make sure that the user wants to close the application
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                //set the alert message and title
                alert.setHeaderText(null);
                alert.setTitle("Sign Up");
                alert.setContentText("The new user has been correctly created");

                //create a variable to compare the button type
                Optional<ButtonType> answer = alert.showAndWait();
                if (answer.get() == ButtonType.OK) {
                    emailTxf.setText("");
                    fullNameTxf.setText("");
                    passwordTxf.setText("");
                    passwordPwdf.setText("");
                    retryPasswordTxf.setText("");
                    repeatPasswordPwdf.setText("");
                    streetTxf.setText("");
                    cityTxf.setText("");
                    zipTxf.setText("");
                    event.consume();
                }
            }

        } catch (IncorrectPasswordException ex) {
            // Logs the error and displays an alert messsage
            Logger.getLogger(SignUpViewController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK).showAndWait();
        } catch (IncorrectPatternException ex) {
            // Logs the error and displays an alert messsage
            Logger.getLogger(SignUpViewController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK).showAndWait();
        } catch (EmptyFieldException ex) {
            // Logs the error and displays an alert messsage
            Logger.getLogger(SignUpViewController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK).showAndWait();
        }

    }

    /**
     * This method handles the event that occur when the button to go back to
     * the signIn window is pressed
     *
     * @param event triggers an action, in this case a button click
     */
    @FXML
    public void backButtonAction(ActionEvent event) {

        try {
            // Load DOM form FXML view
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/clientapp/view/SignInView.fxml"));
            Parent root = (Parent) loader.load();
            // Retrieve the controller associated with the view
            SignInController controller = (SignInController) loader.getController();
            //Check if there is a RuntimeException while opening the view
            if (controller == null) {
                throw new RuntimeException("Failed to load SignInController");
            }

            if (stage == null) {
                throw new RuntimeException("Stage is not initialized");
            }
            controller.setStage(stage);
            //Initializes the controller with the loaded view
            controller.initialize(root);

        } catch (IOException ex) {
            // Logs the error and displays an alert messsage
            Logger.getLogger(SignUpViewController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            new Alert(Alert.AlertType.ERROR, "Error loading SignInView.fxml", ButtonType.OK).showAndWait();
        } catch (RuntimeException ex) {
            // Logs the error and displays an alert messsage
            Logger.getLogger(SignUpViewController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
        }
    }

    /**
     * This method handles the close request for the application
     *
     * @param event triggers an action, in this case a close request when the
     * user attemps to close the window
     */
    @FXML
    public void onCloseRequest(WindowEvent event) {

        //Create an alert to make sure that the user wants to close the application
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        //set the alert message and title
        alert.setHeaderText(null);
        alert.setTitle("EXIT");
        alert.setContentText("Are you sure you want to close the application?");

        //create a variable to compare the button type
        Optional<ButtonType> answer = alert.showAndWait();

        //Condition to close the application
        if (answer.get() == ButtonType.OK) {
            //if the answer is ok the app will close
            Platform.exit();
        } else {
            //else the alert will dispose and the user will continue in the app
            event.consume();
        }
    }

    public void showPassword(ActionEvent event) {
        if (!passwordVisible) {
            buttonImgView.setImage(new Image(getClass().getResourceAsStream("/resources/ViendoContraseña.png")));
            passwordPwdf.setVisible(false);
            passwordPwdf.setManaged(false);
            passwordTxf.setVisible(true);
            passwordTxf.setManaged(true);
            passwordVisible = true;
        } else {
            buttonImgView.setImage(new Image(getClass().getResourceAsStream("/resources/SinVerContraseña.png")));
            passwordTxf.setVisible(false);
            passwordTxf.setManaged(false);
            passwordPwdf.setVisible(true);
            passwordPwdf.setManaged(true);
            passwordVisible = false;
        }
    }

    public void retryShowPassword(ActionEvent event) {
        if (!repeatpasswordVisible) {
            repeatbuttonImgView.setImage(new Image(getClass().getResourceAsStream("/resources/ViendoContraseña.png")));
            repeatPasswordPwdf.setVisible(false);
            repeatPasswordPwdf.setManaged(false);
            retryPasswordTxf.setVisible(true);
            retryPasswordTxf.setManaged(true);
            repeatpasswordVisible = true;  // Aquí cambias la variable correcta
        } else {
            repeatbuttonImgView.setImage(new Image(getClass().getResourceAsStream("/resources/SinVerContraseña.png")));
            retryPasswordTxf.setVisible(false);
            retryPasswordTxf.setManaged(false);
            repeatPasswordPwdf.setVisible(true);
            repeatPasswordPwdf.setManaged(true);
            repeatpasswordVisible = false;  // Aquí cambias la variable correcta
        }
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
