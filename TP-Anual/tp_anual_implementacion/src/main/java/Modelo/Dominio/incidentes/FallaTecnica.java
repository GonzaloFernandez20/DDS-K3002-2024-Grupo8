package Modelo.Dominio.incidentes;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class FallaTecnica extends Incidente{
    private Colaborador colaboradorInformante;
    private String descripcion;
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
