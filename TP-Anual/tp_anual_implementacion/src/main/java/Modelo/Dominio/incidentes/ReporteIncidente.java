package Modelo.Dominio.incidentes;

import Modelo.Dominio.heladera.EstadoHeladera;
import Modelo.Dominio.heladera.Heladera;

import java.time.LocalDateTime;

import static java.sql.Types.NULL;

public class ReporteIncidente {
    private LocalDateTime momentoDelReporte;
    private Heladera heladera;
    private TipoDeIncidente tipoDeIncidente;
    private EstadoDelIncidente estadoDelIncidente;
    private GestorDeSuscripciones gestorDeSuscripciones;
    private ReporteDeTodasLasHeladeras reporteDeTodasLasHeladeras;

    public ReporteIncidente(Heladera heladera, TipoDeIncidente tipoDeIncidente, EstadoDelIncidente estadoDelIncidente) {
        this.momentoDelReporte = LocalDateTime.now();
        this.heladera = heladera;
        this.tipoDeIncidente = tipoDeIncidente;
        this.estadoDelIncidente = estadoDelIncidente;
    }

    public void setGestorDeSuscripciones(GestorDeSuscripciones gestorDeSuscripciones) {
        this.gestorDeSuscripciones = gestorDeSuscripciones;
    }
    public void setReporteDeTodasLasHeladeras(ReporteDeTodasLasHeladeras reporteDeTodasLasHeladeras) {
        this.reporteDeTodasLasHeladeras = reporteDeTodasLasHeladeras;
    }

    public void reportar(){
        heladera.setEstado(EstadoHeladera.inactiva);
        gestorDeSuscripciones.serNotificadoAnte(NULL, this.heladera);
        reporteDeTodasLasHeladeras.recibirReporte(heladera, NULL);
    }
    public void solucionar(){
        this.setEstadoDelIncidente(EstadoDelIncidente.SOLUCIONADO);
        heladera.setEstado(EstadoHeladera.activa);
    }

//NO TIENEN LÓGICA INTERESANTE

    public LocalDateTime getMomentoDelReporte() {
        return momentoDelReporte;
    }

    public void setMomentoDelReporte(LocalDateTime momentoDelReporte) {
        this.momentoDelReporte = momentoDelReporte;
    }

    public Heladera getHeladera() {
        return heladera;
    }

    public void setHeladera(Heladera heladera) {
        this.heladera = heladera;
    }

    public TipoDeIncidente getTipoDeIncidente() {
        return tipoDeIncidente;
    }

    public void setTipoDeIncidente(TipoDeIncidente tipoDeIncidente) {
        this.tipoDeIncidente = tipoDeIncidente;
    }

    public EstadoDelIncidente getEstadoDelIncidente() {
        return estadoDelIncidente;
    }

    public void setEstadoDelIncidente(EstadoDelIncidente estadoDelIncidente) {
        this.estadoDelIncidente = estadoDelIncidente;
    }
}
