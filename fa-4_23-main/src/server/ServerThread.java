/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class ServerThread implements Runnable {

    private ServerForm serverForm;
    private int port;
    private ServerSocket server;
    private boolean isRunning;
    private String fileDirectory;

    public ServerThread(ServerForm serverForm, int port, String path) {
        this.serverForm = serverForm;
        this.port = port;
        fileDirectory = path;
        try {
            isRunning = true;
            server = new ServerSocket(port);
            serverForm.systemLog("[Server]: Server đã bắt đầu tại cổng: " + port + ".");
        } catch (IOException ex) {
            serverForm.systemLog(ex.getMessage());
        }

    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                Socket client = server.accept();
                serverForm.systemLog("[Client]: " + client.getInetAddress().toString() + " đã kết nối, cổng client tại " + client.getPort() + "!");
                ClientHandler clientHandler = new ClientHandler(serverForm, client, fileDirectory);
                new Thread(clientHandler).start();
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }
    
    public void stop(){
        try {
            if(server!=null){
                server.close();
                isRunning = false;
            }
        } catch (IOException ex) {
            serverForm.systemLog(this.getClass() +  ex.getMessage());
        }
        serverForm.systemLog("[Server]: Server đã dừng lại.");
    }
}
