/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.User;
import clientapp.model.SocketFactory;
import exceptions.ConnectionErrorException;
import exceptions.IncorrectCredentialsException;
import exceptions.MaxUsersException;
import exceptions.UserDoesntExistExeption;
import exceptions.UserNotActiveException;
import javafx.stage.WindowEvent;
import model.Signable;

/**
 *
 * @author Dani and Ruth
 */
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
    private TextField usernameField;

    @FXML

    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    private Signable signable;

    private Stage stage;
    private Logger logger = Logger.getLogger(SignInController.class.getName());

    @FXML
    private SplitPane splitPane;

    public void initialize(Parent root) {

        logger.info("Initializing SignIn stage.");
        //create a scene associated the node graph root
        splitPane = (SplitPane) root;
        splitPane.getDividers().forEach(divider -> divider.positionProperty().addListener((obs, oldPos, newPos)
                -> divider.setPosition(0.15) // Vuelve a fijar la posición si se intenta mover
        ));
        Scene scene = new Scene(root);
        //Associate scene to primaryStage(Window)
        stage.setScene(scene);
        //set window properties
        stage.setTitle("Sign In");
        stage.setResizable(false);
        txtFieldPassword.setVisible(false);
        txtFieldPassword.textProperty().bindBidirectional(PasswordField.textProperty());
        ImageViewEye.setImage(new Image(getClass().getResourceAsStream("/resources/SinVerContraseña.png")));
        //set window's events handlesrs
        //stage.setOnShowing(this::handleWindowShowing);
        HyperLinkRegistered.setOnAction(this::handleHyperLinkAction);
        //show primary window
        stage.show();
    }

    @FXML
    private void handleSignIn(ActionEvent event) throws UserDoesntExistExeption, ConnectionErrorException, UserNotActiveException, IncorrectCredentialsException, MaxUsersException {
        try {
            String email = txtFieldEmail.getText();
            String password = PasswordField.getText(); // Usando solo PasswordField

            // Verificar si los campos están vacíos
            if (email.isEmpty() || password.isEmpty()) {
                throw new EmptyFieldException("Fields are empty, all fields need to be filled");
            } else if (!email.matches("^[A-Za-z0-9._%+-]+@gmail\\.com$")) {
                throw new IncorrectPatternException("The email is not well written or is incorrect");
            }

            User user = new User();
            user.setEmail(email);
            user.setPassword(password);

            Signable signable = SocketFactory.getSignable();
            User signedInUser = signable.signIn(user);

            if (!signedInUser.getPassword().equals(PasswordField.getText())) {
                throw new IncorrectCredentialsException("The email and password do not match");
            } else if (signedInUser != null && signedInUser.getActive() != false) {
                openMainWindow(event, signedInUser);
            } else {
                throw new UserNotActiveException("The user is not active");
            }

        } catch (EmptyFieldException ex) {
            showAlert("Error", ex.getLocalizedMessage(), Alert.AlertType.ERROR);
        } catch (IncorrectPatternException ex) {
            showAlert("Error", ex.getLocalizedMessage(), Alert.AlertType.ERROR);
        } catch (UserDoesntExistExeption ex) {
            // Manejar otras excepciones que puedan surgir
            showAlert("Error", ex.getLocalizedMessage(), Alert.AlertType.ERROR);
        } catch (ConnectionErrorException ex) {
            showAlert("Error", ex.getLocalizedMessage(), Alert.AlertType.ERROR);
        } catch (UserNotActiveException ex) {
            showAlert("Error", ex.getLocalizedMessage(), Alert.AlertType.ERROR);
        } catch (IncorrectCredentialsException ex) {
            showAlert("Error", ex.getLocalizedMessage(), Alert.AlertType.ERROR);
        } catch (MaxUsersException ex) {
            showAlert("Error", ex.getLocalizedMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Método que verifica si el correo electrónico existe.
     *
     * @param email El email a verificar
     * @return true si el email existe, false si no
     */
    private boolean emailExists(String email) {
        // Lógica para verificar si el email existe en la base de datos o sistema
        // Esto puede ser una consulta a la base de datos o una llamada a un servicio
        // Por ahora se devuelve false para demostrar el funcionamiento
        return false;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Método que muestra una alerta al usuario.
     *
     * @param title El título de la alerta.
     * @param message El mensaje de la alerta.
     * @param alertType El tipo de alerta (ERROR, INFORMATION, etc.).
     */
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

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

// Método para abrir la ventana de SignUpView al hacer clic en el Hyperlink
    @FXML
    private void handleHyperLinkAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientapp/view/SignUpView.fxml"));
            Parent root = loader.load();

            SignUpViewController controller = (SignUpViewController) loader.getController();
            if (controller == null) {
                throw new RuntimeException("Failed to load SignUpController");
            }

            // Asegúrate de que `stage` no es nulo antes de pasarla al controlador
            if (stage == null) {
                throw new RuntimeException("Stage is not initialized");
            }
            controller.setStage(stage);  // Asigna el stage antes de inicializar
            controller.initialize(root); // Llama al método initialize con el root cargado
            controller.handleWindowShowing(new WindowEvent(stage, WindowEvent.WINDOW_SHOWING));

        } catch (IOException ex) {
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
            new Alert(Alert.AlertType.ERROR, "Error loading SignUpView.fxml", ButtonType.OK).showAndWait();
        } catch (RuntimeException ex) {
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
            new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
        }
    }

    @FXML
    public void openMainWindow(ActionEvent event, User user) {
        try {
            // Load DOM form FXML view
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/clientapp/view/InfoView.fxml"));
            Parent root = (Parent) loader.load();
            // Retrieve the controller associated with the view
            InfoViewController controller = (InfoViewController) loader.getController();
            //Check if there is a RuntimeException while opening the view
            if (controller == null) {
                throw new RuntimeException("Failed to load InfoViewController");
            }

            if (stage == null) {
                throw new RuntimeException("Stage is not initialized");
            }
            controller.setStage(stage);
            //Initializes the controller with the loaded view
            controller.initialize(root, user);

        } catch (IOException ex) {
            // Logs the error and displays an alert messsage
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
            new Alert(Alert.AlertType.ERROR, "Error loading InfoView.fxml", ButtonType.OK).showAndWait();
        } catch (RuntimeException ex) {
            // Logs the error and displays an alert messsage
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, "Exception occurred", ex);
            new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
        }
    }

    public void showPassword(ActionEvent event) {

        if (!passwordVisible) {
            ImageViewEye.setImage(new Image(getClass().getResourceAsStream("/resources/ViendoContraseña.png")));
            PasswordField.setVisible(false);
            PasswordField.setManaged(false);
            txtFieldPassword.setVisible(true);
            txtFieldPassword.setManaged(true);
            passwordVisible = true;
        } else {
            ImageViewEye.setImage(new Image(getClass().getResourceAsStream("/resources/SinVerContraseña.png")));
            txtFieldPassword.setVisible(false);
            txtFieldPassword.setManaged(false);
            PasswordField.setVisible(true);
            PasswordField.setManaged(true);
            passwordVisible = false;
        }
    }
}
