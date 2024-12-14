package DTOs;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import org.springframework.web.multipart.MultipartFile;

public class FallaTecnicaDTO {
    private Colaborador colaboradorInformante;
    private String descripcion;
    private Heladera heladera;
    private MultipartFile foto;

    public FallaTecnicaDTO(Colaborador colaboradorInformante, Heladera heladera, String descripcion, MultipartFile foto) {
        this.colaboradorInformante = colaboradorInformante;
        this.heladera = heladera;
        this.descripcion = descripcion;
        this.foto = foto;
    }


    public Colaborador getColaboradorInformante() { return colaboradorInformante; }
    public void setColaboradorInformante(Colaborador colaboradorInformante) { this.colaboradorInformante = colaboradorInformante; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Heladera getHeladera() { return heladera; }
    public void setHeladera(Heladera heladera) { this.heladera = heladera; }
    public MultipartFile getFoto() { return foto; }
    public void setFoto(MultipartFile foto) { this.foto = foto; }
}
