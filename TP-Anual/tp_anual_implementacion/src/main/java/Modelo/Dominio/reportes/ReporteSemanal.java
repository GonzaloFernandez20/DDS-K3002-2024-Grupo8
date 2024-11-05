package Modelo.Dominio.reportes;

import com.itextpdf.text.pdf.PdfPTable;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.time.LocalDate;
import java.util.stream.Collectors;

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
