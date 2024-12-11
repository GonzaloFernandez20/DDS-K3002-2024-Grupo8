package Modelo.Mappers;

import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.heladera.SensoreoDeMovimiento;
import Modelo.Dominio.heladera.SensoreoDeTemperatura;

public class BuilderSensores {

    public static SensoreoDeTemperatura crearSensoreoDeTemperatura(Heladera heladera){
        return new SensoreoDeTemperatura(heladera);
    }

    public static SensoreoDeMovimiento crearSensoreoDeMovimiento(Heladera heladera){
        return new SensoreoDeMovimiento(heladera);
    }
}
