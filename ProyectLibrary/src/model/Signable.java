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

    public User signIn(User user) throws exceptions.ConnectionErrorException, exceptions.UserDoesntExistExeption;

    public User signUp(User user) throws exceptions.UserAlreadyExistException, exceptions.ConnectionErrorException;

}
