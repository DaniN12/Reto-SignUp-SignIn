/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controller;

import clientapp.Main;
import java.util.concurrent.TimeoutException;
import javafx.stage.Stage;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 *
 * @author Kelian and Enzo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignUpViewControllerTest extends ApplicationTest {

    /* @Override
    public void start(Stage stage) throws Exception {
        new Main().start(stage);
    }
     */
    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Main.class);
    }

    @Test
    public void test_A_SignUpOK() {
        clickOn("#emailTxf");
        write("user@gmail.com");
        clickOn("#fullNameTxf");
        write("enzo");
        clickOn("#passwordPwdf");
        write("abcd*1234");
        clickOn("#repeatPasswordPwdf");
        write("abcd*1234");
        clickOn("#streetTxf");
        write("enara");
        clickOn("#cityTxf");
        write("Zamudio");
        clickOn("#zipTxf");
        write("48170");

        verifyThat("#signInPane", isVisible());
    }

    @Test
    public void test_B_SignInUserAlreadyExistError() {
        clickOn("#txtFieldEmail");
        write("user@gmail.org");
        clickOn("#PasswordField");
        write("abcd*1234");
        clickOn("#btnSignIn");
        verifyThat("Invalid email format. Please enter a valid Gmail address.", isVisible());

        clickOn("OK");
    }

}