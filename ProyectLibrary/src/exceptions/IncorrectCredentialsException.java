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
public class IncorrectCredentialsException extends Exception {

    public IncorrectCredentialsException(String msg) {
        super(msg);
    }
}