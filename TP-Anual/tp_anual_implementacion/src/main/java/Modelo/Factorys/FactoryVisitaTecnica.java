package Modelo.Factorys;

import DTOs.VisitaTecnicaDTO;
import Modelo.Dominio.incidentes.VisitaTecnica;

public class FactoryVisitaTecnica {
    public static VisitaTecnica crearVisitaTecnicaAPartirDe(VisitaTecnicaDTO dto) {
        return new VisitaTecnica(dto.getEstadoVisita(), dto.getDetalleDeTrabajo(), dto.getTecnico(), dto.getLinkFoto(), dto.getIncidenteAsociado());
    }
}
