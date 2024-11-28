package Modelo.Mappers;

import DTOs.DistribucionDeViandaDTO;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.DistribucionDeViandas;
import Modelo.Dominio.heladera.Heladera;

import java.time.LocalDate;

public class DistribucionDeViandasMapper {

    public static DistribucionDeViandas crearDistribucionAPartirDe(DistribucionDeViandaDTO dto, Heladera origen, Heladera destino, Colaborador colaborador){
        DistribucionDeViandas nuevaDistribucion = new DistribucionDeViandas(colaborador, origen, destino,
                                                                          dto.getMotivoDeDistribucion(),
                                                                          dto.getCantidadDeViandas(),
                                                                          LocalDate.now());
        return nuevaDistribucion;
    }
}
