
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controller;

import clientapp.Main;
import clientapp.MainSignUp;
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

    /*
    @Override
    public void start(Stage stage) throws Exception {
        new MainSignUp().start(stage);
    }

     */
    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(MainSignUp.class);
    }

    @Test
    public void test_A_SignUpOK() {
        clickOn("#emailTxf");
        write("user@gmail.com");
        clickOn("#fullNameTxf");
        write("user1");
        clickOn("#passwordPwdf");
        write("abcd*1234");
        clickOn("#repeatPasswordPwdf");
        write("abcd*1234");
        clickOn("#streetTxf");
        write("street1");
        clickOn("#cityTxf");
        write("city1");
        clickOn("#zipTxf");
        write("48170");
        clickOn("#txfPhone");
        write("48170");
        clickOn("#singUpButton");
        verifyThat("#signInpane", isVisible());
    }

    @Test
    public void test_B_SignUpUserAlreadyExistError() {
        clickOn("#HyperLinkRegistered");
        clickOn("#emailTxf");
        write("user@gmail.com");
        clickOn("#fullNameTxf");
        write("manoloSantana");
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
        clickOn("#txfPhone");
        write("48170");
        clickOn("#singUpButton");
        verifyThat("This user already exist", isVisible());
    }

    //@Test
    public void test_C_SignUpEmptyFields() {
        clickOn("#singUpButton");
        verifyThat("Fields are empty, all fields need to be filled", isVisible());
    }

    // @Test
    public void test_D_SignUpPasswordDoesntMatch() {
        clickOn("#emailTxf");
        write("manolo@gmail.com");
        clickOn("#fullNameTxf");
        write("manoloSantana");
        clickOn("#passwordPwdf");
        write("abcd*1234");
        clickOn("#btnShowPasswd");
        clickOn("#repeatPasswordPwdf");
        write("abcd*12345");
        clickOn("#btnShowPasswd2");
        clickOn("#streetTxf");
        write("enara");
        clickOn("#cityTxf");
        write("Zamudio");
        clickOn("#zipTxf");
        write("48170");
        clickOn("#singUpButton");
        verifyThat("The password fields do not match", isVisible());
    }

    // @Test
    public void test_E_SignUpEmailDoesntMatch() {
        clickOn("#emailTxf");
        write("manoloa.com");
        clickOn("#fullNameTxf");
        write("manoloSantana");
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
        clickOn("#singUpButton");
        verifyThat("The email has to have a email format, don't forget the @", isVisible());

    }

    /*  @Test
    public void test_C_SignUpEmptyFields() {
        clickOn("#singUpButton");
        verifyThat("Fields are empty, all fields need to be filled", isVisible());
    }
   // @Test
    public void test_D_SignUpPasswordDoesntMatch() {
        clickOn("#emailTxf");
        write("manolo@gmail.com");
        clickOn("#fullNameTxf");
        write("manoloSantana");
        clickOn("#passwordPwdf");
        write("abcd*1234");
        clickOn("#btnShowPasswd");
        clickOn("#repeatPasswordPwdf");
        write("abcd*12345");
        clickOn("#btnShowPasswd2");
        clickOn("#streetTxf");
        write("enara");
        clickOn("#cityTxf");
        write("Zamudio");
        clickOn("#zipTxf");
        write("48170");
        clickOn("#singUpButton");
        verifyThat("The password fields do not match", isVisible());
    }
    
   // @Test
    public void test_E_SignUpEmailDoesntMatch() {
        clickOn("#emailTxf");
        write("manoloa.com");
        clickOn("#fullNameTxf");
        write("manoloSantana");
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
        clickOn("#singUpButton");
        verifyThat("The email has to have a email format, don't forget the @", isVisible());
    }
    
    //@Test
    public void test_F_SignUpZipDoesntMatch() {
        clickOn("#emailTxf");
        write("manolo@gmail.com");
        clickOn("#fullNameTxf");
        write("manoloSantana");
        clickOn("#passwordPwdf");
        write("abcd*1234");
        clickOn("#repeatPasswordPwdf");
        write("abcd*1234");
        clickOn("#streetTxf");
        write("enara");
        clickOn("#cityTxf");
        write("Zamudio");
        clickOn("#zipTxf");
        write("abcd");
        clickOn("#singUpButton");
        verifyThat("The zip has to be an Integer", isVisible());
    }
     */
}
