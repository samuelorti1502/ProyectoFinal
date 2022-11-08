/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import Clases.ClsConnect;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.CallableStatement;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Samuel
 */
public class ClsFunciones {

    public static String Usuario = "sdortizm@gmail.com";
    public static String Contra = "omcdzjovkpuhchlw";

    private String codigo;
    private String fecha;
    private String tipo;

    String Para = "sdortizm@gmail.com";
    String Asunto = "";
    String Mensaje = "";

    private Properties mProps;
    private Session mSesion;
    private MimeMessage mCorreo;

    Map<String, String> inLineImages = new HashMap<String, String>();
    StringBuffer body;

    ClsConnect cn = new ClsConnect();

    public void Clear(JPanel intFrame) {

        for (Component control : intFrame.getComponents()) {
            if (control instanceof JTextField || control instanceof JTextArea) {
                ((JTextComponent) control).setText(""); //abstract superclass
            }
        }
    }

    public static String getHash(String txt, String hashType) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance(hashType);
            byte[] array = md.digest(txt.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /* Retorna un hash MD5 a partir de un texto */
    public static String md5(String txt) {
        return ClsFunciones.getHash(txt, "MD5");
    }

    /* Retorna un hash SHA1 a partir de un texto */
    public static String sha1(String txt) {
        return ClsFunciones.getHash(txt, "SHA1");
    }

    public void crearQR(String codigo, String tipo, String fecha) {
        try {
            this.fecha = fecha;
            this.codigo = codigo;
            this.tipo = tipo;

            QRCodeWriter qrCode = new QRCodeWriter();
            BitMatrix bqr = qrCode.encode(codigo + tipo + " F " + fecha, BarcodeFormat.QR_CODE.QR_CODE, 200, 200);
            Path pQr = FileSystems.getDefault().getPath("./src/imagenes/QR/" + codigo + "E.png");
            MatrixToImageWriter.writeToPath(bqr, "PNG", pQr);
        } catch (WriterException ex) {
            Logger.getLogger(ClsFunciones.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClsFunciones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertarTicket(String tipo, String usuario, int opc) {
        System.out.println("fecha " + fecha);

        try {

            cn.conexion("proyectofinal", "umg", "1234");

            CallableStatement cStmt = cn.con.prepareCall("{CALL sp_ticket(?, ?, ?, ?)}");

            cStmt.setString(1, tipo);
            cStmt.setString(2, usuario);
            cStmt.setInt(3, opc);
            cStmt.setString(4, fecha);

            cStmt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(ClsFunciones.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void obtenerTicket() {

    }

    public void crearCorreo() {
        Para = "sortizm5@miumg.edu.gt";
        Asunto = "Ticket de " + tipo;
        Mensaje = "Usted ha generado un ticket de " + tipo + " el " + fecha;

        mProps = new Properties();

        mProps.put("mail.smtp.host", "smtp.gmail.com");
        mProps.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        mProps.setProperty("mail.smtp.starttls.enable", "true");
        mProps.setProperty("mail.smtp.port", "587");
        mProps.setProperty("mail.smtp.user", Usuario);
        mProps.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        mProps.setProperty("mail.smtp.auth", "true");

        mSesion = Session.getDefaultInstance(mProps);

        try {
            mCorreo = new MimeMessage(mSesion);
            mCorreo.setFrom(new InternetAddress(Usuario));
            mCorreo.setRecipient(Message.RecipientType.TO, new InternetAddress(Para));
            mCorreo.setSubject(Asunto);
            //mCorreo.setText(Mensaje, "ISO-8859-1", "html");
            mCorreo.setSentDate(new Date());

            body = new StringBuffer("<html>Este mensaje es de prueba.<br>");
            body.append("<img scr=\"cid:image1\" width=\"30%\" height=\"30%\" /><br>");
            body.append("Fin");
            body.append("</html>");

            //Map<String, String> inLineImages = new HashMap<String, String>();
            inLineImages.put("image1", "./src/imagenes/QR/" + this.codigo + "E.png");

        } catch (AddressException ex) {
            Logger.getLogger(ClsFunciones.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(ClsFunciones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void enviarCorreo() {
        try {

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(body.toString(), "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            if (inLineImages != null && inLineImages.size() > 0) {
                Set<String> setImageID = inLineImages.keySet();

                for (String contentId : setImageID) {
                    MimeBodyPart imagePart = new MimeBodyPart();
                    imagePart.setHeader("Content-ID", "<" + contentId + ">");
                    imagePart.setDisposition(MimeBodyPart.INLINE);

                    String imageFilePath = inLineImages.get(contentId);
                    try {
                        imagePart.attachFile(imageFilePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    multipart.addBodyPart(imagePart);
                }
            }

            mCorreo.setContent(multipart);

            Transport mTransport = mSesion.getTransport("smtp");
            mTransport.connect(Usuario, Contra);
            mTransport.sendMessage(mCorreo, mCorreo.getRecipients(Message.RecipientType.TO));
            mTransport.close();
            JOptionPane.showMessageDialog(null, "Correo enviado");
        } catch (MessagingException ex) {
            Logger.getLogger(ClsFunciones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void leerQR() {
        //File codigoQR = new File("./src/imagenes/QR/2540966540101E.png");

        String codigoQR = "./src/imagenes/QR/2540966540101E.png";

        Map<DecodeHintType, Object> tmpHintsMap = new EnumMap<DecodeHintType, Object>(DecodeHintType.class);
        tmpHintsMap.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        tmpHintsMap.put(DecodeHintType.POSSIBLE_FORMATS, EnumSet.allOf(BarcodeFormat.class));
        tmpHintsMap.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
    }

    public void imprimir(String reporte, String titulo) {
        
    }

    public class ImgTabla extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            if (value instanceof JLabel) {
                JLabel lbl = (JLabel) value;
                return lbl;
            }

            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
}
