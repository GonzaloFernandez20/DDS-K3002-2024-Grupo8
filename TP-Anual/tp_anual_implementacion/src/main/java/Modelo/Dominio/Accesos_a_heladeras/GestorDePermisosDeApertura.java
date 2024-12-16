package Modelo.Dominio.Accesos_a_heladeras;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.ContribucionConApertura;
import Modelo.Dominio.contribucion.DistribucionDeViandas;
import Modelo.Dominio.contribucion.DonacionDeViandas;
import Modelo.Dominio.heladera.Heladera;
import Repositories.Accesos_a_heladeras.AccesoDeColaboradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestorDePermisosDeApertura {

    //Dependencias --------------------------------------------------------------------------------------------------
    private final AccesoDeColaboradorRepository accesoDeColaboradorRepository;

    @Autowired
    public GestorDePermisosDeApertura(AccesoDeColaboradorRepository accesoDeColaboradorRepository) {
        this.accesoDeColaboradorRepository = accesoDeColaboradorRepository;
    }

    //Metodos -------------------------------------------------------------------------------------------------------
    public void generarPermisoDeDonacion(DonacionDeViandas nuevaDonacion) {
        registrarMovimientoSolicitado(nuevaDonacion.getColaborador(),
                MotivoApertura.INGRESAR_VIANDAS_DONADAS,
                nuevaDonacion, nuevaDonacion.getHeladeraDestino());

        accesoDeColaboradorRepository.save(nuevaDonacion.getColaborador().getTarjeta());
    }

    public void generarPermisosDeDistribucion(DistribucionDeViandas nuevaDistribucion) {
        nuevaDistribucion.validarSiEsRealizable();
        registrarMovimientoSolicitado(nuevaDistribucion.getColaborador(),
                MotivoApertura.TRASLADAR_VIANDAS,
                nuevaDistribucion,
                nuevaDistribucion.getHeladeraDeOrigen());

        registrarMovimientoSolicitado(nuevaDistribucion.getColaborador(),
                MotivoApertura.INGRESAR_VIANDAS_TRASLADADAS,
                nuevaDistribucion,
                nuevaDistribucion.getHeladeraDestino());

        accesoDeColaboradorRepository.save(nuevaDistribucion.getColaborador().getTarjeta());
    }

    private void registrarMovimientoSolicitado(Colaborador colaborador,
                                                     MotivoApertura motivo,
                                                     ContribucionConApertura contribucionAsociada,
                                                     Heladera heladeraAabrir) {
        AperturaConPermiso nuevoPermiso = new AperturaConPermiso(heladeraAabrir, motivo, contribucionAsociada);
        colaborador.getTarjeta().addPermisoDeApertura(nuevoPermiso);
    }
}
