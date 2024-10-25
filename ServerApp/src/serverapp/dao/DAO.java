package serverapp.dao;

import exceptions.ConnectionErrorException;
import exceptions.UserAlreadyExistException;
import exceptions.UserDoesntExistExeption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Signable;
import model.User;
import serverapp.model.Pool;

public class DAO implements Signable {

    private Connection con;
    private Statement statement;
    private static final Logger logger = Logger.getLogger(DAO.class.getName());

    final String INSERT_USER = "insert into res_users (login, password, company_id, partner_id, active) values (?,?,?,?,?)";
    final String INSERT_USER_DATA = "insert into res_partner (company_id, name, zip, city, street) values (?,?,?,?,?,?)";
    final String GET_USER_ID = "select MAX(id) as id from res_users";
    final String GET_PARTNER_ID = "select MAX(id) as id from res_partner";
    final String GET_USER = "select * from res_users where login = ? and password = ?";
    final String GET_USERNAME = "select * from res_partner where id = ?";
    final String USER_EXISTS = "select * from res_users where login = ?";

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

            if (rs.next()) {
                user.setActive(rs.getBoolean("active"));
                user.setCompany_id(rs.getInt("company_id"));
            }

        } catch (SQLException e) {
            logger.severe("Error al iniciar sesión: " + e.getMessage());
            alert("Error al iniciar sesión.");
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

            if (!userExists(user.getEmail())) {

                // Insertar usuario en la tabla res_partners
                PreparedStatement ps = con.prepareStatement(INSERT_USER_DATA);
                ps.setInt(1, 1);
                ps.setString(2, user.getFullName());
                ps.setInt(3, user.getZip());
                ps.setString(4, user.getCity());
                ps.setString(5, user.getStreet());

                ResultSet rs = ps.executeQuery();

                // Insertar usuario en la tabla res_users
                ps = con.prepareStatement(INSERT_USER);
                ps.setString(1, user.getEmail());
                ps.setString(2, user.getPassword());
                ps.setInt(3, 1);
                ps.setInt(4, getPartnerId());
                ps.setBoolean(5, user.getActive());

                rs = ps.executeQuery();

            } else {
                throw new UserAlreadyExistException("User already exists.");
            }

        } catch (SQLException e) {
            
            try {
                con.rollback();
                alert("Error de base de datos durante el registro.");
                throw new ConnectionErrorException("Error de base de datos durante el registro.");
            } catch (SQLException ex) {
                logger.severe("Error at the rollback: " + ex.getMessage());
            }
            
        } finally {
            this.closeConnection();  // Cierra la conexión y la devuelve al pool
        }

        return user;
    }

    public boolean userExists(String email) {

        try {
            this.openConnection();

            PreparedStatement ps = con.prepareStatement(USER_EXISTS);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            logger.severe("User already exists.");
            alert("User already exists.");
        } catch (ConnectionErrorException ex) {
            logger.severe("Error connecting with database.");
            alert("Error connecting with database.");
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
    
    public void alert( String msg ) {
        
        //Create an alert to make sure that the user wants to close the application
        Alert alert = new Alert(Alert.AlertType.ERROR);
        
        //set the alert message and title
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(msg);
        
        //Make the alert wait until user closes
        alert.showAndWait();

    }
}
