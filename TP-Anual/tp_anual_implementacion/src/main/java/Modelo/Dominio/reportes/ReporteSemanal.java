package Modelo.Dominio.reportes;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import com.itextpdf.text.pdf.PdfPTable;

import java.util.List;
import java.time.LocalDate;

@MappedSuperclass
public class ReporteSemanal {
    @Id
    @GeneratedValue
    private Integer id_reporte;
    @Column(name = "fecha_de_creacion")
    protected LocalDate fechaDeCreacion;

    public ReporteSemanal(LocalDate fechaDeCreacion) {
        this.fechaDeCreacion = LocalDate.now();
    }

    public ReporteSemanal() {
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
