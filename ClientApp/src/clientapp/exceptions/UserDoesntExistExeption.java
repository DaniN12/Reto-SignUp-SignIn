/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.exceptions;

/**
 *
 * @author Enzo
 */
public class UserDoesntExistExeption extends Exception {

    public UserDoesntExistExeption(String msg) {
        super(msg);
    }
}
