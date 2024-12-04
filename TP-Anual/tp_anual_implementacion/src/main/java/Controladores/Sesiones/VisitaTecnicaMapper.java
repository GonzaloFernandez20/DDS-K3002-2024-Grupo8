package Controladores.Sesiones;

import DTOs.AlertaDTO;
import DTOs.HeladeraDTO;
import DTOs.VisitaTecnicaDTO;
import Modelo.Dominio.incidentes.Alerta;
import Modelo.Dominio.incidentes.VisitaTecnica;
import Modelo.Mappers.HeladeraMapper;

import java.util.List;

public class VisitaTecnicaMapper {
    public static VisitaTecnicaDTO convertirEnVisitaTecnicaDTO(VisitaTecnica visitaTecnica) {
        return new VisitaTecnicaDTO(
                visitaTecnica.getEstadoVisita(),
                visitaTecnica.getDetalleDeTrabajo(),
                visitaTecnica.getTecnico(),
                visitaTecnica.getLinkFoto(),
                visitaTecnica.getIncidenteAtendido()
        );
    }
}
