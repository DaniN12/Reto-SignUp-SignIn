package serverapp.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;
import org.apache.commons.dbcp2.BasicDataSource;

public class Pool {

    private static BasicDataSource ds = null;

    // Para asociar conexiones a hilos específicos
    private static ThreadLocal<Connection> threadLocalConnection = new ThreadLocal<>();
    private static Logger logger = Logger.getLogger(Pool.class.getName());;

    // Inicializa el BasicDataSource si aún no está inicializado
    public static BasicDataSource getDataSource() {
        if (ds == null) {
            ds = new BasicDataSource();
             ds.setDriverClassName("org.postgresql.Driver");
            ds.setUsername("odoo");
            ds.setPassword("abcd*1234");
            ds.setUrl("jdbc:postgresql://192.168.21.21:5432/flutter");
            ds.setInitialSize(50); // 50 conexiones iniciales
            ds.setMaxIdle(10);
            ds.setMaxTotal(20);
        }
        return ds;
    }

    // Obtener una conexión para el hilo actual
    public static Connection getConexion() throws SQLException {
        // Revisa si ya existe una conexión asociada al hilo actual
        Connection conn = threadLocalConnection.get();

        if (conn == null || conn.isClosed()) {
            // Si no hay una conexión o está cerrada, asigna una nueva
            conn = getDataSource().getConnection();
            threadLocalConnection.set(conn); // Asociar la conexión con el hilo actual
            logger.info("Conexión obtenida del pool: " + conn);
        }
        return conn;
    }

    // Método para cerrar la conexión asociada al hilo actual
    public static void closeConexion() throws SQLException {
        Connection conn = threadLocalConnection.get();
        if (conn != null && !conn.isClosed()) {
            conn.close(); // Cierra la conexión (la devuelve al pool)
            threadLocalConnection.remove(); // Remueve la referencia en el hilo
        }
    }

    // Método para cerrar el pool de conexiones completo
    public static void closePool() throws SQLException {
        if (ds != null) {
            ds.close(); // Cierra todas las conexiones y libera los recursos del pool
        }
    }
}
