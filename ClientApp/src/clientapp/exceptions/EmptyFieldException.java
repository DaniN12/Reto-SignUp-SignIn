/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.exceptions;

/**
 * Exception thrown when a required field is left empty.
 *
 * @author Enzo
 */
public class EmptyFieldException extends Exception {

    /**
     * Constructs a new EmptyFieldException with the specified detail message.
     *
     * @param msg The detail message to display when the exception is thrown.
     */
    public EmptyFieldException(String msg) {
        super(msg);
    }
}
