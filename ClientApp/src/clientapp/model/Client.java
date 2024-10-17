/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.model;

import clientapp.exceptions.EmptyFieldException;
import clientapp.exceptions.IncorrectPasswordException;
import clientapp.exceptions.IncorrectPatternException;
import exceptions.UserAlreadyExistException;
import exceptions.UserDoesntExistExeption;
import model.Signable;
import model.User;

/**
 *
 * @author 2dam
 */
public class Client implements Signable {
    
    

    @Override
    public User signIn() throws UserAlreadyExistException, UserDoesntExistExeption {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User signUp() throws UserAlreadyExistException, UserDoesntExistExeption {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
