/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */
public class MyOptionPane {
        
        private Object message;
        private Component com;        
        public MyOptionPane(String message,Component com){
            JLabel label = new JLabel(message);
            label.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
            this.message = label;
            this.com = com;
        }
        
        public void showMessageDialog(String title, int messageType){
            JOptionPane.showMessageDialog(com, message,  title, messageType);
        } 
        public int showConfirmDialog(String title, int optionType){
            return JOptionPane.showConfirmDialog(com, message, title, optionType, JOptionPane.QUESTION_MESSAGE);
        }
    }