package serverapp.dao;

import exceptions.ConnectionErrorException;
import exceptions.UserAlreadyExistException;
import exceptions.UserDoesntExistExeption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Signable;
import model.User;
import serverapp.model.Pool;

/**
 *
 * @author Kelian and Enzo
 */
public class DAO implements Signable {

    private Connection con;

    private static final Logger logger = Logger.getLogger(DAO.class.getName());
    final String INSERT_USER = "insert into res_users (login, password, company_id, partner_id, active, notification_type) values (?,?,?,?,?, 'email')";
    final String INSERT_USER_DATA = "insert into res_partner (company_id, name, zip, city, street) values (?,?,?,?,?)";
    final String GET_USER_ID = "select MAX(id) as id from res_users";
    final String GET_PARTNER_ID = "select MAX(id) as id from res_partner";
    final String GET_USER = "select * from res_users where login = ? and password = ?";
    final String GET_USERNAME = "select * from res_partner where id = ?";
    final String USER_EXIST = "select * from res_users where login=?";

    // Método para abrir conexión desde el pool
    private void openConnection() throws ConnectionErrorException {
        try {
            con = Pool.getConexion();  // Obtener la conexión desde el pool
        } catch (SQLException e) {
            logger.severe("Error opening the connection: " + e.getMessage());
            throw new ConnectionErrorException("Error connectiong to the database.");
        }
    }

    // Método para cerrar la conexión y devolverla al pool
    private void closeConnection() {
        try {
            if (con != null && !con.isClosed()) {
                Pool.closeConexion(); // Cerrar la conexión asociada al hilo
            }
        } catch (SQLException e) {
            logger.severe("Error closing conection: " + e.getMessage());
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage(), ButtonType.OK).showAndWait();
        }
    }

    @Override
    public User signIn(User user) throws ConnectionErrorException, UserDoesntExistExeption {
        try {
            //Open connection with pool
            this.openConnection();

            // Variable to get the partner id
            Integer id_partner = getPartnerId();
            // Statement to get the user's data from the res_users table
            PreparedStatement ps = con.prepareStatement(GET_USER);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Set the data from the res_users table
                user.setActive(rs.getBoolean("active"));
                user.setEmail(rs.getString("login"));
                user.setPassword(rs.getString("password"));

            } else {
                // if the user doesn't exist throw exception
                throw new UserDoesntExistExeption("User doesn't exist");
            }

            // Statement to get the user's data from the res_partner table
            ps = con.prepareStatement(GET_USERNAME);
            ps.setInt(1, id_partner);
            rs = ps.executeQuery();

            if (rs.next()) {
                // Set the data from the res_partner table
                user.setFullName(rs.getString("name"));
                user.setStreet(rs.getString("street"));
                user.setCity(rs.getString("city"));
                user.setZip(Integer.parseInt(rs.getString("zip")));
                user.setActive(rs.getBoolean("active"));
                user.setCompany_id(rs.getInt("company_id"));
            } else {
                return null;
            }

        } catch (SQLException e) {
            logger.severe("Error al iniciar sesión: " + e.getMessage());
            throw new ConnectionErrorException("Error de base de datos durante el inicio de sesión.");
        } catch (UserDoesntExistExeption | ConnectionErrorException e) {
            alert("Error", e.getMessage());
        } finally {
            // Close connection with the pool
            this.closeConnection();
        }

        // return the user with all the data from the database
        return user;
    }

    @Override
    public User signUp(User user) throws UserAlreadyExistException, ConnectionErrorException {
        try {
            this.openConnection();
            con.setAutoCommit(false); // Desactiva auto-commit
            logger.info("Abriendo conexión y desactivando auto-commit.");

            // Comprobar si el usuario ya existe
            if (userExists(user.getEmail())) {
                logger.warning("El usuario ya existe: " + user.getEmail());
                throw new UserAlreadyExistException("User already exists.");
            }

            // Insertar usuario en la tabla res_partners
            PreparedStatement ps = con.prepareStatement(INSERT_USER_DATA);
            ps.setInt(1, 1);
            ps.setString(2, user.getFullName());
            ps.setInt(3, user.getZip());
            ps.setString(4, user.getCity());
            ps.setString(5, user.getStreet());
            int rowsInserted = ps.executeUpdate(); // Cambié de executeQuery a executeUpdate
            logger.info("Filas insertadas en res_partners: " + rowsInserted);

            // Insertar usuario en la tabla res_users
            ps = con.prepareStatement(INSERT_USER);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setInt(3, 1);
            ps.setInt(4, getPartnerId());
            ps.setBoolean(5, user.getActive());

            rowsInserted = ps.executeUpdate(); // Cambié de executeQuery a executeUpdate
            logger.info("Filas insertadas en res_users: " + rowsInserted);

            // Si todo fue bien, confirmamos la transacción
            con.commit();
            logger.info("Transacción confirmada.");

        } catch (SQLException e) {
            logger.severe("Error durante el signUp: " + e.getMessage());
            try {
                if (con != null) {
                    con.rollback(); // Deshace los cambios si hay un error
                    logger.info("Rollback realizado.");
                }
            } catch (SQLException rollbackEx) {
                logger.severe("Error al realizar el rollback: " + rollbackEx.getMessage());
            }
            throw new ConnectionErrorException("Error during sign up.");
        } finally {
            this.closeConnection();  // Cierra la conexión y la devuelve al pool
        }

        return user;
    }

    public Integer getUserId() {

        int id_usuario = 0;
        try {
            this.openConnection();
            PreparedStatement statement = con.prepareStatement(GET_USER_ID);

            ResultSet rs = statement.executeQuery(GET_USER_ID);

            if (rs.next()) {
                id_usuario = rs.getInt("id");
            } else if (id_usuario == 0) {
                throw new SQLException("An error has occured");
            }
            this.closeConnection();

        } catch (ConnectionErrorException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
        }

        return id_usuario;

    }

    public boolean userExists(String email) {

        try {
            this.openConnection();

            PreparedStatement ps = con.prepareStatement(USER_EXIST);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            logger.severe("User already exists.");
        } catch (ConnectionErrorException ex) {
            logger.severe("Error connecting with database.");
        }

        return false;
    }

    public Integer getPartnerId() {

        Integer id = 0;

        try {
            PreparedStatement ps = con.prepareStatement(GET_PARTNER_ID);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
            } else {
                throw new SQLException("Error in the SQL sentence.");
            }
        } catch (SQLException e) {
            logger.severe("Error in the SQL sentence: " + e.getMessage());
        }

        return id;

    }

    public void alert(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

}
