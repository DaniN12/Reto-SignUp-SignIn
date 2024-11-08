/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;


/**
 * Represents the various types of messages that can be sent in the application.
 *
 * @author Kelian
 */
public enum MessageType implements Serializable { 
        
        SIGNIN_REQUEST, 
        SIGNUP_REQUEST,
        OK_RESPONSE,
        USER_ALREADY_EXISTS_RESPONSE,
        CONNECTION_ERROR_RESPONSE,
        INCORRECT_CREDENTIALS_RESPONSE,
        USER_NOT_FOUND_RESPONSE,
        ERROR_RESPONSE,
        MAX_THREAD_USER; 

}
