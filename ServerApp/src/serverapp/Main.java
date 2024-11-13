/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Message;
import model.MessageType;
import serverapp.model.ServerThread;

/**
 * Main class for the server application.
 *
 * @author Dani
 */
public class Main {

    private static final ResourceBundle archive = ResourceBundle.getBundle("resources.Config");
    private static final int MAX_USERS = Integer.parseInt(archive.getString("MAX_USERS"));;
    private static final int PORT = Integer.parseInt(archive.getString("PORT"));

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private static boolean serverOn = true;
    private ServerSocket svSocket;
    private Message mensaje = new Message();
    private Socket skCliente;
    private static Integer i = 0;

    /**
     * Initializes the Main class and starts the server thread.
     */
    public Main() {
        this.runThread();
    }

    /**
     * Starts the server thread that listens for incoming connections.
     *
     */
    private void runThread() {
        try {
            LOGGER.info("Servidor en marcha");
            svSocket = new ServerSocket(PORT);

            // Thread to listen for 'q' input to close the server
            Thread tecladoThread = new Thread(() -> {
                LOGGER.info("Press 'q' to close the server.");
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                    while (serverOn) {
                        String input = reader.readLine();
                        if (input != null && input.equalsIgnoreCase("q")) {
                            serverOn = false;
                            break;
                        }
                    }
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, "Error reading the keyboard");
                }
            });

            tecladoThread.start();

            // Server loop for handling client connections
            while (serverOn) {
                LOGGER.info("Listening to connections...");
                if (i < MAX_USERS) {
                    skCliente = svSocket.accept();
                    ServerThread st = new ServerThread(skCliente);
                    st.start();
                    añadirCliente(st);
                } else {
                    // Handling too many clients
                    LOGGER.warning("Max client reached.");
                    try (ObjectOutputStream oos = new ObjectOutputStream(skCliente.getOutputStream())) {
                        mensaje.setMsg(MessageType.MAX_THREAD_USER);
                        oos.writeObject(mensaje);
                    }
                    skCliente.close();  // Close socket after notifying client
                }
            }

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error in the server", e);
        } finally {
            try {
                if (svSocket != null && !svSocket.isClosed()) {
                    svSocket.close();
                    LOGGER.info("Server closed.");
                }
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Error creating server", e);
            }
        }
    }

    /**
     * Adds a client to the server's active user count.
     *
     * @param signerT The {@link ServerThread} instance representing the client
     * to be added.
     */
    public static synchronized void añadirCliente(ServerThread signerT) {
        i++;
    }

    /**
     * Removes a client from the server's active user count.
     *
     * @param signerT The {@link ServerThread} instance representing the client
     * to be removed.
     */
    public static synchronized void borrarCliente(ServerThread signerT) {
        i--;
    }

    /**
     * Main method to start the server application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new Main();
    }
}
