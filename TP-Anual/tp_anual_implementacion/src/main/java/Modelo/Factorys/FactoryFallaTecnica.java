package Modelo.Factorys;

import DTOs.FallaTecnicaDTO;
import Modelo.Dominio.incidentes.FallaTecnica;

public class FactoryFallaTecnica {
    public static FallaTecnica CrearFallaTecnicaAPartirDe(FallaTecnicaDTO dto) {
        FallaTecnica nuevaFallaTecnica = new FallaTecnica(
          dto.getColaboradorInformante(),
          dto.getDescripcion(),
          dto.getHeladera(),
          dto.getLinkFoto()
        );
        return nuevaFallaTecnica;
    }
}
