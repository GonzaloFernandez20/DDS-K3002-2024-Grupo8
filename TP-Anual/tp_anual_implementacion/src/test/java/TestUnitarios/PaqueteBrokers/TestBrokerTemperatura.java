package TestUnitarios.PaqueteBrokers;

import Modelo.Brokers.BrokerTemperatura;
import Modelo.Dominio.sensoreos.SensorFisico;

public class TestBrokerTemperatura {
    BrokerTemperatura brokerTemperatura = new BrokerTemperatura(5000);
    SensorFisico sensor1 = new SensorFisico(1);
    SensorFisico sensor2 = new SensorFisico(2);

    public BrokerTemperatura IniciarBroker() {
        brokerTemperatura.iniciarServidor();
        sensor1.enviarDatos("localhost", 5000);
        sensor2.enviarDatos("localhost", 5000);
    }
}
