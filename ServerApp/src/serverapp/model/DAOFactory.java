/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverapp.model;

import model.Signable;
import serverapp.dao.DAO;

/**
 *
 * @author Ruth
 */
public class DAOFactory{
 
    public Signable getDAO(){
        
        Signable sign;
        
        sign = new DAO();
        
        return sign;
    }
}