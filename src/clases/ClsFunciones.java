/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Samuel
 */
public class ClsFunciones {
    
    public void Clear(JPanel intFrame) {

        for (Component control : intFrame.getComponents()) {
            if (control instanceof JTextField || control instanceof JTextArea) {
                ((JTextComponent) control).setText(""); //abstract superclass
            }
        }
    }
    
}
