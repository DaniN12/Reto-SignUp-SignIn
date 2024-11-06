/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 * Exception thrown when a user already exists.
 *
 *
 * @author Enzo
 */
public class UserAlreadyExistException extends Exception {

    /**
     * Constructs a new UserAlreadyExistException with the specified detail message.
     *
     *
     * @param msg the detail message to be saved with the exception
     */
    public UserAlreadyExistException(String msg) {
        super(msg);
    }

}
