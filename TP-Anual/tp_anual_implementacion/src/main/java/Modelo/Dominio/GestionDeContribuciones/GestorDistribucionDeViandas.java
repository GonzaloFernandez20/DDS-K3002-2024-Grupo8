package Modelo.Dominio.GestionDeContribuciones;

import DTOs.DistribucionDeViandaDTO;
import Modelo.Dominio.Accesos_a_heladeras.GestorDePermisosDeApertura;
import Modelo.Dominio.Accesos_a_heladeras.MotivoApertura;
import Modelo.Dominio.contribucion.DistribucionDeVianda;
import Modelo.Factorys.FactoryDistribucionDeViandas;

public class GestorDistribucionDeViandas {
    public static void crearContribucion(DistribucionDeViandaDTO dto){
        DistribucionDeVianda nuevaDistribucion = FactoryDistribucionDeViandas.crearDistribucionAPartirDe(dto);

        GestorDePermisosDeApertura.registrarMovimientoSolicitado(nuevaDistribucion.getColaborador(),
                MotivoApertura.TRASLADAR_VIANDAS,
                nuevaDistribucion,
                nuevaDistribucion.getHeladeraOrigen());

        GestorDePermisosDeApertura.registrarMovimientoSolicitado(nuevaDistribucion.getColaborador(),
                MotivoApertura.INGRESAR_VIANDAS_TRASLADADAS,
                nuevaDistribucion,
                nuevaDistribucion.getHeladeraDestino());
    }

    // NEXT: MENSAJE RELACIONADO CON LA PERSISTENCIA
}
