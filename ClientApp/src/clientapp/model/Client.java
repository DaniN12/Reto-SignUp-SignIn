/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.model;

import exceptions.ConnectionErrorException;
import exceptions.IncorrectCredentialsException;
import exceptions.MaxUsersException;
import exceptions.UserAlreadyExistException;
import exceptions.UserDoesntExistExeption;
import exceptions.UserNotActiveException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.logging.Level;
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

    private static final Integer port = Integer.parseInt(ResourceBundle.getBundle("resources.Config").getString("PORT"));
    private static final String host = ResourceBundle.getBundle("resources.Config").getString("IP");
    private Logger logger = Logger.getLogger(Client.class.getName());

    @Override
    public User signIn(User user) throws UserDoesntExistExeption, ConnectionErrorException, UserNotActiveException, IncorrectCredentialsException, MaxUsersException {
        Message msg = new Message();
        try (Socket socket = new Socket(host, port);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            logger.info("Initializing logIn...");
            msg.setUser(user);
            msg.setMsg(MessageType.SIGNIN_REQUEST);
            oos.writeObject(msg);
            msg = (Message) ois.readObject();

            switch (msg.getMsg()) {
                case OK_RESPONSE:
                    return msg.getUser();
                case USER_NOT_FOUND_RESPONSE:
                    throw new UserDoesntExistExeption("This user doesn't exist");
                case CONNECTION_ERROR_RESPONSE:
                    throw new ConnectionErrorException("A problem occurred trying to connect with the server");

                case INCORRECT_CREDENTIALS_RESPONSE:
                    throw new IncorrectCredentialsException("The email and password do not match");

                case MAX_THREAD_USER:
                    throw new MaxUsersException("The server is already full, try again later");
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
            throw new ConnectionErrorException("Connection issue: " + e.getMessage());
        } catch (IncorrectCredentialsException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        } catch (MaxUsersException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public User signUp(User user) throws UserAlreadyExistException, ConnectionErrorException, MaxUsersException {
        Message msg = new Message();
        try (Socket socket = new Socket(host, port);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            logger.info("Initializing register...");
            msg.setUser(user);
            msg.setMsg(MessageType.SIGNUP_REQUEST);
            oos.writeObject(msg);
            msg = (Message) ois.readObject();

            switch (msg.getMsg()) {
                case OK_RESPONSE:
                    return msg.getUser();
                case USER_ALREADY_EXISTS_RESPONSE:
                    throw new UserAlreadyExistException("This user already exists");
                case CONNECTION_ERROR_RESPONSE:
                    throw new ConnectionErrorException("A problem occurred trying to connect with the server");
                case MAX_THREAD_USER:
                    throw new MaxUsersException("The server is already full, try again later");
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        } catch (MaxUsersException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return null;
    }
}
