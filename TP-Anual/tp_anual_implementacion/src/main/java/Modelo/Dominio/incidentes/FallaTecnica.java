package Modelo.Dominio.incidentes;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
@Entity
@Table(name = "falla_tecnica")
public class FallaTecnica extends Incidente{
    @ManyToOne
    @JoinColumn(name = "colaborador_informante", referencedColumnName = "id_colaborador")
    private Colaborador colaboradorInformante;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "link_foto")
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

    public FallaTecnica() {}

    @Override
    public String obtenerInformacion() {
        return "se produjo una Falla Tecnica: \n" + "Descripcion: " + descripcion + "\n" + "Link Foto: " + linkFoto;
    }

    public boolean sucedioEntre(LocalDate fechaInicio, LocalDate fechaFin) {
        return (momentoDelSuceso.isAfter(fechaInicio.atStartOfDay()) && momentoDelSuceso.isBefore(fechaFin.atStartOfDay()))
                || momentoDelSuceso.toLocalDate().equals(fechaInicio) || momentoDelSuceso.toLocalDate().isEqual(fechaFin);
    }

    public String getDescripcion() { return descripcion; }
    public String getLinkFoto() { return linkFoto; }
    public Colaborador getColaboradorInformante() { return colaboradorInformante; }


    public void setColaboradorInformante(Colaborador colaboradorInformante) {
        this.colaboradorInformante = colaboradorInformante;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setLinkFoto(String linkFoto) {
        this.linkFoto = linkFoto;
    }
}
