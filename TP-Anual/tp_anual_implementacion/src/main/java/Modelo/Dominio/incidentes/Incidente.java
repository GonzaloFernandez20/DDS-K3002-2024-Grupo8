package Modelo.Dominio.incidentes;

import Modelo.Dominio.heladera.Heladera;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "incidente")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Incidente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_incidente;

    @Column(name = "momento_del_suceso")
    protected LocalDateTime momentoDelSuceso;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "heladera_donde_ocurrio", referencedColumnName = "id_heladera")
    protected Heladera heladeraDondeOcurrio;

    @OneToMany(mappedBy = "incidenteAtendido", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.EAGER)
    protected List <VisitaTecnica> visitas;

    @Enumerated(EnumType.STRING)
    protected EstadoDelIncidente estado;

    public void registrarVisita(VisitaTecnica visitaTecnica){
        visitas.add(visitaTecnica);
        this.estado = visitaTecnica.getEstadoVisita();
    }
    public abstract String obtenerInformacion();

    public LocalDateTime getMomentoDelSuceso() { return momentoDelSuceso; }
    public Heladera getHeladeraDondeOcurrio() { return heladeraDondeOcurrio; }
    public EstadoDelIncidente getEstado() { return estado; }
    public List<VisitaTecnica> getVisitas() { return visitas; }

    public void setMomentoDelSuceso(LocalDateTime momentoDelSuceso) {
        this.momentoDelSuceso = momentoDelSuceso;
    }

    public void setHeladeraDondeOcurrio(Heladera heladeraDondeOcurrio) {
        this.heladeraDondeOcurrio = heladeraDondeOcurrio;
    }

    public void setVisitas(List<VisitaTecnica> visitas) {
        this.visitas = visitas;
    }

    public void setEstado(EstadoDelIncidente estado) {
        this.estado = estado;
    }
}
