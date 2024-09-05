package reportes;

import heladera.Heladera;

import java.time.LocalDate;
import java.util.List;

public class ReporteDeFallas extends ReporteSemanal{
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
