/*import Modelo.Dominio.Accesos_a_heladeras.AccesoDeColaborador;
import Modelo.Dominio.Persona.PersonaHumana;
import Modelo.Dominio.Persona.PersonaJuridica;
import Modelo.Dominio.Persona.TipoOrganizacion;
import Modelo.Dominio.Repositories.Accesos_a_heladeras.AccesoDeColaboradorRepository;
import Modelo.Dominio.Repositories.colaborador.ColaboradorRepository;
import Modelo.Dominio.Repositories.contribucion.DonacionDeDineroRepository;
import Modelo.Dominio.Repositories.contribucion.HacerseCargoDeHeladeraRepository;
import Modelo.Dominio.Repositories.contribucion.ViandaRepository;
import Modelo.Dominio.Repositories.documentacion.DocumentoRepository;
import Modelo.Dominio.Repositories.heladera.HeladeraRepository;
import Modelo.Dominio.Repositories.heladera.ModeloRepository;
import Modelo.Dominio.Repositories.localizacion.DireccionRepository;
import Modelo.Dominio.Repositories.localizacion.PuntoEnElMapaRepository;
import Modelo.Dominio.Repositories.localizacion.UbicacionRepository;
import Modelo.Dominio.Repositories.medios_de_contacto.MedioDeContactoRepository;
import Modelo.Dominio.Repositories.persona.PersonaHumanaRepository;
import Modelo.Dominio.Repositories.persona.PersonaJuridicaRepository;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.DonacionDeDinero;
import Modelo.Dominio.contribucion.HacerseCargoDeHeladera;
import Modelo.Dominio.contribucion.Vianda;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.heladera.EstadoHeladera;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.heladera.Modelo;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.localizacion.PuntoEnElMapa;
import Modelo.Dominio.localizacion.Ubicacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDate;
import java.util.ArrayList;

import static Modelo.Dominio.contribucion.EstadoVianda.ENTREGADA;
import static Modelo.Dominio.documentacion.Sexo.FEMENINO;
import static Modelo.Dominio.documentacion.TipoDeDocumento.DNI;

@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
@ComponentScan(basePackages={"Controladores", "Modelo","ProcesosCalendarizados"})
@EntityScan(basePackages={"Modelo"})
@EnableJpaRepositories(basePackages={"Modelo.Dominio.Repositories", "Repositorios"})
public class App {
    @Autowired
    HeladeraRepository heladeraRepository;
    @Autowired
    DireccionRepository direccionRepository;
    @Autowired
    PuntoEnElMapaRepository puntoEnElMapaRepository;
    @Autowired
    UbicacionRepository ubicacionRepository;
    @Autowired
    PersonaJuridicaRepository personaJuridicaRepository;
    @Autowired
    DocumentoRepository documentoRepository;
    @Autowired
    ColaboradorRepository colaboradorRepository;
    @Autowired
    ModeloRepository modeloRepository;
    @Autowired
    MedioDeContactoRepository medioDeContactoRepository;
    @Autowired
    private ViandaRepository viandaRepository;
    @Autowired
    private AccesoDeColaboradorRepository accesoDeColaboradorRepository;
    @Autowired
    private PersonaHumanaRepository personaHumanaRepository;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(){
        return args -> {
//Insert de clase con otras clases que sean sus atributos
            PuntoEnElMapa puntoEnElMapa = new PuntoEnElMapa();
            puntoEnElMapa.setLatitud(1234567);
            puntoEnElMapa.setLongitud(7654321);

            Direccion direccion = new Direccion();
            direccion.setAltura("9999");
            direccion.setCalle("Medrano");
            direccion.setCodPostal("567");

            Ubicacion ubicacion = new Ubicacion();
            ubicacion.setPunto(puntoEnElMapa);
            ubicacion.setNombreDelPunto("XFCE");
            ubicacion.setCiudad("CABA");
            ubicacion.setDireccion(direccion);

            Modelo modelo = new Modelo();
            modelo.setNombreModelo("XXXXX");
            modelo.setTemperaturaMaxima(100);
            modelo.setTemperaturaMinima(20);

            Heladera heladera = new Heladera();
            heladera.setEstado(EstadoHeladera.ACTIVA);
            heladera.setCapacidadDeViandas(55555);
            heladera.setPuestaEnFuncionamiento(LocalDate.now());
            heladera.setUbicacion(ubicacion);
            heladera.setModelo(modelo);
            heladeraRepository.save(heladera);

            Colaborador colaboradorJuridico = new Colaborador();

            PersonaJuridica personaJuridica = new PersonaJuridica();
            personaJuridica.setRubro("Limpieza");
            personaJuridica.setRazonSocial("1234567890");
            personaJuridica.setTipoDeOrganizacion(TipoOrganizacion.ONG);

            HacerseCargoDeHeladera hacerseCargoDeHeladera = new HacerseCargoDeHeladera();
            hacerseCargoDeHeladera.setColaborador(colaboradorJuridico);
            hacerseCargoDeHeladera.setHeladeraACargo(heladera);

            colaboradorJuridico.registrarContribucion(hacerseCargoDeHeladera);
            colaboradorJuridico.setPersona(personaJuridica);
            colaboradorRepository.save(colaboradorJuridico);

            heladera.setColaboradorACargo(colaboradorJuridico);
            heladeraRepository.save(heladera);

            /*PersonaHumana personaHumana = new PersonaHumana();
            Documento documento = new Documento();
            documento.setNumero("40300200");
            documento.setSexo(FEMENINO);
            documento.setTipo(DNI);
            documentoRepository.save(documento);

            personaHumana.setApellido("Días");
            personaHumana.setNombre("Mariana");
            personaHumana.setFechaDeNacimiento(LocalDate.now().minusYears(24));
            personaHumanaRepository.save(personaHumana);
            personaHumana.setDocumento(documento);
            personaHumanaRepository.save(personaHumana);

            Colaborador colaboradorHumano = new Colaborador();
            colaboradorHumano.setHistorialDeContribuciones(new ArrayList<>());
            colaboradorRepository.save(colaboradorHumano);
            colaboradorHumano.setPersona(personaHumana);
            colaboradorRepository.save(colaboradorHumano);

            Vianda vianda = new Vianda();
            vianda.setEstadoVianda(ENTREGADA);
            vianda.setColaborador(colaboradorHumano);
            vianda.setFechaDeDonacion(LocalDate.now());
            vianda.setFechaDeCaducidad(LocalDate.now().plusDays(5));
            vianda.setTipoDeComida("Pollo con verduras");
            viandaRepository.save(vianda);
        };
    }
}*/