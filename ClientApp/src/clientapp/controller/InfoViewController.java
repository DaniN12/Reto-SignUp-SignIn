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
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.User;

/**
 * FXML Controller class
 *
 * @author ruth
 */
public class InfoViewController {

    /**
     * Button used to log out user sesion
     * Button to log out of the application
     */
    @FXML
    private Button logOutBtn;

    /**
     * TextField to show user email
     */
    @FXML
    private TextField emailTextF;

    /**
     * TextField to show user street
     */
    @FXML
    private TextField streetTextF;

    /**
     * TextField to show user name
     */
    @FXML
    private TextField userNameTextF;

    /**
     * TextField to show user city
     */
    @FXML
    private TextField cityTextF;

    /**
     * TextField to show user zip
     */
    @FXML
    private TextField zipTextF;

    /**
     * ImageView for the profile photo
     */
    @FXML
    private ImageView profileImageView = new ImageView();

    /**
     * ContextMenu for the menu
     */
    @FXML
    private ContextMenu contextMenu;

    /**
     * MenuItm for Mordecay option
     */
    @FXML
    private MenuItem optionMordecay;

    /**
     * MenuItm for Cj option
     */
    @FXML
    private MenuItem optionCj;

    /**
     * MenuItm for Rigby option
     */
    @FXML
    private MenuItem optionRigby;

    /**
     * logger to show the steps of the application by console
     * ImageView for Mordecay image
     */
    @FXML
    private ImageView profileImageMordecay;

    /**
     * ImageView for Cj image
     */
    @FXML
    private ImageView profileImageCj;

    /**
     * ImageView for Rigby image
     */
    @FXML
    private ImageView profileImageRigby;

    private Image icon = new Image(getClass().getResourceAsStream("/resources/icon.png"));

    private Logger logger = Logger.getLogger(InfoViewController.class.getName());

    private Stage stage;

     /**
     * Method that initializes the controller class.
     * 
     * @param root the root element for the scene
     * @param user the user data used to populate fields in the UI
     */
    public void initialize(Parent root, User user) {
        logger.info("Initializing InfoView stage.");

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("User info");
        stage.getIcons().add(icon);
        stage.setResizable(false);
        emailTextF.setText(user.getEmail());
        streetTextF.setText(user.getStreet());
        userNameTextF.setText(user.getFullName());
        cityTextF.setText(Optional.ofNullable(user.getCity()).orElse("City not available"));
        zipTextF.setText(user.getZip() != null ? String.valueOf(user.getZip()) : "ZIP not available");

        profileImageMordecay.setVisible(false);
        profileImageCj.setVisible(false);
        profileImageRigby.setVisible(true);

        optionMordecay.setOnAction(this::onOptionMordecay);
        optionCj.setOnAction(this::onOptionCj);
        optionRigby.setOnAction(this::onOptionRigby);

        profileImageView.setOnMouseClicked(this::showContextMenu);
        stage.addEventHandler(WindowEvent.WINDOW_SHOWN, this::handleWindowShowing);
        stage.setOnCloseRequest(this::onCloseRequest);
        stage.show();
    }
    

    /**
     * Method that handles the events that occur before the window opens
     *
     * @param event triggers an action, in this case a window opening
     */
    public void handleWindowShowing(WindowEvent event) {
        User user = fetchUserData();
        emailTextF.setText(user.getEmail());
        streetTextF.setText(user.getStreet());
        userNameTextF.setText(user.getFullName());
        cityTextF.setText(user.getCity());
        zipTextF.setText(String.valueOf(user.getZip()));
    }

    /**
     * Handles the action event for the "Option Mordecay" option in the UI.
     * Displays the image associated with the Mordecay profile
     *
     * @param event action event triggered by the UI component associated with
     * this method.
     */
    @FXML
    private void onOptionMordecay(ActionEvent event) {
        showImage(profileImageMordecay);
    }

    /**
     * Handles the action event for the "Option Cj" option in the UI. Displays
     * the image associated with the Cj profile
     *
     * @param event action event triggered by the UI component associated with
     * this method.
     */
    @FXML
    private void onOptionCj(ActionEvent event) {
        showImage(profileImageCj);
    }

    /**
     * Handles the action event for the "Option Rigby" option in the UI.
     * Displays the image associated with the Rigby profile
     *
     * @param event action event triggered by the UI component associated with
     * this method.
     */
    @FXML
    private void onOptionRigby(ActionEvent event) {
        showImage(profileImageRigby);
    }

    /**
     * Displays the selected profile image and hides the others.
     *
     * @param selectedImage
     */
    private void showImage(ImageView selectedImage) {
        profileImageMordecay.setVisible(false);
        profileImageCj.setVisible(false);
        profileImageRigby.setVisible(false);

        selectedImage.setVisible(true);
    }

    /**
     * Displays the context menu when a right-click is detected on the
     * component.
     *
     * @param event the mouse event that triggers the method
     */
    @FXML
    private void showContextMenu(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY) {
            contextMenu.show(profileImageView, event.getScreenX(), event.getScreenY());
        }
    }

    /**
     * Handles the action event for logging out
     *
     * @param event the action event triggered by pressing the logout button
     * @throws RuntimeException if the SignInController or the stage is not
     * properly initialized
     */
    @FXML
    public void logOutButtonActtion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientapp/view/SignInView.fxml"));
            Parent root = loader.load();

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

    /**
     * This method handles the close request for the application
     *
     * @param event triggers an action, in this case a close request when the
     * user attemps to close the window
     */
    @FXML
    public void onCloseRequest(WindowEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("EXIT");
        alert.setContentText("Are you sure you want to close the application?");

        Optional<ButtonType> answer = alert.showAndWait();

        if (answer.isPresent() && answer.get() == ButtonType.OK) {
            Platform.exit();
        } else {
            event.consume();
        }
    }

    /**
     * Sets the stage for this instance.
     *
     * @param stage the stage to be set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Fetches the user data.
     *
     * @return A User object containing the user data. This will be an empty
     */
    private User fetchUserData() {
        return new User();
    }
}
