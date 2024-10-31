/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controller;

import clientapp.MainInfo;
import javafx.stage.Stage;
import org.junit.Test;
import static org.junit.Assert.*;
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
public class InfoViewControllerTest extends ApplicationTest{
    
    @Override
    public void start (Stage stage) throws Exception{
        new MainInfo().start(stage);
    }
    
    @Test
    public void logOutButtonTest(){
        clickOn("#logOutBtn");
        verifyThat("#signInpane", isVisible());
    }
    public InfoViewControllerTest() {
    }

    @Test
    public void testSomeMethod() {
    }
    
}