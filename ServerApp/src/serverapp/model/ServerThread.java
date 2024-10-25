/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverapp.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Message;
import model.MessageType;
import model.User;

/**
 *
 * @author 2dam
 */
public class ServerThread {

    private final int PUERTO = Integer.parseInt(ResourceBundle.getBundle("resources.Config").getString("PORT"));
    ;
    private Logger logger = Logger.getLogger(ServerThread.class.getName());
    private MessageType msgType;
    private Message msg;
    private User user;
    private Socket clientSocket;

    // Constructor que recibe un socket
    public ServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    private void run() {

        ServerSocket servidor = null;
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        try {
            
            servidor = new ServerSocket(PUERTO);
            logger.info("Waiting for clients...");
            
            socket = servidor.accept();
            logger.info("Client connected.");
            
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            
           //??AYUDA msg.setUser();
            
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
