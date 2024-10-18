/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controller;

import java.io.IOException;
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


import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import java.net.URL;
import java.util.ResourceBundle;
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

/**
 *
 * @author 2dam
 */
public class SignInController implements Initializable {
    
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
    private Button btnSigin;
    
    @FXML
    private Hyperlink HyperLinkRegistered;
    
    
    
    /**
     * method that initiates the stage and sets/prepares the values inside of
     * it.
     *
     * @param root
     */
    public void initStage(Parent root) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    // Método que simula la validación de credenciales
    private boolean validateCredentials(String username, String password) {
        // Ejemplo simple: validar si el usuario y la contraseña son "admin"
        return username.equals("admin") && password.equals("admin");
    }

}

