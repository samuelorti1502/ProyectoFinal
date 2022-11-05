/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

/**
 *
 * @author Samuel
 */
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClsPDF {

    Date date = new Date();
    private String gestion;
    private String documento;

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
            table.addCell(createImageCell("./src/imagenes/Fotos/U "+ this.getDocumento() +".jpg"));
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

}
