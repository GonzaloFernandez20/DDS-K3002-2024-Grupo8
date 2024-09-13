package Modelo.Dominio.sensoreos;

import Modelo.Dominio.heladera.Heladera;

public class SensoreoTemperatura {
    private Integer id;
    private Heladera heladera;

    public Integer getId() {
        return id;
    }

    public void actualizar(double temperatura) {
        heladera.actualizar(temperatura);
    }
}
