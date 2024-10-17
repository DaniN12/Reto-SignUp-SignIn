/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Kelian
 */
public interface Signable {

    public User signUp() throws Exception;

    public User signIn() throws Exception;
}
