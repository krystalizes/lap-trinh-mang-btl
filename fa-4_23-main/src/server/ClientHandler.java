/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class ClientHandler implements Runnable {

    private ServerForm serverForm;
    private Socket client;
    private String fileDirectory;
    private DataInputStream dis;
    private DataOutputStream dos;
    private File fileRequested;
    private boolean isRunning;
    private int BUFFER_SIZE = 100;

    public ClientHandler(ServerForm serverForm, Socket client, String path) {
        this.serverForm = serverForm;
        this.client = client;
        fileDirectory = path;
        isRunning = true;
        fileRequested = null;
        try {
            dis = new DataInputStream(client.getInputStream());
            dos = new DataOutputStream(client.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        String fileListButStr = getAllFiles(fileDirectory, "");
        try {
            dos.writeUTF(fileListButStr);
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (isRunning) {
            try {

                String message = dis.readUTF();
                String[] messageSplited = message.split(";");
                String command = messageSplited[0];
                switch (command) {
                    case "UPDATE_FILE_LIST":
                        String strr = getAllFiles(fileDirectory, "");
                        dos.writeUTF(strr);
                        break;
                    case "DOWNLOAD_FILE_REQUEST":
                        String filePath = fileDirectory  + messageSplited[1];
                        serverForm.systemLog("[Client]: " + client.getInetAddress().toString() + " đang yêu cầu tải xuống \"" + messageSplited[1] + "\".");
                        fileRequested = new File(filePath);
                        System.out.println(filePath);
                        break;
                    case "DOWNLOAD_FILE_SUCCESSFUL":
                        serverForm.systemLog("[Client]: " + client.getInetAddress().toString() + " tải xuống thành công \"" + messageSplited[1] + "\".");
                        break;
                    case "DOWNLOAD_FILE_CANCEL":
                        serverForm.systemLog("[Client]: " + client.getInetAddress().toString() + " hủy bỏ yêu cầu tải xuống \"" + messageSplited[1] + "\".");
                        break;
                    case "UPLOAD_FILE_REQUEST":
                        serverForm.systemLog("[Client]: " + client.getInetAddress().toString() + " muốn chia sẻ một tệp tin \"" + messageSplited[1] + "\".");
                        dos.writeUTF("UPLOAD_FILE_ACCEPTED");
                        break;
                    case "DOWNLOAD_FILE_BEGIN":
                        long fileLen = fileRequested.length();
                        dos.writeLong(fileLen);
                        long totalRead = 0;
                        InputStream input = new FileInputStream(fileRequested);
                        byte[] buffer = new byte[BUFFER_SIZE];
                        int count = 0;
                        while (totalRead != fileLen) {
                            count = input.read(buffer);
                            dos.writeInt(count);
                            dos.write(buffer, 0, count);
                            totalRead += (long) count;
                        }
                        dos.flush();
                        break;
                    case "UPLOAD_FILE_BEGIN":
                        String newNameFile = "[" + client.getInetAddress().toString().substring(1) + "]" + new SimpleDateFormat("ddMMyyyy_HHmmss").format(System.currentTimeMillis()) +"_" +messageSplited[1];
                        long fileSize = Long.parseLong(messageSplited[2]);
                        FileOutputStream fos = new FileOutputStream(fileDirectory + "\\" + newNameFile);
                        InputStream inputStream = client.getInputStream();
                        int BUFF_SIZE = 100;
                        byte[] buff = new byte[BUFF_SIZE];
                        int c = 0;
                        long total = 0;
                        while (total != fileSize) {
                            BUFF_SIZE = dis.readInt();
                            dis.readFully(buff, 0, BUFF_SIZE);
                            total += BUFF_SIZE;
                            fos.write(buff, 0, BUFF_SIZE);
                        }
                        fos.flush();
                        serverForm.systemLog("[Client]: " + client.getInetAddress().toString() +" tải lên thành công file \"" + messageSplited[1] +"\"." );
                        fos.close();
                        serverForm.systemLog("[Server]: Server lưu thành công file \"" + newNameFile + "\" từ " + client.getInetAddress().toString());
                        break;
                }

            } catch (java.net.SocketException ex) {
                isRunning = false;
                ex.printStackTrace();
                serverForm.systemLog("[Client]: " + client.getInetAddress().toString() + " đã ngắt kết nối!");

            } catch (IOException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private String getAllFiles(String path, String child) {
        String res = "";
        File file = new File(path + "\\" + child);
        File[] listFiles = file.listFiles();
        if(listFiles == null || listFiles.length ==0 ){
            return "";
        }
        for(File f : listFiles){
            if(f.isDirectory()){
                res += getAllFiles(path, child + "\\" +  f.getName()) + ";";
            }
            else{
                res+= child + "\\" +  f.getName() + ":" + f.length()+";";
            }
        }
        return res;
    }
    
}
