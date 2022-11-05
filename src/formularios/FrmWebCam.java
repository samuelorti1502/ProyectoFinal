/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package formularios;

/**
 *
 * @author Samuel
 */
import javax.swing.*;

import com.github.sarxos.webcam.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import org.netbeans.lib.awtextra.AbsoluteLayout;

public class FrmWebCam {

    private JFrame ventana;
    private JPanel pnlGeneral;
    private JPanel pnlFoto;
    private JPanel pnlCam;
    private JPanel pnlBoton;

    private JLabel lblFoto1;
    private JLabel lblFoto2;
    private JLabel lblCam;
    private JButton btnTomar;

    int largo = 440;//ancho
    int ancho = 270;//largo
    BufferedImage ruta;
    int contador = 0;
    Dimension dimension = new Dimension(largo, ancho);
    Dimension dimension1 = WebcamResolution.VGA.getSize();
    Webcam webcam = Webcam.getDefault();
    WebcamPanel webcamPanel = new WebcamPanel(webcam, dimension, false);

    private int tipo;

    public FrmWebCam(int tipo) {
        this.setTipo(tipo);
        dibujarControles();

        webcam.setViewSize(dimension1);
        webcamPanel.setFitArea(true);
        pnlCam.setLayout(new FlowLayout());
        pnlCam.add(webcamPanel);

        iniciarCamara();
    }

    public void iniciarCamara() {
        Thread hilo = new Thread() {
            @Override
            public void run() {
                webcamPanel.start();
            }
        };
        hilo.setDaemon(true);
        hilo.start();
    }

    public void dibujarControles() {
        ventana = new JFrame("Memoria Game");

        pnlGeneral = new JPanel();
        pnlFoto = new JPanel();
        pnlCam = new JPanel();
        pnlBoton = new JPanel();

        lblFoto1 = new JLabel();
        lblFoto2 = new JLabel();
        lblCam = new JLabel();

        btnTomar = new JButton();

        boolean visibilidad = true;
        int tamFoto;

        if (this.getTipo() == 1) {
            ventana.setSize(400, 800);
            visibilidad = false;
            tamFoto = 1;
        } else {
            ventana.setSize(800, 500);
            tamFoto = 2;
        }

        //ventana.setLayout(null);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(false);
        //ventana.setPreferredSize(new Dimension(800,500));

        pnlGeneral.setSize(ventana.getWidth() - 20, ventana.getHeight() - 20);
        pnlGeneral.setLocation(10, 10);
        pnlGeneral.setLayout(null);
        pnlGeneral.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0)));
        pnlGeneral.setVisible(true);

        pnlFoto.setSize(pnlGeneral.getWidth() - 20, (pnlGeneral.getHeight() - 20) / 2);
        pnlFoto.setLocation(10, 10);
        //pnlFoto.setLayout(new AbsoluteLayout());
        pnlFoto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0)));
        pnlFoto.setVisible(true);

        lblFoto1.setSize(pnlFoto.getWidth() / tamFoto, pnlFoto.getHeight());
        lblFoto1.setLocation(0, 0);
        lblFoto1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 125, 0)));

        lblFoto1.setVisible(true);
        pnlFoto.add(lblFoto1, 0);

        pnlGeneral.add(pnlFoto, 0);
        ventana.add(pnlGeneral, BorderLayout.CENTER);

        ventana.setVisible(true);
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

}
