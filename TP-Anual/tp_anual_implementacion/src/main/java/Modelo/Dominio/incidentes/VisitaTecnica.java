package Modelo.Dominio.incidentes;

import Modelo.Dominio.tecnico.Tecnico;
import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "VisitaTecnica")
public class VisitaTecnica {
    @Id
    @GeneratedValue
    private Integer id_visita_tecnica;
    @ManyToOne
    @JoinColumn(name = "tecnico", referencedColumnName = "id_tecnico")
    private Tecnico tecnico;
    @Column(name = "fecha_de_visita")
    private LocalDateTime fechaDeVisita;
    @Column(name = "detalle_de_trabajo")
    private String detalleDeTrabajo;
    @Column(name = "link_foto")
    private String linkFoto;
    @Enumerated(EnumType.STRING)
    private EstadoDelIncidente estadoVisita;
    @ManyToOne
    @JoinColumn(name = "incidenteAtendido", referencedColumnName = "id_incidente")
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

    public Integer getId_visita_tecnica() {
        return id_visita_tecnica;
    }

    public void setId_visita_tecnica(Integer id_visita_tecnica) {
        this.id_visita_tecnica = id_visita_tecnica;
    }
}
