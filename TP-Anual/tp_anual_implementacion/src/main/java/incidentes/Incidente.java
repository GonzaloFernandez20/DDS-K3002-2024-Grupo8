package incidentes;

import heladera.EstadoHeladera;
import heladera.Heladera;
import sistema.Sistema;

import java.util.List;

public abstract class Incidente {
    private ReporteIncidente reporteIncidente;
    private List<VisitaPorIncidente> visitasPorIncidente;

    public Heladera getHeladera() {
        return reporteIncidente.getHeladera();
    }

    public TipoDeIncidente getTipoDeIncidente(){
        return reporteIncidente.getTipoDeIncidente();
    }

    public void setVisitaPorIncidente(List<VisitaPorIncidente> visitasPorIncidente) {
        this.visitasPorIncidente = visitasPorIncidente;
    }

    public EstadoDelIncidente getEstadoDelIncidente() {
        return reporteIncidente.getEstadoDelIncidente();
    }

    public void setEstadoDelIncidente(EstadoDelIncidente estadoDelIncidente) {
        reporteIncidente.setEstadoDelIncidente(estadoDelIncidente);
    }

    public Incidente(ReporteIncidente reporteIncidente) {
        this.reporteIncidente = reporteIncidente;
        this.hacerNotificarAHeladera();
    }

    //Me parece que es responsabilidad del gestor de incidentes
    //public void avisarAlTecnico(){}
    public void registrarVisita(){}
    public void reportar(){
        Sistema sistema = Sistema.getInstancia();
        sistema.sumarIncidente(this);
        reporteIncidente.reportar();
    }
    public void hacerNotificarAHeladera(){
        //NO ENTIENDO QUE HACE
        //tiene que ver con la parte de los reportes que dice "Cantidad de Fallas por Heladera"
        //cada vez que hay una falla hay que sumarlo al reporte semanal (distinto del reporte que tiene que ver con el incidente)
    }
    public void solucionar(){
        reporteIncidente.solucionar();
        GestorDeIncidentes gestorDeIncidentes = GestorDeIncidentes.getInstancia();
        gestorDeIncidentes.pasarIncidenteASolucionado(this);
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
