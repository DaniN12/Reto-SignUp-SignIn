/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.exceptions;

/**
 * Exception thrown when a provided input does not match the expected pattern.
 *
 * @author Kelian
 */
public class IncorrectPatternException extends Exception {

    /**
     * Constructs a new IncorrectPatternException with the specified detail message.
     *
     * @param msg The detail message to display when the exception is thrown.
     */
    public IncorrectPatternException(String msg) {
        super(msg);
    }

}
