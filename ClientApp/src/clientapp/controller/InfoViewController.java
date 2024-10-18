/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author 2dam
 */
public class InfoViewController implements Initializable {

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
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }

}
