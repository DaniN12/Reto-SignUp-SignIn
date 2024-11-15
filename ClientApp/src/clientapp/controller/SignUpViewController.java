/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controller;

import clientapp.model.SignableFactory;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import clientapp.exceptions.EmptyFieldException;
import clientapp.exceptions.IncorrectPasswordException;
import clientapp.exceptions.IncorrectPatternException;
import exceptions.ConnectionErrorException;
import exceptions.UserAlreadyExistException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.input.ContextMenuEvent;
import model.Signable;
import model.User;

/**
 * FXML Controller class of the signUp window
 *
 * @author Kelian and Enzo
 */
public class SignUpViewController {

    @FXML
    private SplitPane splitPane;

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

    /**
     * Image view to set the button eye
     */
    @FXML
    private ImageView buttonImgView;

    /**
     * Image view to set the second button eye
     */
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

    /**
     * Boolean for the password change
     */
    private boolean passwordVisible = false;

    /**
     * Boolean for the password change
     */
    private boolean repeatpasswordVisible = false;
    /**
     * Interface of the application
     */
    private Signable signable;

    /**
     * Context menu of the window
     */
    private ContextMenu contextMenu = new ContextMenu();

    /**
     * First menu Item of the menu
     */
    private MenuItem itemResetFields = new MenuItem("Reset fields");

    /**
     * Second menu Item of the menu
     */
    private MenuItem itemBack = new MenuItem("Go back");

    /**
     * Method that initializes the sign up view and sets the event handlers
     *
     * @param root the parent gotten from the previous window
     */
    public void initialize(Parent root) {

        splitPane = (SplitPane) root;
        splitPane.getDividers().forEach(divider -> divider.positionProperty().addListener((obs, oldPos, newPos)
                -> divider.setPosition(0.25) // Vuelve a fijar la posición si se intenta mover
        ));

        logger.info("Initializing SignUp stage.");
        //create a scene associated the node graph root
        Scene scene = new Scene(root);
        //Associate scene to primaryStage(Window)
        stage.setScene(scene);
        //set window properties
        stage.setTitle("Sign Up");
        stage.setResizable(false);
        //set window's events handlers
        stage.setOnShowing(this::handleWindowShowing);
        stage.setOnCloseRequest(this::onCloseRequest);
        buttonEye.setOnAction(this::showPassword);
        retryButtonEye.setOnAction(this::retryShowPassword);
        itemResetFields.setOnAction(this::resetFields);
        itemBack.setOnAction(this::backButtonAction);
        root.setOnContextMenuRequested(this::manejarContextMenu);
        // Generates a signable to get the register method
        signable = SignableFactory.getSignable();
        //show primary window
        stage.show();
    }

    /**
     * Method to create the context menu and set it to be usable in all the
     * window
     *
     * @param event triggers an action, in this case a click on the window
     */
    private void manejarContextMenu(ContextMenuEvent event) {
        logger.info("hola");
        contextMenu.show(splitPane, event.getScreenX(), event.getScreenY());
    }

