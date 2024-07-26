package suscripcion;

import colaborador.Colaborador;
import contribucion.Contribucion;
import heladera.Heladera;

public abstract class TipoSuscripcion {
    public abstract Boolean deboSerNotificadaAnteNViandas(Integer cantidadViandas);
    public abstract void notificar(Colaborador suscriptor, Integer cantidadViandas, Heladera heladera);
    public abstract Boolean deboSerNotificadoAnteDesperfecto(Heladera heladera);

    public abstract Contribucion crearContribucionDe(Colaborador colaborador, Heladera heladeraQueNotifico, Integer viandasADistribuir);
}
