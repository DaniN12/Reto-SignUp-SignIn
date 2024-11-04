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

    /**
     * Variable to open the connection with the database
     */
    private Connection con;

    /**
     * Logger to show the steps of the application in the console
     */
    private static final Logger logger = Logger.getLogger(DAO.class.getName());

    /**
     * SQL statements to insert and get data from the database
     */
    final String INSERT_USER = "insert into res_users (login, password, company_id, partner_id, active, notification_type) values (?,?,?,?,?, 'email')";
    final String INSERT_USER_DATA = "insert into res_partner (company_id, name, zip, city, street) values (?,?,?,?,?)";
    final String GET_USER_ID = "select MAX(id) as id from res_users";
    final String GET_PARTNER_ID = "select MAX(id) as id from res_partner";
    final String GET_USER = "select * from res_users where login = ? and password = ?";
    final String GET_USERNAME = "select * from res_partner where id = ?";
    final String USER_EXIST = "select * from res_users where login=?";

    /**
     * Method to open connection with the database
     *
     * @throws ConnectionErrorException checks if the connection with the
     * database is made
     */
    private void openConnection() throws ConnectionErrorException {
        try {
            // Get the connection from the pool
            con = Pool.getConexion();
        } catch (SQLException e) {
            logger.severe("Error opening the connection: " + e.getMessage());
            throw new ConnectionErrorException("Error connectiong to the database.");
        }
    }

    /**
     * Method to close the connection with the database
     */
    private void closeConnection() {
        try {
            if (con != null && !con.isClosed()) {
                // Close the conection with the thread
                Pool.closeConexion();
            }
        } catch (SQLException e) {
            logger.severe("Error closing conection: " + e.getMessage());
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage(), ButtonType.OK).showAndWait();
        }
    }

    /**
     * Method to login with the user gotten from the database
     *
     * @param user is the user with the parameters set by the client
     * @return the user from the database
     * @throws ConnectionErrorException exception that checks if an error
     * happened with the connection while executing the statement
     * @throws UserDoesntExistExeption exception that checks if the user doesn't
     * exist in the database
     */
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
            logger.severe("Error: " + e.getMessage());
        } finally {
            // Close connection with the pool
            this.closeConnection();
        }

        // return the user with all the data from the database
        return user;
    }

    /**
     * Method to register a user in the database
     *
     * @param user is the user with the parameters set by the client
     * @return the user from the database
     * @throws UserAlreadyExistException exception to check if the user already
     * exist
     * @throws ConnectionErrorException exception that checks if an error
     * happened with the connection while executing the statement
     */
    @Override
    public User signUp(User user) throws UserAlreadyExistException, ConnectionErrorException {
        try {
            // Open the connection with the pool
            this.openConnection();
            // Deactivates the autocommit
            con.setAutoCommit(false);
            logger.info("Abriendo conexión y desactivando auto-commit.");

            // Check if the user already exist
            if (userExists(user.getEmail())) {
                logger.warning("El usuario ya existe: " + user.getEmail());
                throw new UserAlreadyExistException("User already exists.");
            }

            // Insert user's data to the res_partner table
            PreparedStatement ps = con.prepareStatement(INSERT_USER_DATA);
            ps.setInt(1, 1);
            ps.setString(2, user.getFullName());
            ps.setInt(3, user.getZip());
            ps.setString(4, user.getCity());
            ps.setString(5, user.getStreet());
            int rowsInserted = ps.executeUpdate();
            logger.info("Filas insertadas en res_partners: " + rowsInserted);

            // Insert user's data in the res_users table
            ps = con.prepareStatement(INSERT_USER);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setInt(3, 1);
            ps.setInt(4, getPartnerId());
            ps.setBoolean(5, user.getActive());

            rowsInserted = ps.executeUpdate();
            logger.info("Filas insertadas en res_users: " + rowsInserted);

            // Confirm the transaction if everything is correct
            con.commit();
            logger.info("Transacción confirmada.");

        } catch (SQLException e) {
            logger.severe("Error durante el signUp: " + e.getMessage());
            try {
                if (con != null) {
                    // Undo the changes if an error happens
                    con.rollback();
                    logger.info("Rollback realizado.");
                }
            } catch (SQLException rollbackEx) {
                logger.severe("Error al realizar el rollback: " + rollbackEx.getMessage());
            }
            throw new ConnectionErrorException("Error during sign up.");
        } finally {
            // Close the connection with the pool
            this.closeConnection();
        }

        // returns the new user
        return user;
    }

    /**
     * Method to get the users id
     *
     * @return the last users id
     */
    public Integer getUserId() {

        int id_usuario = 0;
        try {
            // Open connection with the pool
            this.openConnection();
            PreparedStatement statement = con.prepareStatement(GET_USER_ID);

            ResultSet rs = statement.executeQuery(GET_USER_ID);

            // Get the last user's id
            if (rs.next()) {
                // set the last user's id tp the new id
                id_usuario = rs.getInt("id");
            } else if (id_usuario == 0) {
                throw new SQLException("An error has occured");
            }
            // Close connection with the pool
            this.closeConnection();

        } catch (ConnectionErrorException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
        }

        // return the user's new id
        return id_usuario;

    }

    /**
     * Method to check if the user already exist
     *
     * @param email variable to check the existance of the user
     * @return a Boolean that especifies if the user exist or not
     */
    public boolean userExists(String email) {

        try {
            // Open connection with the pool
            this.openConnection();

            PreparedStatement ps = con.prepareStatement(USER_EXIST);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            // Check if the user exist
            if (rs.next()) {
                // if the user exist returns true
                return true;
            }

        } catch (SQLException e) {
            logger.severe("User already exists.");
        } catch (ConnectionErrorException ex) {
            logger.severe("Error connecting with database.");
        }
        // Close connection with the pool
        this.closeConnection();

        // if the user doesn't exist resturns false
        return false;
    }

    /**
     * Method to get the user's partner id
     *
     * @return the user's new partner id
     */
    public Integer getPartnerId() {

        Integer id = 0;

        try {
            // Open connection with the pool
            this.openConnection();
            PreparedStatement ps = con.prepareStatement(GET_PARTNER_ID);

            ResultSet rs = ps.executeQuery();
            // Gets the last partner's id
            if (rs.next()) {
                // Set the id to the new user
                id = rs.getInt("id");
            } else {
                throw new SQLException("Error in the SQL sentence.");
            }
        } catch (SQLException e) {
            logger.severe("Error in the SQL sentence: " + e.getMessage());
        } catch (ConnectionErrorException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Close connection with the pool
        this.closeConnection();

        // returns the users partner id
        return id;

    }
}
