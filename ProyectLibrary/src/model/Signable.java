/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Kelian
 */
public interface Signable {
    
    public User signIn() throws exceptions.EmptyFieldException, exceptions.IncorrectPasswordException;
    
    public User signUp() throws exceptions.EmptyFieldException, exceptions.IncorrectPasswordException, exceptions.IncorrectPatternException;

}
