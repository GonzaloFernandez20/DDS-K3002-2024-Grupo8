package tp_anual.tp_anual_implementacion;

import colaborador.Colaborador;
import heladera.Heladera;
import heladera.Modelo;
import heladera.SensoreoDeTemperatura;
import contribucion.Vianda;
import localizacion.Direccion;
import persona.Persona;
import persona.PersonaJuridica;

import java.util.ArrayList;
import java.util.List;

import static persona.TipoOrganizacion.ong;

public class BrokerSensorTemperatura {
    public static void main(String[] args) {
        Direccion direccion = new Direccion("Rivadavia", "100", "1111", "Local");
        Persona persona = new PersonaJuridica("SRL", ong, "Muebles", direccion);
        Colaborador colaborador = new Colaborador(persona);
        // Inicializamos la heladera con un modelo y capacidad
        List<Vianda> stock1 = new ArrayList<Vianda>();
        Modelo modeloEstandar = new Modelo(10F, 2F);
        Heladera heladera = new Heladera(colaborador, modeloEstandar, stock1, null,3,null);
        // Crea una instancia de SensoreoDeTemperatura pasando la heladera
        SensoreoDeTemperatura sensoreoDeTemperatura = new SensoreoDeTemperatura(heladera);
        // Inicia el broker
        sensoreoDeTemperatura.iniciar();
    }
}
