package Modelo.Mappers;

import DTOs.VisitaTecnicaDTO;
import Modelo.Dominio.incidentes.VisitaTecnica;

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
