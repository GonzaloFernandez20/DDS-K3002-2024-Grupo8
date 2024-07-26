package suscripcion;

import colaborador.Colaborador;
import contribucion.Contribucion;
import contribucion.DistribucionDeVianda;
import contribucion.MotivoDeDistribucion;
import heladera.Heladera;

import java.time.LocalDate;

public class SuscripcionMuchasViandasDisponibles extends TipoSuscripcion{
    private Integer cantidadViandasDisponibles;

    public SuscripcionMuchasViandasDisponibles(Integer cantidadViandasDisponibles) {
        this.cantidadViandasDisponibles = cantidadViandasDisponibles;
    }

    public Boolean deboSerNotificadaAnteNViandas(Integer cantidadViandas) {
        return cantidadViandasDisponibles == cantidadViandas;
    }
    public Boolean deboSerNotificadoAnteDesperfecto(Heladera heladera) { return false;}

    public void notificar(Colaborador suscriptor, Integer n, Heladera heladera) {
        suscriptor.serNotificadoPorSuscripcion(heladera, n, this);
    }

    public Contribucion crearContribucionDe(Colaborador colaborador, Heladera heladeraQueNotifico, Integer viandasADistribuir) {
        Heladera heladeraAElegir = colaborador.heladeraAElegir();
        while((heladeraAElegir.getViandasEnStock()).size() > (heladeraQueNotifico.getViandasEnStock()).size()) {
            System.out.print("La heladera seleccionada tiene mas viandas que la de origen. Seleccione otra!");
            heladeraAElegir = colaborador.heladeraAElegir();
        }
        DistribucionDeVianda distribucionDeVianda = new DistribucionDeVianda(colaborador, LocalDate.now(), heladeraQueNotifico, heladeraAElegir, viandasADistribuir, MotivoDeDistribucion.faltaDeViandas);
        return distribucionDeVianda;
    }
}