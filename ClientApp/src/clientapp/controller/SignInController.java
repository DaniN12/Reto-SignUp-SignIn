/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controller;

import javafx.scene.image.Image;
import java.io.IOException;

import java.util.logging.Level;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import java.util.logging.Logger;
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
import model.User;

/**
 *
 * @author 2dam
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

    private Button btnSigin = new Button();

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

    private Stage stage;

    private Logger logger = Logger.getLogger(SignUpViewController.class.getName());

    public void initialize(Parent root) {

        logger.info("Initializing SignUp stage.");
        //create a scene associated the node graph root
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
        //show primary window
        stage.show();
    }

    // Método que se ejecuta cuando el botón "Sign In" es presionado
    @FXML
    public void handleSignIn() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/clientapp/view/InfoView.fxml"));

        Parent root = (Parent) loader.load();
        InfoViewController controller = (InfoViewController) loader.getController();

        controller.setStage(stage);
        controller.initialize(root);

        String email = txtFieldEmail.getText();
        String password = passwordField.getText();

        // Verificar si los campos están vacíos
        if (txtFieldEmail.getText().isEmpty() && password.isEmpty()) {
            lblError.setText("Please enter both email and password.");
            lblError.setVisible(true);  // Mostrar el mensaje de error
        } else if (txtFieldEmail.getText().isEmpty()) {
            lblError.setText("Please enter your email.");
            lblError.setVisible(true);  // Mostrar el mensaje de error
        } else if (password.isEmpty()) {
            lblError.setText("Please enter your password.");
            lblError.setVisible(true);  // Mostrar el mensaje de error
        } else {
            // Aquí puedes añadir la lógica para validar las credenciales
            if (validateCredentials(txtFieldEmail.getText(), password)) {
                lblError.setText("Sign in successful!");
                lblError.setVisible(false);  // Ocultar el mensaje si el inicio de sesión es exitoso
            } else {
                lblError.setText("Invalid email or password.");
                lblError.setVisible(true);  // Mostrar el mensaje de error
            }
        }
    }

    // Método que simula la validación de credenciales
    private boolean validateCredentials(String email, String password) {
        // Ejemplo simple: validar si el usuario y la contraseña son "admin"
        return email.equals("admin") && password.equals("admin");
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

// Método para abrir la ventana de SignUpView al hacer clic en el Hyperlink
    @FXML
    private void handleHyperLinkAction(ActionEvent event) {
        try {
            // Load DOM form FXML view
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/clientapp/view/SignUpView.fxml"));
            Parent root = (Parent) loader.load();
            // Retrieve the controller associated with the view
            SignUpViewController controller = (SignUpViewController) loader.getController();
            //Check if there is a RuntimeException while opening the view
            if (controller == null) {
                throw new RuntimeException("Failed to load SignUpController");
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
