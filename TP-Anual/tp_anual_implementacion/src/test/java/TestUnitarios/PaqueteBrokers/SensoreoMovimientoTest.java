package TestUnitarios.PaqueteBrokers;

import Modelo.Brokers.BrokerMovimiento;
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
import Modelo.Dominio.sensoreos.IntentoDeRobo;
import Modelo.Dominio.sensoreos.SensorFisico;
import Modelo.Dominio.sensoreos.SensoreoAvisoRobo;
import Modelo.Dominio.sensoreos.SensoreoTemperatura;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class SensoreoMovimientoTest {

    @Test
    public void testActualizar() {
        BrokerMovimiento brokerMovimiento = new BrokerMovimiento(1234);
        brokerMovimiento.iniciarServidor();
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
        SensoreoAvisoRobo sensoreo = new SensoreoAvisoRobo();
        sensoreo.heladera = heladera;

        IntentoDeRobo intentoDeRobo = new IntentoDeRobo(1);
        // En lugar de enviar datos, probamos la integración
        intentoDeRobo.enviarDatos("localhost", 1234);

        // Notifica al colaborador a cargo de la heladera
    }
}
