package Modelo.Factorys;

import DTOs.DistribucionDeViandaDTO;
import Modelo.Dominio.contribucion.DistribucionDeVianda;

import java.time.LocalDate;

public class FactoryDistribucionDeViandas {

    public static DistribucionDeVianda crearDistribucionAPartirDe(DistribucionDeViandaDTO distribucionDeViandaDTO){
        DistribucionDeVianda nuevaDistribucion = new DistribucionDeVianda(distribucionDeViandaDTO.getColaborador(),
                                                                          distribucionDeViandaDTO.getHeladeraDeOrigen(),
                                                                          distribucionDeViandaDTO.getHeladeraDestino(),
                                                                          distribucionDeViandaDTO.getMotivoDeDistribucion(),
                                                                          distribucionDeViandaDTO.getCantidadDeViandas(),
                                                                          LocalDate.now());
        return nuevaDistribucion;
    }
}
