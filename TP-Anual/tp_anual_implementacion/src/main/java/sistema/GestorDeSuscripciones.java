package sistema;

import colaborador.Colaborador;
import heladera.Heladera;
import suscripcion.Suscripcion;
import suscripcion.TipoSuscripcion;

import java.util.List;

public class GestorDeSuscripciones {
    private static GestorDeSuscripciones instancia = null;
    List<Suscripcion> suscripciones;

    public static GestorDeSuscripciones getInstancia() {
        if (instancia == null) {
            instancia = new GestorDeSuscripciones();
        }
        return instancia;
    }

    public void suscribirA(Colaborador suscriptor, Integer n, TipoSuscripcion tipoSuscripcion, Heladera heladera) {
        Suscripcion suscripcion = new Suscripcion(suscriptor, tipoSuscripcion, n, heladera);
        suscripciones.add(suscripcion);
    }

    public void serNotificadoAnte(Integer n, Heladera heladera) {
        for (Suscripcion suscripcion : suscripciones) {
            if(suscripcion.deboSerNotificadaAnteNViandas(n, heladera) || suscripcion.deboSerNotificadoAnteDesperfecto(heladera)) {
                suscripcion.notificar(n);
            }
        }
    }
}
