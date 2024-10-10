/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author 2dam
 */
public class SignInController implements Initializable {
    
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    // Método que se ejecuta cuando el botón "Sign In" es presionado
    @FXML
    protected void handleSignIn() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Verificar si los campos están vacíos
        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please enter both username and password.");
        } else {
            // Aquí puedes añadir la lógica para validar las credenciales
            if (validateCredentials(username, password)) {
                errorLabel.setText("Sign in successful!");
            } else {
                errorLabel.setText("Invalid username or password.");
            }
        }
    }

    // Método que simula la validación de credenciales
    private boolean validateCredentials(String username, String password) {
        // Ejemplo simple: validar si el usuario y la contraseña son "admin"
        return username.equals("admin") && password.equals("admin");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
