/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverapp.dao;

import model.Signable;

/**
 *
 * @author 2dam
 */
public class DAOFactory {
    
    public Signable getDao() {
        Signable sign;
        sign = new DAO();
        return sign;
    }
    
}