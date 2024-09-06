package Modelo.Dominio.GestionDeContribuciones;

import DTOs.DonacionDeViandaDTO;
import Modelo.Dominio.Accesos_a_heladeras.GestorDePermisosDeApertura;
import Modelo.Dominio.Accesos_a_heladeras.MotivoApertura;
import Modelo.Dominio.contribucion.DonacionDeVianda;
import Modelo.Factorys.BuilderDonacionDeViandas;

public class GestorDonacionDeViandas {
    // TODO: Todo lo relacionado a la instanciacion de una donacion de vianda...
    public static void crearContribucion(DonacionDeViandaDTO dto){
        DonacionDeVianda nuevaDonacion = BuilderDonacionDeViandas.crearDonacionAPartirDe(dto);
        GestorDePermisosDeApertura.registrarMovimientoSolicitado(nuevaDonacion.getColaborador(),
                                                                 MotivoApertura.INGRESAR_VIANDAS_DONADAS,
                                                                 nuevaDonacion, nuevaDonacion.getHeladeraDestino());
    }
    // NEXT: MENSAJE RELACIONADO CON LA PERSISTENCIA
}
