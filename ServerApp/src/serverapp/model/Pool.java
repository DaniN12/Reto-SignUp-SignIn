package serverapp.model;

import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Pool {

    private static final BasicDataSource dataSource = new BasicDataSource();
    private static Pool pool;
    private static final ResourceBundle ARCHIVE = ResourceBundle.getBundle("resources.ConfigServer");
    private static Logger logger = Logger.getLogger(Pool.class.getName());

    // Configuración del DataSource
    static {
        dataSource.setUrl(ARCHIVE.getString("URL")); // Reemplaza con tu URL de la base de datos
        dataSource.setUsername(ARCHIVE.getString("USER")); // Reemplaza con tu usuario
        dataSource.setPassword(ARCHIVE.getString("PASSWORD")); // Reemplaza con tu contraseña

        dataSource.setInitialSize(10); // Tamaño inicial del pool de conexiones
        dataSource.setMaxTotal(20); // Número máximo de conexiones en el pool
        dataSource.setMinIdle(5); // Número mínimo de conexiones inactivas
        dataSource.setMaxIdle(15); // Número máximo de conexiones inactivas

        logger.info("Pool de conexiones configurado con BasicDataSource.");
    }

    // Constructor privado para implementar el patrón singleton
    private Pool() {
    }

    // Método para obtener la instancia del pool (singleton)
    public static Pool getPool() {
        if (pool == null) {
            synchronized (Pool.class) {
                if (pool == null) {
                    pool = new Pool();
                }
            }
        }
        return pool;
    }

    // Método para obtener una conexión del pool
    public Connection getConnection() {
        try {
            Connection conn = dataSource.getConnection();
            logger.info("Conexión obtenida del pool.");
            return conn;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener una conexión del pool", e);
            return null;
        }
    }

    // Método para cerrar el DataSource y liberar los recursos
    public void closePool() {
        try {
            dataSource.close();
            logger.info("Pool de conexiones cerrado.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al cerrar el pool de conexiones", e);
        }
    }
}
