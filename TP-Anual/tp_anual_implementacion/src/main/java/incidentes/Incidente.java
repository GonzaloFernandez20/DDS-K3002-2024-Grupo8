package incidentes;

import heladera.EstadoHeladera;
import heladera.Heladera;
import sistema.Sistema;

import java.util.List;

public abstract class Incidente {
    private ReporteIncidente reporteIncidente;
    private List<VisitaPorIncidente> visitasPorIncidente;
    private EstadoDelIncidente estadoDelIncidente;
    private Heladera heladera;

    public Heladera getHeladera() {
        return heladera;
    }

    public void setHeladera(Heladera heladera) {
        this.heladera = heladera;
    }

    public void setVisitaPorIncidente(List<VisitaPorIncidente> visitasPorIncidente) {
        this.visitasPorIncidente = visitasPorIncidente;
    }

    public EstadoDelIncidente getEstadoDelIncidente() {
        return estadoDelIncidente;
    }

    public void setEstadoDelIncidente(EstadoDelIncidente estadoDelIncidente) {
        this.estadoDelIncidente = estadoDelIncidente;
    }

    public Incidente(ReporteIncidente reporteIncidente, EstadoDelIncidente estadoDelIncidente, Heladera heladera) {
        this.reporteIncidente = reporteIncidente;
        this.estadoDelIncidente = estadoDelIncidente;
        this.heladera = heladera;
    }

    //Me parece que es responsabilidad del gestor de incidentes
    //public void avisarAlTecnico(){}
    public void registrarVisita(){}
    public void reportar(){
        Sistema sistema = Sistema.getInstancia();
        sistema.sumarIncidente(this);
        heladera.setEstado(EstadoHeladera.inactiva);
    }
    public void hacerNotificarAHeladera(){
        //NO ENTIENDO QUE HACE
    }
    public void solucionar(){
        this.setEstadoDelIncidente(EstadoDelIncidente.SOLUCIONADO);
        heladera.setEstado(EstadoHeladera.activa);
        GestorDeIncidentes gestorDeIncidentes = GestorDeIncidentes.getInstancia();
    gestorDeIncidentes.pasarIncidenteASolucionado(this);
    }

    public Incidente(ReporteIncidente reporteIncidente) {
        this.reporteIncidente = reporteIncidente;
    }

    public List<VisitaPorIncidente> getVisitaPorIncidente() {
        return visitasPorIncidente;
    }

    public ReporteIncidente getReporteIncidente() {
        return reporteIncidente;
    }

    public void setReporteIncidente(ReporteIncidente reporteIncidente) {
        this.reporteIncidente = reporteIncidente;
    }

    public boolean equalsIncidente(Object incidenteAux) {
        return incidenteAux.equals(this);
    }
    public void atender(VisitaPorIncidente visita) {
        if (visita.visitaSolucionaElIncidente()) {
            this.solucionar();
        }
        visitasPorIncidente.add(visita);
    }
}
