package Modelo.Dominio.incidentes;

import Modelo.Dominio.tecnico.Tecnico;

import java.time.LocalDateTime;

public class VisitaTecnica {
    private Tecnico tecnico;
    private LocalDateTime fechaDeVisita;
    private String detalleDeTrabajo;
    private String linkFoto;
    private EstadoDelIncidente estadoVisita;

    public VisitaTecnica(EstadoDelIncidente estadoVisita, String detalleDeTrabajo, Tecnico tecnico, String linkFoto) {
        this.estadoVisita = estadoVisita;
        this.detalleDeTrabajo = detalleDeTrabajo;
        this.tecnico = tecnico;
        this.linkFoto = linkFoto;
        this.fechaDeVisita = LocalDateTime.now();
    }

    public EstadoDelIncidente getEstadoVisita() { return estadoVisita; }

}
