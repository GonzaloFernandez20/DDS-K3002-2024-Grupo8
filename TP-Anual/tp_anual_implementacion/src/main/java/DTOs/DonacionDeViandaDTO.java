package DTOs;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;

import java.util.List;

public class DonacionDeViandaDTO {
    Colaborador colaborador;
    Heladera heladera;
    List<ViandaDTO> viandasDTO;

    public DonacionDeViandaDTO(Colaborador colaborador, Heladera heladera, List<ViandaDTO> viandas) {
        this.colaborador = colaborador;
        this.heladera = heladera;
        this.viandasDTO = viandas;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public Heladera getHeladera() {
        return heladera;
    }

    public void setHeladera(Heladera heladera) {
        this.heladera = heladera;
    }

    public List<ViandaDTO> getViandasDTO() {
        return viandasDTO;
    }

    public void setViandasDTO(List<ViandaDTO> viandasDTO) {
        this.viandasDTO = viandasDTO;
    }
}
