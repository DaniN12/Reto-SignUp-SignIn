/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverapp.model;

import exceptions.StackIsFullExeption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Enzo
 */
public class Pool2 implements IConexionPool {

    protected static Stack<Connection> pilaStack;
    protected String connectionURL;
    protected String userName;
    protected String password;
    protected static final ResourceBundle ARCHIVE = ResourceBundle.getBundle("resources.ConfigServer");
    private static Logger logger = Logger.getLogger(Pool.class.getName());

    /**
     * Genera Pool básico de conexión utilizando URL , contraseña y nombre
     */
    public Pool2() {
        connectionURL = ARCHIVE.getString("URL");
        userName = ARCHIVE.getString("USER");
        password = ARCHIVE.getString("PASSWORD");
        pilaStack = new Stack<>();
    }

    /**
     * Adquiere conexión del Pool o genera una nueva si el pool esta vacío
     */
    public synchronized Connection extraerConexion()
            throws SQLException {
        try {

            // Si el pool no esta vacio, tomar una conexion  
            if (!pilaStack.empty()) {
                return (Connection) pilaStack.pop();
            } else if (pilaStack.size() >= 5) {

                throw new StackIsFullExeption("The stack is full of connections");

            } else {
                return DriverManager.getConnection(connectionURL, userName, password);
            }
        } catch (StackIsFullExeption ex) {
            Logger.getLogger(Pool2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Regresar conexion al pool
     */
    public synchronized void liberarConexion(Connection conn)
            throws SQLException {
        conn.close();
        pilaStack.push(conn);
    }

}
