package suscripcion;

import colaborador.Colaborador;
import contribucion.Contribucion;
import contribucion.DistribucionDeVianda;
import contribucion.MotivoDeDistribucion;
import heladera.Heladera;
import org.apache.commons.lang3.ObjectUtils;
import sistema.Sistema;

import java.time.LocalDate;
import java.util.List;

import static java.sql.Types.NULL;

public class SuscripcionDesperfectoHeladera extends TipoSuscripcion{
    private Sistema sistema;

    public void setSistema(Sistema sistema) {this.sistema = sistema;}

    public Boolean deboSerNotificadaAnteNViandas(Integer cantidadViandas) {
        return false;
    }

    public Boolean deboSerNotificadoAnteDesperfecto(Heladera heladera) { return true;}

    public void notificar(Colaborador suscriptor, Integer n, Heladera heladera) {
        suscriptor.serNotificadoPorSuscripcion(heladera, n, this);
    }

    public Contribucion crearContribucionDe(Colaborador colaborador, Heladera heladeraQueNotifico, Integer viandasADistribuir) {
        List<Heladera> heladeras = sistema.getInstancia().recomendarHeladeras(colaborador);
        Heladera heladera = colaborador.aceptarUnaHeladeraDe(heladeras);
        if(!heladera.equals(NULL)) {
            DistribucionDeVianda distribucionDeVianda = new DistribucionDeVianda(colaborador, LocalDate.now(), heladeraQueNotifico, heladera, viandasADistribuir, MotivoDeDistribucion.desperfectoHeladera);
            return distribucionDeVianda;
        } else {
            Heladera heladeraAElegir = colaborador.heladeraAElegir();
            DistribucionDeVianda distribucionDeVianda = new DistribucionDeVianda(colaborador, LocalDate.now(), heladeraQueNotifico, heladeraAElegir, viandasADistribuir, MotivoDeDistribucion.desperfectoHeladera);
            return distribucionDeVianda;
        }

    }
}
