/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.ProgressMonitorInputStream;
import javax.swing.SwingWorker;

/**
 *
 * @author ASUS
 */
public class DownloadFile extends javax.swing.JFrame {

    private Socket client;
    private String fileName;
    private DataOutputStream dos;
    private DataInputStream dis;
    private String currPath;

    /**
     * Creates new form SendFile
     */
    public DownloadFile(Socket client, String fileName) {
        this.client = client;
        this.fileName = fileName;
        try {
            dos = new DataOutputStream(client.getOutputStream());
            dis = new DataInputStream(client.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(DownloadFile.class.getName()).log(Level.SEVERE, null, ex);
        }

        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        pathInput = new javax.swing.JTextField();
        pathInput.setEditable(false);
        chooseFileBtn = new javax.swing.JButton();
        confirmBtn = new javax.swing.JButton();
        progressBar = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("SF Pro Display", 0, 16)); // NOI18N
        jLabel1.setText("Đường dẫn:");

        pathInput.setFont(new java.awt.Font("SF Pro Display", 0, 16)); // NOI18N
        pathInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pathInputActionPerformed(evt);
            }
        });

        chooseFileBtn.setFont(new java.awt.Font("SF Pro Display", 0, 16)); // NOI18N
        chooseFileBtn.setText("Chọn...");
        chooseFileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseFileBtnActionPerformed(evt);
            }
        });

        confirmBtn.setFont(new java.awt.Font("SF Pro Display", 0, 16)); // NOI18N
        confirmBtn.setText("Tải xuống");
        confirmBtn.setMinimumSize(new java.awt.Dimension(110, 29));
        confirmBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmBtnActionPerformed(evt);
            }
        });

        progressBar.setFont(new java.awt.Font("SF Pro Display", 0, 16)); // NOI18N
        progressBar.setStringPainted(true);
        progressBar.setForeground(Color.BLUE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pathInput)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chooseFileBtn))
                    .addComponent(jLabel1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 306, Short.MAX_VALUE)
                        .addComponent(confirmBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 305, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pathInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chooseFileBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(confirmBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pathInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pathInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pathInputActionPerformed

    private void chooseFileBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseFileBtnActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new java.io.File("."));
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fc.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File yourFolder = fc.getSelectedFile();
            currPath = yourFolder.getAbsolutePath();
            pathInput.setText(currPath);
        }
    }//GEN-LAST:event_chooseFileBtnActionPerformed

    private void confirmBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmBtnActionPerformed
        String path = currPath + "\\" + fileName;
        if (pathInput.getText().equals("")) {
            new MyOptionPane("Xin chọn nơi lưu tập tin", this).showMessageDialog("Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (new MyOptionPane("Bạn chắc chắn muốn tải xuống " + path + "?", this).showConfirmDialog("Bạn chắc chứ", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
            return;
        }
        new DownloadFileTask(path).execute();
        
    }//GEN-LAST:event_confirmBtnActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton chooseFileBtn;
    private javax.swing.JButton confirmBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField pathInput;
    private javax.swing.JProgressBar progressBar;
    // End of variables declaration//GEN-END:variables

    class DownloadFileTask extends SwingWorker<Void, Integer> {

        FileOutputStream fos;
        InputStream input;
        int BUFFER_SIZE = 100;
        byte[] buffer = new byte[BUFFER_SIZE];
        int count = 0;
        long totalRead = 0;
        String path;
        long fileSize;

        public DownloadFileTask(String path) {
            this.path = path;
            try {
                fos = new FileOutputStream(path);
                input = client.getInputStream();
                dos.writeUTF("DOWNLOAD_FILE_BEGIN");
                fileSize = dis.readLong();
                confirmBtn.setEnabled(false);
                confirmBtn.setText("Đang tải xuống...");
                chooseFileBtn.setEnabled(false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground() throws Exception {
            while (totalRead != fileSize) {
                BUFFER_SIZE = dis.readInt();
                dis.readFully(buffer, 0, BUFFER_SIZE);
                totalRead += BUFFER_SIZE;
                fos.write(buffer, 0, BUFFER_SIZE);
                int p = (int) (((float) totalRead / (float) fileSize) * 100f);
                publish(p);
            }
            return null;
        }

        @Override
        protected void done() {
            try {
                fos.flush();
                fos.close();
                int t = new MyOptionPane("Tải xuống tập tin thành công, bạn có muốn mở tập tin?", DownloadFile.this).showConfirmDialog("Mở tập tin?", JOptionPane.YES_NO_OPTION);
                if (t == JOptionPane.YES_OPTION) {
                    Desktop.getDesktop().open(new File(path));
                }
                dispose();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

        @Override
        protected void process(List<Integer> chunks) {
            
            progressBar.setValue(chunks.get(chunks.size() - 1));
        }

    }
}
