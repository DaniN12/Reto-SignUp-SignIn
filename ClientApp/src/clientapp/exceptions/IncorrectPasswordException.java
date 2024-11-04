/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.exceptions;

/**
 * Exception thrown when a user enters an incorrect password during authentication.
 *
 * @author Enzo
 */
public class IncorrectPasswordException extends Exception {

    /**
     * Constructs a new IncorrectPasswordException with the specified detail message.
     *
     * @param msg The detail message to display when the exception is thrown.
     */
    public IncorrectPasswordException(String msg) {
        super(msg);
    }
}
