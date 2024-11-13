/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverapp.model;

import exceptions.NotAvailableConnectionException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 2dam
 */
public class Pool_2 {

    private static final int MAX_CONNECTIONS = 5;
    private static final Stack<Connection> connectionPool = new Stack<>();
    private static int currentConnections = 0;
    private static final ResourceBundle ARCHIVE = ResourceBundle.getBundle("resources.ConfigServer");
    private static Pool_2 pool;
    private static final Logger logger = Logger.getLogger(Pool_2.class.getName());

     // Constructor privado para implementar el patrón singleton
    private Pool_2() {
        initializeConnections();
    }
    
    private void initializeConnections() {
        try {
            for (int i = 0; i < MAX_CONNECTIONS; i++) {
                connectionPool.push(createNewConnection());
                currentConnections++;
            }
            logger.info("Pool de conexiones configurado con Stack.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al inicializar las conexiones", e);
        }
    }

    private Connection createNewConnection() throws SQLException {
        String url = ARCHIVE.getString("URL");
        String user = ARCHIVE.getString("USER");
        String password = ARCHIVE.getString("PASSWORD");
        return DriverManager.getConnection(url, user, password);
    }

   

    // Método para obtener la instancia del pool (singleton)
    public static Pool_2 getPool() {
        if (pool == null) {
            synchronized (Pool.class) {
                if (pool == null) {
                    pool = new Pool_2();
                }
            }
        }
        return pool;
    }
    
     // Método para obtener una conexión del pool
    public synchronized Connection getConnection() throws NotAvailableConnectionException {
        if (connectionPool.isEmpty()) {
            if (currentConnections < MAX_CONNECTIONS) {
                try {
                    Connection conn = createNewConnection();
                    currentConnections++;
                    logger.info("Nueva conexión creada y proporcionada.");
                    return conn;
                } catch (SQLException e) {
                    logger.log(Level.SEVERE, "Error al crear una nueva conexión", e);
                    return null;
                }
            } else {
                throw new NotAvailableConnectionException("No hay más conexiones disponibles.");
            }
        }
        logger.info("Conexión obtenida del stack.");
        return connectionPool.pop();
    }

 public synchronized void releaseConnection(Connection connection) {
        if (connection != null) {
            connectionPool.push(connection);
            logger.info("Conexión devuelta al stack.");
        }
    }
 
    // Método para cerrar las conexiones y liberar los recursos
    public void closePool_2() {
        while (!connectionPool.isEmpty()) {
            try {
                Connection conn = connectionPool.pop();
                conn.close();
                currentConnections--;
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error al cerrar una conexión", e);
            }
        }
        logger.info("Pool de conexiones cerrado.");
    }
}
