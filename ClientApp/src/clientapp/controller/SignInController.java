/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controller;

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
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;


/**
 *
 * @author 2dam
 */
public class SignInController  {


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
    private Button btnField;
    
    @FXML
    private Button btnShowPassword;
    
     @FXML
    private Button btnSigIn;
    
    @FXML
    private Hyperlink HyperLinkRegistered;
      
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;
    
      /**
     * logger to show the steps of the application by console
     */
    private Logger logger = Logger.getLogger(SignInController.class.getName());

    /**
     * stage for the view
     */
    private Stage stage;

    // Método que se ejecuta cuando el botón "Sign In" es presionado
    @FXML
    protected void handleSignIn() throws IOException {
         FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/clientapp/view/InfoView.fxml"));

        Parent root = (Parent) loader.load();
        InfoViewController controller = (InfoViewController) loader.getController();

        controller.setStage(stage);
        controller.initialize(root);
       
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

    public void initialize(Parent root) {

        logger.info("Initializing SignIn stage.");
        //create a scene associated the node graph root
        Scene scene = new Scene(root);
        //Associate scene to primaryStage(Window)
        stage.setScene(scene);
        //set window properties
        stage.setTitle("Sign In");
        stage.setResizable(false);
        //set window's events handlesrs
        //stage.setOnShowing(this::handleWindowShowing);
        //show primary window
        stage.show();
    }


    // Método que simula la validación de credenciales
    private boolean validateCredentials(String username, String password) {
        // Ejemplo simple: validar si el usuario y la contraseña son "admin"
        return username.equals("admin") && password.equals("admin");
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}

