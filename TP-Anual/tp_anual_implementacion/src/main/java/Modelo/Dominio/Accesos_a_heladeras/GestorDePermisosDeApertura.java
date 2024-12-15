package Modelo.Dominio.Accesos_a_heladeras;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.ContribucionConApertura;
import Modelo.Dominio.contribucion.DistribucionDeViandas;
import Modelo.Dominio.contribucion.DonacionDeViandas;
import Modelo.Dominio.heladera.Heladera;
import Repositories.Accesos_a_heladeras.AccesoDeColaboradorRepository;
import Repositories.Accesos_a_heladeras.AperturaConPermisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class GestorDePermisosDeApertura {

    AccesoDeColaboradorRepository accesoDeColaboradorRepository;
    AperturaConPermisoRepository aperturaConPermisoRepository;

    @Autowired
    public GestorDePermisosDeApertura(AccesoDeColaboradorRepository accesoDeColaboradorRepository, AperturaConPermisoRepository aperturaConPermisoRepository) {
        this.accesoDeColaboradorRepository = accesoDeColaboradorRepository;
        this.aperturaConPermisoRepository = aperturaConPermisoRepository;
    }

    public void generarPermisoDeDonación(DonacionDeViandas nuevaDonacion) {
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

    public void registrarMovimientoSolicitado(Colaborador colaborador,
                                                     MotivoApertura motivo,
                                                     ContribucionConApertura contribucionAsociada,
                                                     Heladera heladeraAabrir) {
        AperturaConPermiso nuevoPermiso = new AperturaConPermiso();
        nuevoPermiso.setContribucion(contribucionAsociada);
        nuevoPermiso.setFueConcretadaLaApertura(false);
        nuevoPermiso.setHoraEnQueVence(LocalDateTime.now().plusHours(3));
        nuevoPermiso.setMotivo(motivo);
        nuevoPermiso.setHeladera(heladeraAabrir);
        nuevoPermiso.setCantidadViandasInvolucradas(contribucionAsociada.cantidadDeViandasInvolucradas());

        aperturaConPermisoRepository.save(nuevoPermiso);
        colaborador.getTarjeta().addPermisoDeApertura(nuevoPermiso);
    }
}
