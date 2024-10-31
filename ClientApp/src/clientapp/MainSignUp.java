/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp;

import clientapp.controller.SignUpViewController;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 *
 * @author 2dam
 */
public class MainSignUp extends Application {

    /**
     * Method to open the main window, in this case the signIn window
     *
     * @param stage the main window
     * @throws Exception when the view cannot be found
     */
    @Override
    public void start(Stage stage) throws Exception {

        // Load DOM form FXML view
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/clientapp/view/SignUpView.fxml"));
        Parent root = (Parent) loader.load();
        // Retrieve the controller associated with the view
        SignUpViewController controller = (SignUpViewController) loader.getController();
        controller.setStage(stage);
        //Initializes the controller with the loaded view
        controller.initialize(root);

    }

    /**
     * Launches the application
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}