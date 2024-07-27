package suscripcion;

import colaborador.Colaborador;
import contribucion.Contribucion;
import heladera.Heladera;
import java.util.List;

public class Suscripcion {
    public Colaborador suscriptor;
    public TipoSuscripcion tipoSuscripcion;
    public Heladera heladera;

    public Suscripcion(Colaborador suscriptor, TipoSuscripcion tipoSuscripcion, Integer n, Heladera heladera) {
        this.suscriptor = suscriptor;
        this.tipoSuscripcion = tipoSuscripcion;
        this.heladera = heladera;
    }

    public Boolean deboSerNotificadoAnteDesperfecto(Heladera heladera) {
        return tipoSuscripcion.deboSerNotificadoAnteDesperfecto(heladera);
    }

    public Boolean deboSerNotificadaAnteNViandas(Integer cantidadViandas, Heladera heladeraQueNotifico) {
        if(heladera == heladeraQueNotifico) {
            return tipoSuscripcion.deboSerNotificadaAnteNViandas(cantidadViandas);
        }else {
            return false;
        }
    }

    public void notificar(Integer cantidadViandas){
        tipoSuscripcion.notificar(suscriptor, cantidadViandas, heladera);
    }

    public Contribucion crearContribucionDe(Colaborador colaborador, Heladera heladeraQueNotifico, Integer viandasADistribuir){
        return tipoSuscripcion.crearContribucionDe(colaborador, heladeraQueNotifico, viandasADistribuir);
    }
}
