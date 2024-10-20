package Modelo.Dominio.reportes;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDate;

@MappedSuperclass
public class ReporteSemanal {
    @Id
    @GeneratedValue
    private Integer id_reporte;
    @Column(name = "fecha_de_creacion")
    private LocalDate fechaDeCreacion;

    public ReporteSemanal(LocalDate fechaDeCreacion) {
        this.fechaDeCreacion = LocalDate.now();
    }

    public LocalDate getFechaDeCreacion() {
        return fechaDeCreacion;
    }

    public void completarReporte(){}
}
