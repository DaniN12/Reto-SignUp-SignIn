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

public class InfoViewController {

    @FXML
    private Button logOutBtn;

    @FXML
    private TextField emailTextF;

    @FXML
    private TextField streetTextF;

    @FXML
    private TextField userNameTextF;

    @FXML
    private TextField cityTextF; // Actualizado para coincidir con FXML

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

    @FXML
    private ImageView profileImageMordecay;

    @FXML
    private ImageView profileImageCj;

    @FXML
    private ImageView profileImageRigby;

    private Logger logger = Logger.getLogger(InfoViewController.class.getName());
    private Stage stage;

    @FXML
    private void onOptionMordecay(ActionEvent event) {
        showImage(profileImageMordecay);
    }

    @FXML
    private void onOptionCj(ActionEvent event) {
        showImage(profileImageCj);
    }

    @FXML
    private void onOptionRigby(ActionEvent event) {
        showImage(profileImageRigby);
    }

    private void showImage(ImageView selectedImage) {
        // Oculta todas las imágenes
        profileImageMordecay.setVisible(false);
        profileImageCj.setVisible(false);
        profileImageRigby.setVisible(false);

        // Muestra la imagen seleccionada
        selectedImage.setVisible(true);
    }

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
        alert.setTitle("SALIR");
        alert.setContentText("¿Estás seguro de que quieres cerrar la aplicación?");

        Optional<ButtonType> answer = alert.showAndWait();

        if (answer.isPresent() && answer.get() == ButtonType.OK) {
            Platform.exit();
        } else {
            event.consume();
        }
    }

    public void handleWindowShowing(WindowEvent event) {
        User user = fetchUserData(); // Método para obtener datos reales del usuario
        emailTextF.setText(user.getEmail());
        streetTextF.setText(user.getStreet());
        userNameTextF.setText(user.getFullName());
        cityTextF.setText(user.getCity()); // Actualizado para coincidir con FXML
        zipTextF.setText(String.valueOf(user.getZip()));
    }

    @FXML
    private void showContextMenu(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY) {
            contextMenu.show(profileImageView, event.getScreenX(), event.getScreenY());
        }
    }

    private void changeProfileImage(String imageFile) {
        Image image = new Image(getClass().getResourceAsStream(imageFile));
        profileImageView.setImage(image);
    }

    public void initialize(Parent root) {
        logger.info("Initializing InfoView stage.");

        profileImageMordecay.setVisible(false);
        profileImageCj.setVisible(false);
        profileImageRigby.setVisible(true);

        // Asocia las acciones del menú contextual con los métodos correspondientes
        optionMordecay.setOnAction(this::onOptionMordecay);
        optionCj.setOnAction(this::onOptionCj);
        optionRigby.setOnAction(this::onOptionRigby);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Información del usuario");
        stage.setResizable(false);
        stage.addEventHandler(WindowEvent.WINDOW_SHOWN, this::handleWindowShowing); // Asegúrate de manejar la ventana al mostrar
        stage.setOnCloseRequest(this::onCloseRequest);
        stage.show();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private User fetchUserData() {
        // Implementa la lógica para obtener los datos del usuario, posiblemente de una base de datos o sesión
        return new User(); // Reemplazar con los datos reales del usuario
    }
}