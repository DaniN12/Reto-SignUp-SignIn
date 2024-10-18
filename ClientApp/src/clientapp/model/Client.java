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
import exceptions.ConnectionErrorException;
import exceptions.UserAlreadyExistException;
import exceptions.UserDoesntExistExeption;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Message;
import model.MessageType;
import exceptions.EmptyFieldException;
import exceptions.IncorrectPasswordException;
import exceptions.IncorrectPatternException;
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
    private User user;

    @Override
    public User signIn() throws UserDoesntExistExeption, ConnectionErrorException {

        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        try {
            logger.info("Initializing register...");
            Socket SocketCliente = new Socket(host, port);
            oos = new ObjectOutputStream(SocketCliente.getOutputStream());
            ois = new ObjectInputStream(SocketCliente.getInputStream());
            msg.setUser(user);
            msg.setMsg(msgType.SIGNIN_REQUEST);
            oos.writeObject(msg);
            msg = (Message) ois.readObject();
            user = msg.getUser();
            oos.close();
            SocketCliente.close();

            switch (msgType) {

                case OK_RESPONSE:
                    return user;

                case USER_ALREADY_EXISTS_RESPONSE:
                    throw new UserDoesntExistExeption("This user doesn't exist");

                case CONNECTION_ERROR_RESPONSE:
                    throw new ConnectionErrorException("A problem occurred trying to connect with the server");

            }

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UserDoesntExistExeption ex) {
             Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConnectionErrorException ex) {
             Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    @Override
    public User signUp() throws UserAlreadyExistException, ConnectionErrorException {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        try {
            logger.info("Initializing register...");
            Socket SocketCliente = new Socket(host, port);
            oos = new ObjectOutputStream(SocketCliente.getOutputStream());
            ois = new ObjectInputStream(SocketCliente.getInputStream());
            msg.setUser(user);
            msg.setMsg(msgType.SIGNUP_REQUEST);
            oos.writeObject(msg);
            msg = (Message) ois.readObject();
            user = msg.getUser();
            oos.close();
            SocketCliente.close();

            switch (msgType) {

                case OK_RESPONSE:
                    return user;

                case USER_ALREADY_EXISTS_RESPONSE:
                    throw new UserAlreadyExistException("This user already exist");

                case CONNECTION_ERROR_RESPONSE:
                    throw new ConnectionErrorException("A problem occurred trying to connect with the server");

            }

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UserAlreadyExistException ex) {

        } catch (ConnectionErrorException ex) {

        }
        return user;
    }

}
