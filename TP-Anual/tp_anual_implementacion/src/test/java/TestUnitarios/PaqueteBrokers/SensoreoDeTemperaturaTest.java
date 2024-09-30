package TestUnitarios.PaqueteBrokers;

import Modelo.Brokers.BrokerTemperatura;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.heladera.Modelo;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.localizacion.Ubicacion;
import Modelo.Dominio.medios_de_contacto.MedioDeContacto;
import Modelo.Dominio.persona.Persona;
import Modelo.Dominio.persona.PersonaJuridica;
import Modelo.Dominio.persona.TipoOrganizacion;
import Modelo.Dominio.sensoreos.SensorFisico;
import Modelo.Dominio.sensoreos.SensoreoTemperatura;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class SensoreoDeTemperaturaTest {

    @Test
    public void testActualizar() {
        BrokerTemperatura brokerTemperatura = new BrokerTemperatura(1234);
        brokerTemperatura.iniciarServidor();
        Direccion direccion = new Direccion("Medrano", "124", "1234");
        MedioDeContacto medioDeContacto = new MedioDeContacto() {
            @Override
            public void notificar(String mensaje) {
                System.out.print("soy un medio de contacto");
            }
        };
        Ubicacion ubicacion = new Ubicacion(direccion, "Buenos Aires", "ALmagro");
        Persona personaJuridica = new PersonaJuridica("ONG", TipoOrganizacion.ong, "Gastronomia", direccion);
        Colaborador colaborador = new Colaborador(personaJuridica, (List<MedioDeContacto>) medioDeContacto);
        Modelo modelo = new Modelo(15, 9);
        Heladera heladera = new Heladera(colaborador, ubicacion, 10, modelo, LocalDate.now());
        System.out.print("Temperatura inicial de la heladera: " + heladera.getTemperatura());

        // Crear instancia de SensoreoTemperatura
        SensoreoTemperatura sensoreo = new SensoreoTemperatura();
        sensoreo.heladera = heladera;

        SensorFisico sensor = new SensorFisico(1);
        // En lugar de enviar datos, probamos la integración
        sensor.enviarDatos("localhost", 1234);

        // Deberia actualizar la temperatura
        System.out.print("Nueva temperatura: "+ sensoreo.heladera.getTemperatura());
    }
}

