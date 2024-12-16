package Modelo.Dominio.reportes;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import com.itextpdf.text.pdf.PdfPTable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDate;

@Service
public class ReporteSemanal {
    protected LocalDate fechaDeCreacion;

    public ReporteSemanal(LocalDate fechaDeCreacion) {
        this.fechaDeCreacion = LocalDate.now();
    }

    public ReporteSemanal() {}

    public LocalDate getFechaDeCreacion() {
        return fechaDeCreacion;
    }

    public void completarReporte() {
        GeneradorPDFReporte.documentar(this);
    }

    public List<String> obtenerNombreDeAtributosDelReporte() {
        return null;
    }

    public void setFechaDeCreacion(LocalDate fechaDeCreacion) {
        this.fechaDeCreacion = fechaDeCreacion;
    }

    public void completarTablaConAtributos(PdfPTable tabla) {}
}
