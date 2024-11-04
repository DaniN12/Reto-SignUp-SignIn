/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 * Exception thrown when there is a connection error.
 *
 * @author 2dam
 */
public class ConnectionErrorException extends Exception {

    /**
     * Constructs a new ConnectionErrorException with the specified detail message.
     *
     *
     * @param msg the detail message to be saved with the exception
     */
    public ConnectionErrorException(String msg) {
        super(msg);
    }

}
