/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.model;

import exceptions.ConnectionErrorException;
import exceptions.IncorrectCredentialsException;
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
import model.Signable;
import model.User;

/**
 * Handles client-side operations for signing in and signing up a user.
 *
 * @author 2dam
 */
public class Client implements Signable {

    private static final Integer PORT = Integer.parseInt(ResourceBundle.getBundle("resources.Config").getString("PORT"));
    private static final String HOST = ResourceBundle.getBundle("resources.Config").getString("IP");
    private Logger logger = Logger.getLogger(Client.class.getName());

    /**
     * Attempts to sign in a user by sending a sign-in request to the server.
     *
     * @param user The {@link User} object containing the user's login
     * credentials.
     * @return The updated {@link User} object if the sign-in is successful.
     * @throws UserDoesntExistExeption if the user does not exist on the server.
     * @throws ConnectionErrorException if there is an error connecting to the
     * server.
     */
    @Override
public User signIn(User user) throws UserDoesntExistExeption, ConnectionErrorException, IncorrectCredentialsException {
    Message msg = new Message();
    try (Socket socket = new Socket(HOST, PORT);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

        logger.info("Initializing logIn...");
        msg.setUser(user);
        msg.setMsg(MessageType.SIGNIN_REQUEST);
        oos.writeObject(msg);
        msg = (Message) ois.readObject();

        // Verificamos la respuesta del servidor
        switch (msg.getMsg()) {
            case OK_RESPONSE:
                return msg.getUser();  // Si todo es correcto, retornamos el usuario
            case USER_NOT_FOUND_RESPONSE:
                throw new UserDoesntExistExeption("El usuario no existe.");
            case INCORRECT_CREDENTIALS_RESPONSE:
                throw new IncorrectCredentialsException("El correo electrónico o la contraseña no son correctos.");
            case CONNECTION_ERROR_RESPONSE:
                throw new ConnectionErrorException("Hubo un problema al intentar conectar con el servidor.");
            default:
                throw new ConnectionErrorException("Respuesta no válida del servidor.");
        }
    } catch (IOException | ClassNotFoundException e) {
        logger.log(Level.SEVERE, e.getLocalizedMessage());
        throw new ConnectionErrorException("Problema de conexión: " + e.getMessage());
    }
}


    /**
     * Attempts to register a new user by sending a sign-up request to the
     * server.
     *
     * @param user The {@link User} object containing the user's registration
     * details.
     * @return The updated {@link User} object if the sign-up is successful.
     * @throws UserAlreadyExistException if the user already exists on the
     * server.
     * @throws ConnectionErrorException if there is an error connecting to the
     * server.
     */
    @Override
    public User signUp(User user) throws UserAlreadyExistException, ConnectionErrorException {
        Message msg = new Message();
        try (Socket socket = new Socket(HOST, PORT);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            logger.info("Initializing register...");
            msg.setUser(user);
            msg.setMsg(MessageType.SIGNUP_REQUEST);
            oos.writeObject(msg);
            msg = (Message) ois.readObject();

            if (msg == null) {
                throw new ConnectionErrorException("Received empty or invalid response from the server.");
            }

            switch (msg.getMsg()) {
                case OK_RESPONSE:
                    return msg.getUser();
                case USER_ALREADY_EXISTS_RESPONSE:
                    throw new UserAlreadyExistException("This user already exists");
                case CONNECTION_ERROR_RESPONSE:
                    throw new ConnectionErrorException("A problem occurred trying to connect with the server");
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());

        }
        return null;
    }
}