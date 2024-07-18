package incidentes;

import heladera.EstadoHeladera;
import heladera.Heladera;
import sistema.Sistema;

import java.time.LocalDateTime;

public class ReporteIncidente {
    private LocalDateTime momentoDelReporte;
    private Heladera heladera;
    private TipoDeIncidente tipoDeIncidente;
    private EstadoDelIncidente estadoDelIncidente;

    public ReporteIncidente(Heladera heladera, TipoDeIncidente tipoDeIncidente, EstadoDelIncidente estadoDelIncidente) {
        this.momentoDelReporte = LocalDateTime.now();
        this.heladera = heladera;
        this.tipoDeIncidente = tipoDeIncidente;
        this.estadoDelIncidente = estadoDelIncidente;
    }

    public void reportar(){
        heladera.setEstado(EstadoHeladera.inactiva);
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
