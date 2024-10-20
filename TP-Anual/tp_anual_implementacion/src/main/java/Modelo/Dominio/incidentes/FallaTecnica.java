package Modelo.Dominio.incidentes;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
@Entity
@Table(name = "FallaTecnica")
public class FallaTecnica extends Incidente{
    @Id
    @GeneratedValue
    private  Integer id_falla_tecnica;
    @OneToOne
    @JoinColumn(name = "id_colaborador", referencedColumnName = "id_colaborador")
    private Colaborador colaboradorInformante;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "foto")
    private String linkFoto;

    public FallaTecnica(Colaborador colaboradorInformante, String descripcion, Heladera heladera, String linkFoto) {
        this.colaboradorInformante = colaboradorInformante;
        this.descripcion = descripcion;
        this.heladeraDondeOcurrio = heladera;
        this.momentoDelSuceso = LocalDateTime.now();
        this.visitas = new ArrayList<>();
        this.estado = EstadoDelIncidente.PENDIENTE;
        this.linkFoto = linkFoto;
    }

    @Override
    public String obtenerInformacion() {
        return "se produjo una Falla Tecnica: \n" + "Descripcion: " + descripcion + "\n" + "Link Foto: " + linkFoto;
    }
}
