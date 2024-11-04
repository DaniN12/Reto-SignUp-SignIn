/**
 * Controller class for the SignIn view, managing user sign-in functionality.
 * It handles user input, displays errors, and initiates the sign-in process.
 * 
 * <p>
 * It verifies user input for the email and password fields, handles errors, and
 * connects to the server to validate the sign-in. Additionally, it includes
 * functionality to toggle password visibility and handles window events, 
 * such as confirmation before exiting the application.
 * </p>
 * 
 * @author Dani
 */


package clientapp.controller;

import clientapp.exceptions.EmptyFieldException;

import java.util.Optional;

import clientapp.exceptions.IncorrectPatternException;
import javafx.scene.image.Image;
import java.io.IOException;
import java.util.logging.Level;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.User;
import clientapp.model.SocketFactory;
import exceptions.ConnectionErrorException;
import exceptions.UserDoesntExistExeption;
import model.Signable;

public class SignInController {

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblPassword;

    @FXML
    private Label lblError;

    @FXML
    private TextField txtFieldEmail;

    @FXML
    private TextField txtFieldPassword;

    @FXML
    private PasswordField PasswordField;

    @FXML
    private Button btnShowPassword = new Button();

    @FXML
    private Button btnSignIn = new Button();

    @FXML
    private Hyperlink HyperLinkRegistered;

    @FXML
    private Label label;

    @FXML
    private boolean passwordVisible = false;

    @FXML
    private ImageView ImageViewEye = new ImageView();

    @FXML
    private Button btnSigIn;

    @FXML
    private TextField usernameField;

    @FXML

    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    private Signable signable;

    private Stage stage;

    private Logger logger = Logger.getLogger(SignInController.class.getName());

    /**
     * Initializes the SignIn view by setting up the stage and its properties.
     *
     * @param root The root node of the scene graph.
     */
    public void initialize(Parent root) {
        logger.info("Initializing SignIn stage.");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Sign In");
        stage.setResizable(false);

        txtFieldPassword.setVisible(false);
        txtFieldPassword.textProperty().bindBidirectional(PasswordField.textProperty());

        ImageViewEye.setImage(new Image(getClass().getResourceAsStream("/resources/SinVerContraseña.png")));

        stage.show();
    }

    /**
     * Handles the sign-in process when the sign-in button is clicked.
     *
     * @param event The action event triggered by clicking the sign-in button.
     * @throws ConnectionErrorException   If there is a connection error.
     * @throws UserDoesntExistExeption    If the user does not exist.
     */
    @FXML
    protected void handleSignIn(ActionEvent event) throws ConnectionErrorException, UserDoesntExistExeption {
        String email = txtFieldEmail.getText();
        String password = txtFieldPassword.getText();
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        try {
            if (email.isEmpty() || password.isEmpty() || passwordField.getText().isEmpty()) {
                throw new EmptyFieldException("Fields are empty, all fields need to be filled");
            } else if (!emailExists(email)) {
                throw new IncorrectPatternException("The email doesn't exist");
            } else {
                SocketFactory socket = new SocketFactory();
                Signable signable = socket.getSignable();
                signable.signUp(user);
            }
        } catch (EmptyFieldException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            showAlert("Error", "Please fill in all fields.", Alert.AlertType.ERROR);
        } catch (IncorrectPatternException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            showAlert("Error", "The email does not exist. Please check your information or sign up.", Alert.AlertType.ERROR);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "An unexpected error occurred.", ex);
            showAlert("Error", "An unexpected error occurred. Please try again.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Checks if an email exists in the database or system.
     *
     * @param email The email to verify.
     * @return true if the email exists, false otherwise.
     */
    private boolean emailExists(String email) {
        // Logic to check if the email exists in the database or system.
        // This could involve a database query or a service call.
        return false;
    }

    /**
     * Gets the stage associated with this controller.
     *
     * @return The stage.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Sets the stage for this controller.
     *
     * @param stage The stage to set.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Displays an alert to the user with the specified title, message, and alert type.
     *
     * @param title     The title of the alert.
     * @param message   The message to display in the alert.
     * @param alertType The type of alert (ERROR, INFORMATION, etc.).
     */
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Handles the window close request event, displaying a confirmation dialog.
     *
     * @param event The window event.
     */
    @FXML
    public void onCloseRequest(WindowEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("EXIT");
        alert.setContentText("Are you sure you want to close the application?");
        Optional<ButtonType> answer = alert.showAndWait();

        if (answer.get() == ButtonType.OK) {
            Platform.exit();
        } else {
            event.consume();
        }
    }

    /**
     * Opens the SignUpView window when the Hyperlink is clicked.
     *
     * @param event The action event triggered by clicking the hyperlink.
     */
    @FXML
    private void handleHyperLinkAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientapp/view/SignUpView.fxml"));
            Parent root = loader.load();

            SignUpViewController controller = loader.getController();
            if (controller == null || stage == null) {
                throw new RuntimeException("Failed to load SignUpController or stage not initialized.");
            }

            controller.setStage(stage);
            controller.initialize(root);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            showAlert("Error", "Error loading SignInView.fxml", Alert.AlertType.ERROR);
        } catch (RuntimeException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            showAlert("Error", ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Toggles the password visibility when the show password button is clicked.
     *
     * @param event The action event triggered by clicking the show password button.
     */
    public void showPassword(ActionEvent event) {
        if (!passwordVisible) {
            ImageViewEye.setImage(new Image(getClass().getResourceAsStream("/resources/ViendoContraseña.png")));
            PasswordField.setVisible(false);
            txtFieldPassword.setVisible(true);
            passwordVisible = true;
        } else {
            ImageViewEye.setImage(new Image(getClass().getResourceAsStream("/resources/SinVerContraseña.png")));
            txtFieldPassword.setVisible(false);
            PasswordField.setVisible(true);
            passwordVisible = false;
        }
    }
}