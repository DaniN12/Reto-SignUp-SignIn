package serverapp.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import org.apache.commons.dbcp2.BasicDataSource;

public class Pool {

    private static BasicDataSource ds = null;
    private static final ResourceBundle archive = ResourceBundle.getBundle("resourcesServer.Config");
    // Associate conection with a thread
    private static ThreadLocal<Connection> threadLocalConnection = new ThreadLocal<>();
    private static Logger logger = Logger.getLogger(Pool.class.getName());

    /**
     * Initializes the basic data source if not activated
     *
     * @return the data source with the connections made
     */
    public static BasicDataSource getDataSource() {
        if (ds == null) {
            ds = new BasicDataSource();
            ds.setDriverClassName(archive.getString("DRIVER"));
            ds.setUsername(archive.getString("USER"));
            ds.setPassword(archive.getString("PASSWORD"));
            ds.setUrl(archive.getString("URL"));
            ds.setInitialSize(50); // 50 conexiones iniciales
            ds.setMaxIdle(10);
            ds.setMaxTotal(20);
        }
        return ds;
    }

    /**
     * Method that get's the connection with the thread
     *
     * @return the new connection
     * @throws SQLException thrown if there's no connection done
     */
    public static Connection getConexion() throws SQLException {
        // Revisa si ya existe una conexión asociada al hilo actual
        Connection conn = threadLocalConnection.get();

        if (conn == null || conn.isClosed()) {
            // If there's no connection it creates a new one
            conn = getDataSource().getConnection();
            // Associate it with the new thread
            threadLocalConnection.set(conn);
            logger.info("Conexión gotten pool: " + conn);
        }
        return conn;
    }

    /**
     * Method to close the connection with the thread
     *
     * @throws SQLException thrown if there's an error with the connection
     */
    public static void closeConexion() throws SQLException {
        Connection conn = threadLocalConnection.get();
        if (conn != null && !conn.isClosed()) {
            // Closes the connection with the pool (return it to the pool)
            conn.close();
            // Removes the connection with the thread
            threadLocalConnection.remove();
        }
    }

    /**
     * Method to close the conection with the pool
     *
     * @throws SQLException SQLException thrown if there's an error with the
     * connection
     */
    public static void closePool() throws SQLException {
        if (ds != null) {
            //Closes all the connections with the pool and frees the pool resources
            ds.close();
        }
    }
}
