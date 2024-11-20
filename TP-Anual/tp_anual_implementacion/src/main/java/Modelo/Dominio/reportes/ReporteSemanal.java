package Modelo.Dominio.reportes;

import com.itextpdf.text.pdf.PdfPTable;

import java.util.List;
import java.time.LocalDate;

public class ReporteSemanal {
    private LocalDate fechaDeCreacion;

    public ReporteSemanal(LocalDate fechaDeCreacion) {
        this.fechaDeCreacion = LocalDate.now();
    }

    public LocalDate getFechaDeCreacion() {
        return fechaDeCreacion;
    }

    public void completarReporte() {
        GeneradorPDFReporte.documentar(this);
    }

    public List<String> obtenerNombreDeAtributosDelReporte() {
        return null;
    }

    public void completarTablaConAtributos(PdfPTable tabla) { }
}
