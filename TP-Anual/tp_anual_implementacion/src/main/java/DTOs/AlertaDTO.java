package DTOs;

import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.incidentes.EstadoDelIncidente;
import Modelo.Dominio.incidentes.TipoAlerta;

import java.time.LocalDateTime;
import java.util.List;

public class AlertaDTO {
    public int idAlerta;
    public LocalDateTime momentoDelSuceso;
    public HeladeraDTO heladeraDTO;
    public List<VisitaTecnicaDTO> visitasTecnicasDTO;
    public EstadoDelIncidente estado;    
    public TipoAlerta tipoAlerta;

    public AlertaDTO(LocalDateTime momentoDelSuceso, HeladeraDTO heladeraDTO, List<VisitaTecnicaDTO> visitasTecnicasDTO, EstadoDelIncidente estado, TipoAlerta tipoAlerta) {
        this.momentoDelSuceso = momentoDelSuceso;
        this.heladeraDTO = heladeraDTO;
        this.visitasTecnicasDTO  = visitasTecnicasDTO;
        this.estado = estado;
        this.tipoAlerta = tipoAlerta;
    }

    public LocalDateTime getMomentoDelSuceso() { return momentoDelSuceso; }
    public void setMomentoDelSuceso(LocalDateTime momentoDelSuceso) { this.momentoDelSuceso = momentoDelSuceso; }
    public HeladeraDTO getheladeraDTO() { return heladeraDTO; }
    public void setheladeraDTO(HeladeraDTO heladeraDTO) { this.heladeraDTO = heladeraDTO; }
    public List<VisitaTecnicaDTO> getvisitasTecnicasDTO() { return visitasTecnicasDTO; }
    public void setvisitasTecnicasDTO(List<VisitaTecnicaDTO> visitasTecnicasDTO) { this.visitasTecnicasDTO = visitasTecnicasDTO; }
    public EstadoDelIncidente getEstado() { return estado; }
    public void setEstado(EstadoDelIncidente estado) { this.estado = estado; }
    public TipoAlerta getTipoAlerta() { return tipoAlerta; }
    public void setTipoAlerta(TipoAlerta tipoAlerta) { this.tipoAlerta = tipoAlerta; }
}
