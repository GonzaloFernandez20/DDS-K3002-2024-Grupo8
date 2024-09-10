package DTOs;

import Modelo.Dominio.incidentes.EstadoDelIncidente;
import Modelo.Dominio.incidentes.Incidente;
import Modelo.Dominio.tecnico.Tecnico;

public class VisitaTecnicaDTO {
    private EstadoDelIncidente estadoVisita;
    private String detalleDeTrabajo;
    private Tecnico tecnico;
    private String linkFoto;
    private Incidente incidenteAsociado;

    public VisitaTecnicaDTO(EstadoDelIncidente estadoVisita, String detalleDeTrabajo, Tecnico tecnico, String linkFoto, Incidente incidenteAsociado) {
        this.estadoVisita = estadoVisita;
        this.detalleDeTrabajo = detalleDeTrabajo;
        this.tecnico = tecnico;
        this.linkFoto = linkFoto;
        this.incidenteAsociado = incidenteAsociado;
    }

    public EstadoDelIncidente getEstadoVisita() { return estadoVisita; }
    public void setEstadoVisita(EstadoDelIncidente estadoVisita) { this.estadoVisita = estadoVisita; }
    public String getDetalleDeTrabajo() { return detalleDeTrabajo; }
    public void setDetalleDeTrabajo(String detalleDeTrabajo) { this.detalleDeTrabajo = detalleDeTrabajo; }
    public Tecnico getTecnico() { return tecnico; }
    public void setTecnico(Tecnico tecnico) { this.tecnico = tecnico; }
    public String getLinkFoto() { return linkFoto; }
    public void setLinkFoto(String linkFoto) { this.linkFoto = linkFoto; }
    public Incidente getIncidenteAsociado() { return incidenteAsociado; }
    public void setIncidenteAsociado(Incidente incidenteAsociado) { this.incidenteAsociado = incidenteAsociado; }
}
