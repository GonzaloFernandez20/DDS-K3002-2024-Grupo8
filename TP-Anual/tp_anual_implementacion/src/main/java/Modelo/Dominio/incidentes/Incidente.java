package Modelo.Dominio.incidentes;

import Modelo.Dominio.heladera.Heladera;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Incidente")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Incidente {
    @Id
    @GeneratedValue
    private  Integer id_incidente;
    @Column(name = "momento_del_suceso")
    protected LocalDateTime momentoDelSuceso;
    @OneToOne
    @JoinColumn(name = "heladera_donde_ocurrio", referencedColumnName = "id_heladera")
    protected Heladera heladeraDondeOcurrio;
    @OneToMany(mappedBy = "incidenteAtendido", cascade = CascadeType.ALL, orphanRemoval = true)
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
}
