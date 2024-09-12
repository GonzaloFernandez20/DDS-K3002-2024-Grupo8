package tp_anual.tp_anual_implementacion;

import colaborador.Colaborador;
import heladera.EstadoHeladera;
import heladera.Heladera;
import heladera.Modelo;
import heladera.SensoreoDeTemperatura;
import contribucion.Vianda;
import localizacion.Direccion;
import localizacion.PuntoEnElMapa;
import localizacion.Ubicacion;
import medios_de_contacto.MedioDeContacto;
import persona.Persona;
import persona.PersonaHumana;
import persona.PersonaJuridica;
import suscripcion.NotificadorDeSuscriptos;

import java.util.ArrayList;
import java.util.List;

import static persona.TipoOrganizacion.ong;

public class BrokerSensorTemperatura {
    public static void main(String[] args) {
        Direccion direccion = new Direccion("Rivadavia", "100", "1111", "Local");
        PuntoEnElMapa puntoEnElMapa = new PuntoEnElMapa(1.22, 3.45);
        Ubicacion ubicacion = new Ubicacion(puntoEnElMapa, direccion, "Buenos Aires", "Ubicacion 1");
        Persona personaJuridica = new PersonaJuridica("SRL", ong, "Muebles", direccion);
        MedioDeContacto medioDeContacto = new MedioDeContacto() {
            @Override
            public void notificar(String mensaje) {
                System.out.println("Soy un medio de contacto");
            }
        }
        Persona personaHumana = new PersonaHumana("Carla", "Perez", "12 12 2000", 12345678, direccion);
        Colaborador colaborador = new Colaborador(personaHumana, List<medioDeContacto>);
        // Inicializamos la heladera con un modelo y capacidad
        List<Vianda> stock1 = new ArrayList<Vianda>();
        Modelo modeloEstandar = new Modelo(10F, 2F);
        Heladera heladera = new Heladera(colaborador, ubicacion, 3, modeloEstandar, EstadoHeladera.ACTIVA, not);
        NotificadorDeSuscriptos notificadorDeSuscriptos = new NotificadorDeSuscriptos(heladera);
        // Crea una instancia de SensoreoDeTemperatura pasando la heladera
        SensoreoDeTemperatura sensoreoDeTemperatura = new SensoreoDeTemperatura(heladera);
        // Inicia el broker
        sensoreoDeTemperatura.iniciar();
    }
}