    /**
     * Method that handles the events that occur before the window opens
     *
     * @param event triggers an action, in this case a window opening
     */
    public void handleWindowShowing(WindowEvent event) {

        logger.info("Beginning event handler::handleWindowShowing");
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
        //Context menu
        contextMenu.getItems().addAll(itemResetFields, itemBack);
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
            // Check if the fields are empty and throws an exception if they are
            if (emailTxf.getText().isEmpty() || fullNameTxf.getText().isEmpty() || passwordTxf.getText().isEmpty()
                    || passwordPwdf.getText().isEmpty() || retryPasswordTxf.getText().isEmpty()
                    || repeatPasswordPwdf.getText().isEmpty() || streetTxf.getText().isEmpty() || cityTxf.getText().isEmpty()) {

                throw new EmptyFieldException("Fields are empty, all fields need to be filled");
                // Checks if the password and retry password do match, if not throws an exception
            } else if (!passwordTxf.getText().equalsIgnoreCase(retryPasswordTxf.getText()) && !passwordPwdf.getText().equalsIgnoreCase(repeatPasswordPwdf.getText())) {

                throw new IncorrectPasswordException("The password fields do not match");
                // Checks if the email format is correct, if not throws an exception
            } else if (!emailTxf.getText().matches("^[A-Za-z0-9]+@[A-Za-z0-9]+\\.[A-Za-z]{2,}$")) {

                throw new IncorrectPatternException("Email has an invalid format.");
                // Checks if the zip format is correct, if not throws an exception
            } else if (!zipTxf.getText().matches("\\d+")) {

                throw new IncorrectPatternException("The zip has to be an Integer");

                // if all the credentials are ok creates a user with the written credentials
            } else {
                // Create a new user
                User user = new User();
                // Set the credentials to the new user
                user.setEmail(emailTxf.getText());
                user.setFullName(fullNameTxf.getText());
                user.setPassword(passwordTxf.getText());
                user.setStreet(streetTxf.getText());
                user.setCity(cityTxf.getText());
                user.setZip(Integer.parseInt(zipTxf.getText()));
                user.setActive(checkActive.isSelected());
                // Execute the register method
                User signedUpUser = signable.signUp(user);

                if (signedUpUser != null) {
                    // if the method is well executed returns to the signIn window
                    backButtonAction(event);
                } else {
                    // if not an exection is thrown to botify the user
                    throw new ConnectionErrorException("An unexpected error occurred.");
                }

            }

        } catch (IncorrectPasswordException ex) {
            // Logs the error and displays an alert messsage
            Logger.getLogger(SignUpViewController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
            new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK).showAndWait();
        } catch (IncorrectPatternException ex) {
            // Logs the error and displays an alert messsage
            Logger.getLogger(SignUpViewController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
            new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK).showAndWait();
        } catch (EmptyFieldException ex) {
            // Logs the error and displays an alert messsage
            Logger.getLogger(SignUpViewController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
            new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK).showAndWait();
        } catch (UserAlreadyExistException ex) {
            Logger.getLogger(SignUpViewController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
            new Alert(Alert.AlertType.ERROR, "This user already exist", ButtonType.OK).showAndWait();
        } catch (ConnectionErrorException ex) {
            Logger.getLogger(SignUpViewController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
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
            Logger.getLogger(SignUpViewController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
            new Alert(Alert.AlertType.ERROR, "Error loading SignInView.fxml", ButtonType.OK).showAndWait();
        } catch (RuntimeException ex) {
            // Logs the error and displays an alert messsage
            Logger.getLogger(SignUpViewController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
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

    /**
     * Method to change the visibility of the password
     *
     * @param event triggers an action, in this case a button click
     */
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

    /**
     * Method to change the visibility of the retryPassword
     *
     * @param event triggers an action, in this case a button click
     */
    public void retryShowPassword(ActionEvent event) {
        if (!repeatpasswordVisible) {
            repeatbuttonImgView.setImage(new Image(getClass().getResourceAsStream("/resources/ViendoContraseña.png")));
            repeatPasswordPwdf.setVisible(false);
            repeatPasswordPwdf.setManaged(false);
            retryPasswordTxf.setVisible(true);
            retryPasswordTxf.setManaged(true);
            repeatpasswordVisible = true;
        } else {
            repeatbuttonImgView.setImage(new Image(getClass().getResourceAsStream("/resources/SinVerContraseña.png")));
            retryPasswordTxf.setVisible(false);
            retryPasswordTxf.setManaged(false);
            repeatPasswordPwdf.setVisible(true);
            repeatPasswordPwdf.setManaged(true);
            repeatpasswordVisible = false;
        }
    }

    /**
     * Method to reset all the fields in the window
     *
     * @param event triggers an action, in this case a button click
     */
    public void resetFields(ActionEvent event) {
        // Limpiar los campos de texto
        emailTxf.clear();
        fullNameTxf.clear();
        passwordTxf.clear();
        passwordPwdf.clear();
        retryPasswordTxf.clear();
        repeatPasswordPwdf.clear();
        streetTxf.clear();
        cityTxf.clear();
        zipTxf.clear();
    }

    /**
     * Gets the stage of the window
     *
     * @return the stage of the window
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Set's the window's stage
     *
     * @param stage represents the changed stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
