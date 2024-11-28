package Modelo.Dominio.Accesos_a_heladeras;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.ContribucionConApertura;
import Modelo.Dominio.contribucion.DistribucionDeViandas;
import Modelo.Dominio.contribucion.DonacionDeViandas;
import Modelo.Dominio.heladera.Heladera;

public class GestorDePermisosDeApertura {

    public static void generarPermisoDeDonación(DonacionDeViandas nuevaDonacion) {
        registrarMovimientoSolicitado(nuevaDonacion.getColaborador(),
                MotivoApertura.INGRESAR_VIANDAS_DONADAS,
                nuevaDonacion, nuevaDonacion.getHeladeraDestino());

        // TODO 3: hacer la tarea 5 del CU - Declarar Donacion De Viandas del trello
        //AccesoDeColaboradorRepository.save(colaborador.getTarjeta());
    }

    public static void generarPermisosDeDistribucion(DistribucionDeViandas nuevaDistribucion) {
        nuevaDistribucion.validarSiEsRealizable();
        registrarMovimientoSolicitado(nuevaDistribucion.getColaborador(),
                MotivoApertura.TRASLADAR_VIANDAS,
                nuevaDistribucion,
                nuevaDistribucion.getHeladeraDeOrigen());

        registrarMovimientoSolicitado(nuevaDistribucion.getColaborador(),
                MotivoApertura.INGRESAR_VIANDAS_TRASLADADAS,
                nuevaDistribucion,
                nuevaDistribucion.getHeladeraDestino());

        // TODO 4: hacer la tarea 5 del CU - Declarar Distribucion De Viandas del trello
        //AccesoDeColaboradorRepository.save(colaborador.getTarjeta());
    }

    public static void registrarMovimientoSolicitado(Colaborador colaborador,
                                                     MotivoApertura motivo,
                                                     ContribucionConApertura contribucionAsociada,
                                                     Heladera heladeraAabrir) {
        AperturaConPermiso nuevoPermiso = new AperturaConPermiso(heladeraAabrir, motivo, contribucionAsociada);
        colaborador.getTarjeta().addPermisoDeApertura(nuevoPermiso);
    }
}
