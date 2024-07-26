package suscripcion;

import colaborador.Colaborador;
import contribucion.Contribucion;
import contribucion.DistribucionDeVianda;
import contribucion.MotivoDeDistribucion;
import heladera.Heladera;

import java.time.LocalDate;

public class SuscripcionPocasViandasDisponibles extends TipoSuscripcion{
    private Integer cantidadViandasDisponibles;

    public Boolean deboSerNotificadaAnteNViandas(Integer cantidadViandas) {
        return cantidadViandasDisponibles == cantidadViandas;
    }

    public Boolean deboSerNotificadoAnteDesperfecto(Heladera heladera) { return false;}

    public void notificar(Colaborador suscriptor, Integer n, Heladera heladera) {
        suscriptor.serNotificadoPorSuscripcion(heladera, n, this);
    }

    public Contribucion crearContribucionDe(Colaborador colaborador, Heladera heladeraQueNotifico, Integer viandasADistribuir) {
        Heladera heladeraAElegir = colaborador.heladeraAElegir();
        DistribucionDeVianda distribucionDeVianda = new DistribucionDeVianda(colaborador, LocalDate.now(), heladeraAElegir, heladeraQueNotifico, viandasADistribuir, MotivoDeDistribucion.faltaDeViandas);
        return distribucionDeVianda;
    }
}
