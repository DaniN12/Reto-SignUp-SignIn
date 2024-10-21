/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controller;

import javafx.scene.image.Image;
import java.io.IOException;

import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.util.Optional;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
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

        String username = usernameField.getText();
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
            // Cargar el archivo FXML de la nueva ventana (SignUpView)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientapp/view/SignUpView.fxml"));
            Parent root = loader.load();

            // Crear una nueva escena con la nueva vista cargada
            Scene scene = new Scene(root);

            // Obtener la referencia al escenario actual y cerrarlo
            Stage currentStage = (Stage) HyperLinkRegistered.getScene().getWindow();
            currentStage.close();

            // Crear un nuevo escenario (ventana) para la nueva vista
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Sign Up");
            newStage.setResizable(false);

            // Mostrar la nueva ventana
            newStage.show();

        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
            showAlert("Error", "Failed to load SignUpView.fxml", Alert.AlertType.ERROR);
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