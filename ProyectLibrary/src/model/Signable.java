/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * This interface defines the contract for user authentication operations.
 * Implementing classes are expected to provide functionality for signing
 * in and signing up users.
 * @author Kelian
 */
public interface Signable {

    /**
     * Authenticates a user with the given credentials.
     *
     * @param user The {@link User} object containing the user's login details.
     * @return The authenticated {@link User} object if the sign-in is successful.
     * @throws exceptions.ConnectionErrorException if there is a problem with the connection to the server.
     * @throws exceptions.UserDoesntExistExeption if the provided user does not exist in the system.
     */
    public User signIn(User user) throws exceptions.ConnectionErrorException, exceptions.UserDoesntExistExeption;

     /**
     * Registers a new user account.
     *
     * @param user The {@link User} object containing the user's registration details.
     * @return The registered {@link User} object if the sign-up is successful.
     * @throws exceptions.UserAlreadyExistException if the user already exists in the system.
     * @throws exceptions.ConnectionErrorException if there is a problem with the connection to the server.
     */
    public User signUp(User user) throws exceptions.UserAlreadyExistException, exceptions.ConnectionErrorException;

}
