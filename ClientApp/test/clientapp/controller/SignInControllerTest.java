/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controller;

import clientapp.Main;
import javafx.stage.Stage;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 *
 * @author 2dam
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignInControllerTest extends ApplicationTest{
    
    @Override
    public void start (Stage stage) throws Exception{
        new Main().start(stage);
    }
    
    public SignInControllerTest() {
    }

    @Test
    public void testSomeMethod() {
        clickOn("#txtFieldEmail");
        write("test@gmail.com");
        clickOn("#PasswordField");
        write("abcd*1234");
        /*verifyThat("#logOutBtn", isVisible());*/
    }
    
     @Test
    public void testCredentials() {
        clickOn("#txtFieldEmail");
        write("test@gmail.com");
        clickOn("#PasswordField");
        write("");
        clickOn("#btnSignIn");
        verifyThat("ERROR: Invalid email format. Please enter a valid Gmail address", isVisible());
        clickOn("Aceptar");
    }
    
}
