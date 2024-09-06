package Modelo.Factorys;

import DTOs.DistribucionDeViandaDTO;
import Modelo.Dominio.contribucion.DistribucionDeVianda;

public class FactoryDistribucionDeViandas {

    public static DistribucionDeVianda crearDistribucionAPartirDe(DistribucionDeViandaDTO distribucionDeViandaDTO){
        DistribucionDeVianda nuevaDistribucion = new DistribucionDeVianda(distribucionDeViandaDTO.getColaborador(),
                                                                          distribucionDeViandaDTO.getHeladeraDeOrigen(),
                                                                          distribucionDeViandaDTO.getHeladeraDestino(),
                                                                          distribucionDeViandaDTO.getMotivoDeDistribucion(),
                                                                          distribucionDeViandaDTO.getCantidadDeViandas() );
        return nuevaDistribucion;
    }
}
