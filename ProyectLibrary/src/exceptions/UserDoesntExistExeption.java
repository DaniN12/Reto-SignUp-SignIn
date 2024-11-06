/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 * Exception thrown when a user does not exist.
 *
 * @author Enzo
 */
public class UserDoesntExistExeption extends Exception {

    /**
     * Constructs a new UserDoesntExistExeption with the specified detail message.
     *
     *
     * @param msg the detail message to be saved with the exception
     */
    public UserDoesntExistExeption(String msg) {
        super(msg);
    }
}
