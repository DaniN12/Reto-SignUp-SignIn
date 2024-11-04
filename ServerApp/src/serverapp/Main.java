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
 *
 * @author Dani
 */
public class Main {

    private static final ResourceBundle archivo = ResourceBundle.getBundle("resources.Config");
    private static final int MAX_USERS = 7;
    private static final int PORT = Integer.parseInt(archivo.getString("PORT"));

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private static boolean serverOn = true;
    private ServerSocket svSocket;
    private Message mensaje = new Message();
    private Socket skCliente;
    private static Integer i = 0;

    public Main() {
        this.arrancarHilo();
    }

    private void arrancarHilo() {
        try {
            LOGGER.info("Servidor en marcha");
            svSocket = new ServerSocket(PORT);

            // Thread to listen for 'q' input to close the server
            Thread tecladoThread = new Thread(() -> {
                LOGGER.info("Presiona 'q' para cerrar el servidor.");
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                    while (serverOn) {
                        String input = reader.readLine();
                        if (input != null && input.equalsIgnoreCase("q")) {
                            serverOn = false;
                            break;
                        }
                    }
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, "Error al leer la entrada del teclado", e);
                }
            });

            tecladoThread.start();

            // Server loop for handling client connections
            while (serverOn) {
                LOGGER.info("Escuchando conexiones entrantes...");
                if (i < MAX_USERS) {
                    skCliente = svSocket.accept();
                    ServerThread st = new ServerThread(skCliente);
                    st.start();
                    añadirCliente(st);
                } else {
                    // Handling too many clients
                    LOGGER.warning("Límite de clientes alcanzado. Rechazando conexión...");
                    try (ObjectOutputStream oos = new ObjectOutputStream(skCliente.getOutputStream())) {
                        mensaje.setMsg(MessageType.MAX_THREAD_USER);
                        oos.writeObject(mensaje);
                    }
                    skCliente.close();  // Close socket after notifying client
                }
            }

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error en el servidor", e);
        } finally {
            try {
                if (svSocket != null && !svSocket.isClosed()) {
                    svSocket.close();
                    LOGGER.info("Servidor cerrado.");
                }
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Error al cerrar el servidor", e);
            }
        }
    }

    // Synchronized methods to manage the client connection counter
    public static synchronized void añadirCliente(ServerThread signerT) {
        i++;
    }

    public static synchronized void borrarCliente(ServerThread signerT) {
        i--;
    }

    public static void main(String[] args) {
        new Main();
    }
}