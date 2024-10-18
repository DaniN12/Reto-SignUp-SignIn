/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.model;

import clientapp.controller.SignUpViewController;
import clientapp.exceptions.EmptyFieldException;
import clientapp.exceptions.IncorrectPasswordException;
import clientapp.exceptions.IncorrectPatternException;
import exceptions.UserAlreadyExistException;
import exceptions.UserDoesntExistExeption;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import model.Message;
import model.MessageType;
import model.Signable;
import model.User;

/**
 *
 * @author 2dam
 */
public class Client implements Signable {

    private static final ResourceBundle archive = ResourceBundle.getBundle("resources.Config");
    private static final Integer port = Integer.parseInt(ResourceBundle.getBundle("resources.Config").getString("PORT"));
    private static final String host = ResourceBundle.getBundle("resources.Config").getString("IP");
    private Logger logger = Logger.getLogger(SignUpViewController.class.getName());
    private MessageType msgType;
    private Message msg;

    @Override
    public User signIn() throws UserAlreadyExistException, UserDoesntExistExeption {

        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        try {
            logger.info("Initializing register...");
            Socket SocketCliente = new Socket(host, port);
            oos = new ObjectOutputStream(SocketCliente.getOutputStream());
            ois = new ObjectInputStream(SocketCliente.getInputStream());
            msg.setMsg(msgType.SIGNUP_REQUEST);
            oos.writeObject(msg);

        } catch (Exception ex) {

        }
        return null;

    }

    @Override
    public User signUp() throws UserAlreadyExistException, UserDoesntExistExeption {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
