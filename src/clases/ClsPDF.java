/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

/**
 *
 * @author Samuel
 */
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class ClsPDF {

    Date date = new Date();
    private String gestion;
    private String documento;

    private String modulo;
    private int tipo;
    private static final String IMG = "./src/imagenes/umg.png";

    private String ruta = null;
    private String imagen;
    private String reporte;
    private String archivoReporte;
    private String foto;
    private int columnas = 0;
    private float pointColumnWidths[];

    public ClsPDF() {

    }

    public ClsPDF(String modulo, int tipo, String imagen) {
        this.modulo = modulo;
        this.tipo = tipo;
        this.imagen = imagen;
        this.tipoR(tipo);
    }

    public void tipoR(int tipo) {
        SimpleDateFormat DateFor = new SimpleDateFormat("yyyyMMddHHmm");

        switch (tipo) {
            case 1 -> {
                this.setRuta("Usuarios/ReporteUsusarios" + DateFor.format(date) + ".pdf");
                this.setColumnas(5);
                this.setImagen("./src/imagenes/Fotos/U " + this.getDocumento() + ".jpg");
                this.setReporte("Usuarios");
                this.setArchivoReporte("Usuarios/ReporteUsusarios/usuarios.txt");
                this.setPointColumnWidths(new float[getColumnas()]);
                for (int i = 0; i < this.getColumnas(); i++) {
                    this.getPointColumnWidths()[i] = 150F;
                    //System.out.println(this.pointColumnWidths[i]);
                }
            }


        }
    }

    private void tablaTipoReporte(Table table, JTable tablaR, String encabezado[]) {
        String[] u;
        //String foto = null;
        try {
            for (int i = 0; i < encabezado.length; i++) {
                table.addCell(createTextCell(encabezado[i]));
            }

            /*File myObj = new File(this.archivoReporte);
            Scanner myReader = new Scanner(myObj);*/

            for (int filas = 0; filas < tablaR.getRowCount(); filas++) {
                for (int columnas = 0; columnas < tablaR.getColumnCount()-1; columnas++) {
                    table.addCell(tablaR.getModel().getValueAt(filas, columnas).toString());
                    //foto = tablaR.getModel().getValueAt(filas, 4).toString();
                }
                ImageData data = ImageDataFactory.create(getFoto());
                Image img = new Image(data);
                
                table.addCell(img.setAutoScale(true));

            }
        } catch (Exception ex) {
            Logger.getLogger(ClsPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void reportes(String[] encabezado, JTable tabla) {
        String[] u;

        SimpleDateFormat DateFor = new SimpleDateFormat("EEEEE dd MMMMM yyyy HH:mm:ss");

        try {
            PdfDocument pdf = new PdfDocument(new PdfWriter("reportes/" + this.getRuta()));
            Document doc = new Document(pdf, PageSize.A4.rotate());
            doc.setMargins(2, 20, 20, 20);

            Table tablaTitulo = new Table(UnitValue.createPercentArray(new float[]{1, 2}));
            tablaTitulo.addCell(createImageCell(this.getImagen()));
            tablaTitulo.addCell(createTextCell("Reporte de " + this.getReporte() + "\n Guatemala, " + DateFor.format(date)));

            for (int i = 0; i < 4; i++) {
                tablaTitulo.addCell(createTextCell(" "));
            }

            Table table = new Table(this.getPointColumnWidths());

            this.tablaTipoReporte(table, tabla, encabezado);

            doc.add(tablaTitulo);
            doc.add(table);

            // Closing the document       
            doc.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e.getMessage());
        }

        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File("reportes/" + this.getRuta());
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                // no application registered for PDFs
            }
        }
    }

    public void crearPDF() {
        System.out.println(getDocumento());
        Locale locale = new Locale("es", "ES");

        String nombreArchivo = "prueba.pdf";
        SimpleDateFormat DateFor = new SimpleDateFormat("EEEEE dd MMMMM yyyy HH:mm:ss");

        try {

            PdfDocument pdf = new PdfDocument(new PdfWriter("formularios/" + nombreArchivo));
            Document doc = new Document(pdf, new PageSize(612, 396));
            doc.setMargins(2, 20, 20, 20);

            Table table = new Table(UnitValue.createPercentArray(new float[]{50f, 50f}));
            table.addCell(createImageCell("./src/imagenes/Fotos/U " + this.getDocumento() + ".jpg"));
            table.addCell(createImageCell("./src/imagenes/QR/" + this.getDocumento() + "E.png"));
            table.addCell(createTextCell(" No. \n Guatemala, " + DateFor.format(date)));
            table.addCell(createTextCell("Tipo de Gestion: " + this.getGestion()));

            doc.add(table);

            doc.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ClsPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ClsPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static Cell createImageCell(String path) throws MalformedURLException {
        Image img = new Image(ImageDataFactory.create(path));
        img.setWidth(UnitValue.createPercentValue(100));
        Cell cell = new Cell().add(img);
        cell.setBorder(Border.NO_BORDER);
        return cell;
    }

    private static Cell createTextCell(String text) {
        Cell cell = new Cell();
        Paragraph p = new Paragraph(text);
        p.setTextAlignment(TextAlignment.RIGHT);
        cell.add(p).setVerticalAlignment(VerticalAlignment.BOTTOM);
        cell.setBorder(Border.NO_BORDER);

        return cell;
    }

    public String getGestion() {
        return gestion;
    }

    public void setGestion(String gestion) {
        this.gestion = gestion;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public static String getIMG() {
        return IMG;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getReporte() {
        return reporte;
    }

    public void setReporte(String reporte) {
        this.reporte = reporte;
    }

    public String getArchivoReporte() {
        return archivoReporte;
    }

    public void setArchivoReporte(String archivoReporte) {
        this.archivoReporte = archivoReporte;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getColumnas() {
        return columnas;
    }

    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }

    public float[] getPointColumnWidths() {
        return pointColumnWidths;
    }

    public void setPointColumnWidths(float[] pointColumnWidths) {
        this.pointColumnWidths = pointColumnWidths;
    }

}
