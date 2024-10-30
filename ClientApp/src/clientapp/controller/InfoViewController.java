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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.User;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

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
     * Field where the street will be displayed
     */
    @FXML
    private TextField streetTextF;

    /**
     * Field where the name will be displayed
     */
    @FXML
    private TextField userNameTextF;

    /**
     * Field where the city will be displayed
     */
    @FXML
    private TextField cityTextF;

    /**
     * Field where the zip will be displayed
     */
    @FXML
    private TextField zipTextF;

    @FXML
    private ImageView profileImageView;

    @FXML
    private ContextMenu contextMenu;

    @FXML
    private MenuItem optionMordecay;

    @FXML
    private MenuItem optionCj;

    @FXML
    private MenuItem optionRigby;

    /**
     * logger to show the steps of the application by console
     */
    private Logger logger = Logger.getLogger(InfoViewController.class.getName());

    /**
     * stage for the view
     */
    private Stage stage;

    @FXML
    public void logOutButtonActtion(ActionEvent event) {
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
            Logger.getLogger(SignUpViewController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
            new Alert(Alert.AlertType.ERROR, "Error loading SignInView.fxml", ButtonType.OK).showAndWait();
        } catch (RuntimeException ex) {
            Logger.getLogger(SignUpViewController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
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

    /* public void handleWindowShowing(WindowEvent event, User user) {
        emailTextF.setText(user.getEmail());
        streetTextF.setText(user.getStreet());
        userNameTextF.setText(user.getFullName());
        citylTextF.setText(user.getCity());
        zipTextF.setText(String.valueOf(user.getZip()));
    }
     */
    private void showContextMenu(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY) { // Clic derecho
            contextMenu.show(profileImageView, event.getScreenX(), event.getScreenY());
        }
    }

    private void changeProfileImage(String imageFile) {
        Image image = new Image(getClass().getResourceAsStream("/resources/" + imageFile));
        profileImageView.setImage(image);
    }

    /**
     * Initializes the controller class.
     */
    public void initialize(Parent root, User user) {
        logger.info("Initializing InfoView stage.");
        //create a scene associated the node graph root
        Scene scene = new Scene(root);
        //Associate scene to primaryStage(Window)
        stage.setScene(scene);
        //set window properties
        stage.setTitle("User info");
        stage.setResizable(false);
        emailTextF.setText(user.getEmail());
        streetTextF.setText(user.getStreet());
        userNameTextF.setText(user.getFullName());
        cityTextF.setText(Optional.ofNullable(user.getCity()).orElse("City not available"));
        zipTextF.setText(user.getZip() != null ? String.valueOf(user.getZip()) : "ZIP not available");
        // stage.addEventHandler(WindowEvent.WINDOW_SHOWN, this::handleWindowShowing);

        Image rigbyImage = new Image(getClass().getResourceAsStream("/resources/rigby.png"));
        profileImageView.setImage(rigbyImage);

        // Asignar el evento de clic derecho para mostrar el ContextMenu
        profileImageView.setOnMouseClicked(this::showContextMenu);

        // Asignar eventos de cambio de imagen a cada opción del menú
        optionMordecay.setOnAction(event -> changeProfileImage("mordecay.png"));
        optionCj.setOnAction(event -> changeProfileImage("cj.png"));
        optionRigby.setOnAction(event -> changeProfileImage("rigby.png"));

        //set window's events handlesrs
        stage.setOnCloseRequest(this::onCloseRequest);
        //show primary window
        stage.show();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
