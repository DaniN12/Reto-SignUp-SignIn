/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 * Represents a message that contains information about a specific user
 * and the type of message being sent.
 *
 * @author kbilb
 */
public class Message implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private MessageType msg;
    private User user;

    public Message(){
        this.msg= MessageType.SIGNUP_REQUEST;
        this.user = new User();
    }
     /**
     * Gets the message type.
     *
     * @return the {@link MessageType} of this message
     */
    public MessageType getMsg() {
        return msg;
    }

    /**
     * Sets the message type.
     *
     * @param msg the {@link MessageType} to set for this message
     */
    public void setMsg(MessageType msg) {
        this.msg = msg;
    }

    /**
     * Gets the user associated with this message.
     *
     * @return the {@link User} associated with this message
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user associated with this message.
     *
     * @param user the {@link User} to set for this message
     */
    public void setUser(User user) {
        this.user = user;
    }
    
    
}
