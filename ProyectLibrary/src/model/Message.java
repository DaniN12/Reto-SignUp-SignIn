/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
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
    public MessageType getMsg() {
        return msg;
    }

    public void setMsg(MessageType msg) {
        this.msg = msg;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
}
