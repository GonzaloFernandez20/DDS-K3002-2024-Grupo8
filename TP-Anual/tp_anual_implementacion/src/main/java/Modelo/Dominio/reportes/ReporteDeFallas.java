package Modelo.Dominio.reportes;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "ReporteFallas")
public class ReporteDeFallas extends ReporteSemanal{
    @OneToMany
    @JoinColumn(name = "id_reporte", referencedColumnName = "id_reporte")
    private List<FallasPorHeladera> fallasPorHeladera;

    public ReporteDeFallas(LocalDate fechaDeCreacion) {
        super(fechaDeCreacion);
    }
    @Override
    public void completarReporte(){
        //NECESITO TENER DEFINIDA LA ESTRUCTURA DEL PAQUETE INCIDENTE ANTES DE AVANZAR
    }
    public void sumarFallasPorheladera(FallasPorHeladera unaFallaPorHeladera){
        fallasPorHeladera.add(unaFallaPorHeladera);
    }
}
