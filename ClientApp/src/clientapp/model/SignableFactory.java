/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.model;

import model.Signable;


/**
 * A factory class to create instances of {@link Signable}.
 *
 * @author Enzo
 */
public class SignableFactory {

    /**
     * Returns an instance of a {@link Signable} implementation.
     *
     *
     * @return An instance of a class implementing the Signable interface.
     */
    public static Signable getSignable() {

        Signable signable;

        signable = new Client();

        return signable;

    }
    
}
