package DTOs;

import Modelo.Dominio.incidentes.EstadoDelIncidente;
import Modelo.Dominio.incidentes.TipoAlerta;

import java.time.LocalDateTime;
import java.util.List;

public class AlertaDTO {
    public LocalDateTime momentoDelSuceso;
    public int idHeladera;
    public List<Integer> idVisitasTecnicas;
    public EstadoDelIncidente estado;    
    public TipoAlerta tipoAlerta;

    public AlertaDTO(LocalDateTime momentoDelSuceso, int idHeladera, List<Integer> idVisitasTecnicas, EstadoDelIncidente estado, TipoAlerta tipoAlerta) {
        this.momentoDelSuceso = momentoDelSuceso;
        this.idHeladera = idHeladera;
        this.idVisitasTecnicas  = idVisitasTecnicas;
        this.estado = estado;
        this.tipoAlerta = tipoAlerta;
    }

    public LocalDateTime getMomentoDelSuceso() { return momentoDelSuceso; }
    public void setMomentoDelSuceso(LocalDateTime momentoDelSuceso) { this.momentoDelSuceso = momentoDelSuceso; }
    public int getIdHeladera() { return idHeladera; }
    public void setIdHeladera(int idHeladera) { this.idHeladera = idHeladera; }
    public List<Integer> getIdVisitasTecnicas() { return idVisitasTecnicas; }
    public void setIdVisitasTecnicas(List<Integer> idVisitasTecnicas) { this.idVisitasTecnicas = idVisitasTecnicas; }
    public EstadoDelIncidente getEstado() { return estado; }
    public void setEstado(EstadoDelIncidente estado) { this.estado = estado; }
    public TipoAlerta getTipoAlerta() { return tipoAlerta; }
    public void setTipoAlerta(TipoAlerta tipoAlerta) { this.tipoAlerta = tipoAlerta; }
}
