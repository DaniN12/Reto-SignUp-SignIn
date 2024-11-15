/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverapp.model;

import exceptions.ConnectionErrorException;
import exceptions.IncorrectCredentialsException;
import exceptions.UserAlreadyExistException;
import exceptions.UserDoesntExistExeption;
import exceptions.UserNotActiveException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Message;
import model.MessageType;
import model.Signable;
import model.User;
import serverapp.Main;
import serverapp.model.DAOFactory;

/**
 *
 * @author Ruth
 */
public class ServerThread extends Thread {

    //Llamada de los distintos objetos
    private ObjectInputStream ois = null;
    private ObjectOutputStream oos = null;
    private Socket sk = null;
    private Signable sign;
    private Message msg = null;
    private User user;
    private static final Logger LOGGER = Logger.getLogger(ServerThread.class.getName());

    //Constructor vacio
    public ServerThread() {
    }

    //Constructor con el socket
    public ServerThread(Socket sk) {
        this.sk = sk;

    }

    @Override
    public void run() {
        try {
            // Initialize input and output streams early
            oos = new ObjectOutputStream(sk.getOutputStream());
            ois = new ObjectInputStream(sk.getInputStream());

            // Initialize DAO for sign-in and sign-up methods
            DAOFactory daofact = new DAOFactory();
            sign = daofact.getDAO();

            // Read message from client
            msg = (Message) ois.readObject();
            LOGGER.info("Message received: " + msg.getMsg());

            switch (msg.getMsg()) {
                case SIGNIN_REQUEST:
                    LOGGER.info("Processing Sign In request...");
                    try {
                        user = sign.signIn(msg.getUser());
                        if (user != null) {
                            msg.setMsg(MessageType.OK_RESPONSE);
                            msg.setUser(user);
                        } else {
                            msg.setMsg(MessageType.USER_NOT_FOUND_RESPONSE);
                        }
                    } catch (UserDoesntExistExeption e) {
                        msg.setMsg(MessageType.USER_NOT_FOUND_RESPONSE);
                        LOGGER.log(Level.SEVERE, "User does not exist");
                    } catch (ConnectionErrorException ex) {
                        Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
                        msg.setMsg(MessageType.CONNECTION_ERROR_RESPONSE);
                    } catch (UserNotActiveException ex) {
                        Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
                    } catch (IncorrectCredentialsException ex) {
                        Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
                        msg.setMsg(MessageType.INCORRECT_CREDENTIALS_RESPONSE);
                    }
                    break;

                case SIGNUP_REQUEST:
                    LOGGER.info("Processing Sign Up request...");
                    try {
                        user = sign.signUp(msg.getUser());
                        if (user != null) {
                            msg.setMsg(MessageType.OK_RESPONSE);
                            msg.setUser(user);
                        } else {
                            msg.setMsg(MessageType.USER_ALREADY_EXISTS_RESPONSE);
                        }
                    } catch (UserAlreadyExistException e) {
                        msg.setMsg(MessageType.USER_ALREADY_EXISTS_RESPONSE);
                        LOGGER.log(Level.SEVERE, "User already exists");
                    } catch (ConnectionErrorException ex) {
                        msg.setMsg(MessageType.CONNECTION_ERROR_RESPONSE);
                        Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
                    }
                    break;

                default:
                    msg.setMsg(MessageType.ERROR_RESPONSE);
                    LOGGER.warning("Unknown request type received");
                    break;
            }

            // Send response back to the client
            oos.writeObject(msg);
            oos.flush();

        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Class not found during message reading", e);
            msg.setMsg(MessageType.ERROR_RESPONSE);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "I/O error during client communication", e);
            msg.setMsg(MessageType.CONNECTION_ERROR_RESPONSE);
        } finally {
            // Ensure resources are closed
            LOGGER.info("Closing client connection...");
            try {
                if (ois != null) {
                    ois.close();
                }
                if (oos != null) {
                    oos.close();
                }
                if (sk != null && !sk.isClosed()) {
                    sk.close();
                }
                Main.borrarCliente(this);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Error closing resources", e);
            }

        }
    }
}
