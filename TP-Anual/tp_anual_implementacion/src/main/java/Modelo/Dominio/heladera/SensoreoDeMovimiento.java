package Modelo.Dominio.heladera;

import Modelo.Dominio.incidentes.GestorDeIncidentes;
import Modelo.Dominio.incidentes.TipoAlerta;

public class SensoreoDeMovimiento {
    Heladera heladera;

    public Integer getId() {
        return ID;
    }

    Integer ID;

    public SensoreoDeMovimiento(Heladera heladera) {
        this.heladera = heladera;
    }

    public void enviarAlerta() {
        GestorDeIncidentes.reportarAlerta(heladera, TipoAlerta.FRAUDE);
    }
}
