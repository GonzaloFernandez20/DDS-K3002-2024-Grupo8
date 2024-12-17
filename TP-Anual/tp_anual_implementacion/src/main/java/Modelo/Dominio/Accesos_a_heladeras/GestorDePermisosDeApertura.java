package Modelo.Dominio.Accesos_a_heladeras;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.ContribucionConApertura;
import Modelo.Dominio.contribucion.DistribucionDeViandas;
import Modelo.Dominio.contribucion.DonacionDeViandas;
import Modelo.Dominio.heladera.Heladera;
import Repositories.Accesos_a_heladeras.AccesoDeColaboradorRepository;
import Repositories.Accesos_a_heladeras.AperturaConPermisoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class GestorDePermisosDeApertura {

    //Dependencias --------------------------------------------------------------------------------------------------
    private final AccesoDeColaboradorRepository accesoDeColaboradorRepository;
    private final AperturaConPermisoRepository aperturaConPermisoRepository;

    @Autowired
    public GestorDePermisosDeApertura(AccesoDeColaboradorRepository accesoDeColaboradorRepository,
                                      AperturaConPermisoRepository aperturaConPermisoRepository) {
        this.accesoDeColaboradorRepository = accesoDeColaboradorRepository;

        this.aperturaConPermisoRepository = aperturaConPermisoRepository;
    }

    //Metodos -------------------------------------------------------------------------------------------------------
    public void generarPermisoDeDonacion(DonacionDeViandas nuevaDonacion) {
        registrarMovimientoSolicitado(nuevaDonacion.getColaborador(),
                MotivoApertura.INGRESAR_VIANDAS_DONADAS,
                nuevaDonacion, nuevaDonacion.getHeladeraDestino());

        accesoDeColaboradorRepository.save(nuevaDonacion.getColaborador().getTarjeta());
        log.info("La tarjeta: {} tiene un permiso de apertura para ingresar viandas donadas en la {}",
                nuevaDonacion.getColaborador().getTarjeta().getCodigoTarjeta(),
                nuevaDonacion.getHeladeraDestino().getNombreDelPunto());
    }

    public void generarPermisosDeDistribucion(DistribucionDeViandas nuevaDistribucion) {
        nuevaDistribucion.validarSiEsRealizable();
        registrarMovimientoSolicitado(nuevaDistribucion.getColaborador(),
                MotivoApertura.TRASLADAR_VIANDAS,
                nuevaDistribucion,
                nuevaDistribucion.getHeladeraDeOrigen());
        log.info("La tarjeta: {} tiene un permiso de apertura para retirar viandas para distribuir en la {}",
                nuevaDistribucion.getColaborador().getTarjeta().getCodigoTarjeta(),
                nuevaDistribucion.getHeladeraDeOrigen().getNombreDelPunto());

        registrarMovimientoSolicitado(nuevaDistribucion.getColaborador(),
                MotivoApertura.INGRESAR_VIANDAS_TRASLADADAS,
                nuevaDistribucion,
                nuevaDistribucion.getHeladeraDestino());
        log.info("La tarjeta: {} tiene un permiso de apertura para ingresar viandas distribuidas en la {}",
                nuevaDistribucion.getColaborador().getTarjeta().getCodigoTarjeta(),
                nuevaDistribucion.getHeladeraDestino().getNombreDelPunto());

        accesoDeColaboradorRepository.save(nuevaDistribucion.getColaborador().getTarjeta());
    }

    private void registrarMovimientoSolicitado(Colaborador colaborador,
                                                     MotivoApertura motivo,
                                                     ContribucionConApertura contribucionAsociada,
                                                     Heladera heladeraAabrir) {

        AperturaConPermiso nuevoPermiso = new AperturaConPermiso(heladeraAabrir, motivo, contribucionAsociada);
        nuevoPermiso.setContribucionAsociada(contribucionAsociada);
        nuevoPermiso.setHoraEnQueVence(LocalDateTime.now().plusHours(3));
        AperturaConPermiso aperturaGuardada = aperturaConPermisoRepository.save(nuevoPermiso);
        colaborador.getTarjeta().addPermisoDeApertura(aperturaGuardada);
    }
}
