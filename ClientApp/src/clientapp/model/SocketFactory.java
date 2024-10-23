/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.model;

import model.Signable;

/**
 *
 * @author Enzo
 */
public class SocketFactory {

    public static Signable getSignable() {

        Signable signable;

        signable = new Client();

        return signable;

    }
    
}
