package Modelo.Dominio.reportes;

import com.itextpdf.text.*;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.util.List;
import java.io.FileOutputStream;
import java.time.LocalDate;

public class GeneradorPDFReporte {
    public static void documentar(ReporteSemanal reporteSemanal) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream("src/main/resources/static/reportes/" + reporteSemanal.getClass().getSimpleName() + ".pdf"));

            LocalDate fechaDeCreacion = reporteSemanal.getFechaDeCreacion();

            document.open();

            Paragraph semanaDelReporte = new Paragraph("Semana del " + fechaDeCreacion.getDayOfMonth() + "/" + fechaDeCreacion.getMonthValue() + "/" + fechaDeCreacion.getYear() + ":");
            semanaDelReporte.setSpacingAfter(15);
            document.add(semanaDelReporte);

            List<String> headers = reporteSemanal.obtenerNombreDeAtributosDelReporte();

            int cantidadDeAtributos = headers.size();
            PdfPTable tabla = new PdfPTable(cantidadDeAtributos);

            for(int i=0; i<cantidadDeAtributos; i++){
                PdfPCell header = new PdfPCell(new Phrase(headers.get(i)));
                header.setBackgroundColor(WebColors.getRGBColor("#bff2e4"));
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabla.addCell(header);
            }

            reporteSemanal.completarTablaConAtributos(tabla);

            document.add(tabla);

            document.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
