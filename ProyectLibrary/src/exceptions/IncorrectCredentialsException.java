/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 * Exception thrown when credentials are incorrect
 *
 * @author Enzo
 */
public class IncorrectCredentialsException extends Exception {

    /**
     * Exception thrown to indicate that an attempt to authenticate has failed due to incorrect credentials.
     * 
     * @param msg the detail message describing the error
     */
    public IncorrectCredentialsException(String msg) {
        super(msg);
    }
}