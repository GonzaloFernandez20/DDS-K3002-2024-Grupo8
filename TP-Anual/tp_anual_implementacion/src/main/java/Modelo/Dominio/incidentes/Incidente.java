package Modelo.Dominio.incidentes;

import Modelo.Dominio.heladera.Heladera;

import java.time.LocalDateTime;
import java.util.List;

public abstract class Incidente {
    protected LocalDateTime momentoDelSuceso;
    protected Heladera heladeraDondeOcurrio;
    protected List <VisitaTecnica> visitas;
    protected EstadoDelIncidente estado;

    public void registrarVisita(VisitaTecnica visitaTecnica){
        visitas.add(visitaTecnica);
        this.estado = visitaTecnica.getEstadoVisita();
    }
    public abstract String obtenerInformacion();

    public Heladera getHeladeraDondeOcurrio() { return heladeraDondeOcurrio; }
}
