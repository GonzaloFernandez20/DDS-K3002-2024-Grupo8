package tp_anual.tp_anual_implementacion;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.heladera.Modelo;
import Modelo.Dominio.heladera.SensoreoDeTemperatura;
import Modelo.Dominio.contribucion.Vianda;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.persona.Persona;
import Modelo.Dominio.persona.PersonaJuridica;

import java.util.ArrayList;
import java.util.List;

import static Modelo.Dominio.persona.TipoOrganizacion.ong;

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
