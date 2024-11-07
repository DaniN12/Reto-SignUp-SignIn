package serverapp.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;
import org.apache.commons.dbcp2.BasicDataSource;

public class Pool {

    private static BasicDataSource ds = null;

    private static ThreadLocal<Connection> threadLocalConnection = new ThreadLocal<>();
    private static Logger logger = Logger.getLogger(Pool.class.getName());

    /**
     * Returns a singleton instance of the database connection pool.
     * 
     * @return The configured BasicDataSource instance.
     */
    public static BasicDataSource getDataSource() {
        if (ds == null) {
            ds = new BasicDataSource();
            ds.setDriverClassName("org.postgresql.Driver");
            ds.setUsername("odoo");
            ds.setPassword("abcd*1234");
            ds.setUrl("jdbc:postgresql://192.168.20.157:5432/flutter");
            ds.setInitialSize(50);
            ds.setMaxIdle(10);
            ds.setMaxTotal(20);
        }
        return ds;
    }

    /**
     * Obtains a database connection from a thread-local pool or creates a new connection
     * This method ensures that each thread has its own connection
     * 
     * @return  the database connection associated with the current thread.
     * @throws SQLException if a database access error occurs or the connection could not be established.
     */
    public static Connection getConexion() throws SQLException {
        Connection conn = threadLocalConnection.get();

        if (conn == null || conn.isClosed()) {
            conn = getDataSource().getConnection();
            threadLocalConnection.set(conn);
            logger.info("Conexión obtenida del pool: " + conn);
        }
        return conn;
    }

    /**
     * Closes the current database connection stored in the thread-local storage.
     * 
     * @throws SQLException if there is an error while closing the connection.
     */
    public static void closeConexion() throws SQLException {
        Connection conn = threadLocalConnection.get();
        if (conn != null && !conn.isClosed()) {
            conn.close();
            threadLocalConnection.remove();
        }
    }

    /**
     * Closes the connection pool if it is not already closed.
     * 
     * @throws SQLException If an error occurs while closing the connection pool.
     */
    public static void closePool() throws SQLException {
        if (ds != null) {
            ds.close();
        }
    }
}
