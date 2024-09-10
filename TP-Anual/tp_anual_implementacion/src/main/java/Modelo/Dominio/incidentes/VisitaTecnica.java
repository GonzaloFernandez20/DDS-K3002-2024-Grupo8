package Modelo.Dominio.incidentes;

import Modelo.Dominio.tecnico.Tecnico;

import java.time.LocalDateTime;

public class VisitaTecnica {
    private Tecnico tecnico;
    private LocalDateTime fechaDeVisita;
    private String detalleDeTrabajo;
    private String linkFoto;
    private EstadoDelIncidente estadoVisita;
    private Incidente incidenteAtendido;

    public VisitaTecnica(EstadoDelIncidente estadoVisita, String detalleDeTrabajo, Tecnico tecnico, String linkFoto, Incidente incidenteAtendido) {
        this.estadoVisita = estadoVisita;
        this.detalleDeTrabajo = detalleDeTrabajo;
        this.tecnico = tecnico;
        this.linkFoto = linkFoto;
        this.fechaDeVisita = LocalDateTime.now();
        this.incidenteAtendido = incidenteAtendido;
    }

    public EstadoDelIncidente getEstadoVisita() { return estadoVisita; }
    public Incidente getIncidenteAtendido() { return incidenteAtendido; }

}
