package DTOs;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;

public class FallaTecnicaDTO {
    private Colaborador colaboradorInformante;
    private String descripcion;
    private Heladera heladera;
    private String linkFoto;

    public FallaTecnicaDTO(Colaborador colaboradorInformante, Heladera heladera, String descripcion, String linkFoto) {
        this.colaboradorInformante = colaboradorInformante;
        this.heladera = heladera;
        this.descripcion = descripcion;
        this.linkFoto = linkFoto;
    }


    public Colaborador getColaboradorInformante() { return colaboradorInformante; }
    public void setColaboradorInformante(Colaborador colaboradorInformante) { this.colaboradorInformante = colaboradorInformante; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Heladera getHeladera() { return heladera; }
    public void setHeladera(Heladera heladera) { this.heladera = heladera; }
    public String getLinkFoto() { return linkFoto; }
    public void setLinkFoto(String linkFoto) { this.linkFoto = linkFoto; }
}
