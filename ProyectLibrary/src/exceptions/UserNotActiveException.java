/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Enzo
 */
public class UserNotActiveException extends Exception {

    /**
     * This message indicates that the user is not active.
     * 
     * @param msg the detail message to be passed to the exception
     */
    public UserNotActiveException(String msg) {
        super(msg);
    }
}