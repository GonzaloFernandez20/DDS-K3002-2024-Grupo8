package Modelo.Dominio.heladera;

import Modelo.Dominio.incidentes.GestorDeIncidentes;
import Modelo.Dominio.incidentes.TipoAlerta;

public class SensoreoDeMovimiento {
    Heladera heladera;

    public SensoreoDeMovimiento(Heladera heladera) {
        this.heladera = heladera;
    }

    void enviarAlerta() {
        GestorDeIncidentes.reportarAlerta(heladera, TipoAlerta.FRAUDE);
    }
}
