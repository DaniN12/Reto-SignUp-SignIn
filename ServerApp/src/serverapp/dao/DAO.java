package serverapp.dao;

import exceptions.ConnectionErrorException;
import exceptions.UserAlreadyExistException;
import exceptions.UserDoesntExistExeption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Signable;
import model.User;
import serverapp.model.Pool;

public class DAO implements Signable {

    private Connection con;
    private static final Logger logger = Logger.getLogger(DAO.class.getName());

    final String INSERTAR_USUARIO = "insert into res_users (login, password, company_id, partner_id,notification_type) values (?,?,?,?,'email')";
    final String INSERTAR_DATOS_USUARIO = "insert into res_partner (company_id, create_date, name, zip, city, phone, active, email) values (?,?,?,?,?,?,?,?)";
    final String INSERTAR_USUARIO_GRUPO = "insert into res_groups_users_rel (gid, uid) values (16,?), (26,?), (28,?), (31,?)";
    final String ID_USUARIO = "select MAX(id) as id from res_users";
    final String INSERTAR_USUARIO_COMPAÑIA = "insert into res_company_users_rel (cid, user_id) values (1,?)";
    final String ID_PARTNER = "select MAX(id) as id from res_partner";
    final String BUSCAR_USUARIO = "select * from res_users where login = ? and password = ?";
    final String NOMBRE_USUARIO = "select * from res_partner where email=?";
    final String USUARIO_EXISTE = "select * from res_users where login=?";

    // Método para abrir conexión desde el pool
    private void openConnection() throws ConnectionErrorException {
        try {
            con = Pool.getConexion();  // Obtener la conexión desde el pool
        } catch (SQLException e) {
            logger.severe("Error al abrir la conexión: " + e.getMessage());
            throw new ConnectionErrorException("Error al conectar con la base de datos.");
        }
    }

    // Método para cerrar la conexión y devolverla al pool
    private void closeConnection() {
        try {
            if (con != null && !con.isClosed()) {
                Pool.closeConexion(); // Cerrar la conexión asociada al hilo
            }
        } catch (SQLException e) {
            logger.severe("Error al cerrar la conexión: " + e.getMessage());
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage(), ButtonType.OK).showAndWait();
        }
    }

    @Override
    public User signIn(User user) throws ConnectionErrorException, UserDoesntExistExeption {
        try {
            this.openConnection();  // Abre la conexión desde el pool

            // Ejemplo de consulta para buscar al usuario
            PreparedStatement ps = con.prepareStatement(BUSCAR_USUARIO);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());

            ResultSet rs = ps.executeQuery();

        } catch (SQLException e) {
            logger.severe("Error al iniciar sesión: " + e.getMessage());
            throw new ConnectionErrorException("Error de base de datos durante el inicio de sesión.");
        } finally {
            this.closeConnection();  // Cierra la conexión y la devuelve al pool
        }

        return user;
    }

    @Override
    public User signUp(User user) throws UserAlreadyExistException, ConnectionErrorException {
        try {
            this.openConnection();  // Abre la conexión desde el pool

            // Comprobar si el usuario ya existe
            PreparedStatement psCheck = con.prepareStatement(USUARIO_EXISTE);
            psCheck.setString(1, user.getEmail());
            ResultSet rsCheck = psCheck.executeQuery();

            if (rsCheck.next()) {
                throw new UserAlreadyExistException("El usuario ya existe");
            }

            // Insertar usuario en la base de datos
            PreparedStatement psInsertUser = con.prepareStatement(INSERTAR_USUARIO);
            psInsertUser.setString(1, user.getEmail());
            psInsertUser.setString(2, user.getPassword());
            psInsertUser.setInt(3, 1);
            

            psInsertUser.executeUpdate();

        } catch (SQLException e) {
            logger.severe("Error al registrar usuario: " + e.getMessage());
            throw new ConnectionErrorException("Error de base de datos durante el registro.");
        } finally {
            this.closeConnection();  // Cierra la conexión y la devuelve al pool
        }

        return user;
    }
}
