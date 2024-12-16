package Modelo.Mappers;

import DTOs.AlertaDTO;
import DTOs.HeladeraDTO;
import DTOs.VisitaTecnicaDTO;
import Modelo.Dominio.incidentes.Alerta;

import java.util.List;
import java.util.stream.Collectors;

public class AlertaMapper {
    public static AlertaDTO convertirEnAlertaDTO(Alerta alerta) {
        HeladeraDTO heladeraDTO = HeladeraMapper.convertirEnHeladeraDTO(alerta.getHeladeraDondeOcurrio());
        List<VisitaTecnicaDTO> visitaTecnicaDTOList = alerta.getVisitas().stream().map(visita -> VisitaTecnicaMapper.convertirEnVisitaTecnicaDTO(visita)).collect(Collectors.toList());;
        return new AlertaDTO(alerta.getMomentoDelSuceso(),
                heladeraDTO,
                visitaTecnicaDTOList,
                alerta.getEstado(),
                alerta.getTipoAlerta()
                );
    }
}
