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
public class MaxUsersException extends Exception{
    
    /**
     * Constructs a new MaxUsersException with the specified detail message.
     * 
     * @param msg the detail message that provides information about the exception.
     */
    public MaxUsersException(String msg){
        super(msg);
    }
}