package Modelo.Mappers;

import DTOs.FallaTecnicaDTO;
import Modelo.Dominio.incidentes.FallaTecnica;
import Utils.DescargaDeArchivo;

public class FactoryFallaTecnica {
    public static FallaTecnica CrearFallaTecnicaAPartirDe(FallaTecnicaDTO dto) {
        // Guardar la foto
        DescargaDeArchivo.guardarArchivo("img/fotosHeladerasReportadas/", dto.getFoto());
        FallaTecnica nuevaFallaTecnica = new FallaTecnica(
          dto.getColaboradorInformante(),
          dto.getDescripcion(),
          dto.getHeladera(),
          "img/fotosHeladerasReportadas/" + dto.getFoto()
        );
        return nuevaFallaTecnica;
    }

}
