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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ruth
 */
public class InfoViewController {
    /**
     * Button used to log out user sesion
     */
    
    @FXML
    private Button logOutBtn;

    /**
     * Field where the email will be displayed
     */
    
    @FXML
    private TextField emailTextF;

    /**
     *Field where the street will be displayed
     */
    
    @FXML
    private TextField streetTextF;

    /**
     *Field where the name will be displayed
     */
    
    @FXML
    private TextField userrNameTextF;

    /**
     *Field where the city will be displayed
     */
    
    @FXML
    private TextField citylTextF;

    /**
     *Field where the zip will be displayed
     */
    
    @FXML
    private TextField zipTextF;
    
       /**
     * logger to show the steps of the application by console
     */
    private Logger logger = Logger.getLogger(InfoViewController.class.getName());

    /**
     * stage for the view
     */
    private Stage stage;
    
    
    @FXML
    public void backButtonAction(ActionEvent event) { 
        try {
            // Load DOM form FXML view
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/clientapp/view/SignInView.fxml"));
            Parent root = (Parent) loader.load();
            
            SignInController controller = loader.getController();
            if (controller == null) {
                throw new RuntimeException("Failed to load SignInController");
            }
            
            if (stage == null) {
                throw new RuntimeException("Stage is not initialized");
            }
            
            controller.setStage(stage);
            controller.initialize(root);
            
        } catch (IOException ex) {
            Logger.getLogger(SignUpViewController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error loading SignInView.fxml", ButtonType.OK).showAndWait();
        } catch (RuntimeException ex) {
            Logger.getLogger(SignUpViewController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
        }
    }

      @FXML
    public void onCloseRequest(WindowEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
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
     * Initializes the controller class.
     */
    public void initialize(Parent root) {
        logger.info("Initializing InfoView stage.");
        //create a scene associated the node graph root
        Scene scene = new Scene(root);
        //Associate scene to primaryStage(Window)
        stage.setScene(scene);
        //set window properties
        stage.setTitle("User info");
        stage.setResizable(false);
        //set window's events handlesrs
        stage.setOnCloseRequest(this::onCloseRequest);
        //show primary window
        stage.show();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
}

