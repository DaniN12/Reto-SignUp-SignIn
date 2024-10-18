/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.model;

import exceptions.EmptyFieldException;
import exceptions.IncorrectPasswordException;
import exceptions.IncorrectPatternException;
import model.Signable;
import model.User;

/**
 *
 * @author 2dam
 */
public class Client implements Signable{

    @Override
    public User signIn() throws EmptyFieldException, IncorrectPasswordException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User signUp() throws EmptyFieldException, IncorrectPasswordException, IncorrectPatternException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
}
