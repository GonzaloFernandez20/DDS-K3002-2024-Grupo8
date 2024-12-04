import Modelo.Dominio.Accesos_a_heladeras.AccesoDeColaborador;
import Modelo.Dominio.Persona.PersonaHumana;
import Modelo.Dominio.Persona.PersonaJuridica;
import Modelo.Dominio.Persona.TipoOrganizacion;
import Modelo.Dominio.Repositories.Accesos_a_heladeras.AccesoDeColaboradorRepository;
import Modelo.Dominio.Repositories.colaborador.ColaboradorRepository;
import Modelo.Dominio.Repositories.contribucion.DonacionDeDineroRepository;
import Modelo.Dominio.Repositories.contribucion.DonacionDeViandasRepository;
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
import Modelo.Dominio.contribucion.DonacionDeViandas;
import Modelo.Dominio.contribucion.HacerseCargoDeHeladera;
import Modelo.Dominio.contribucion.Vianda;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.heladera.EstadoHeladera;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.heladera.Modelo;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.localizacion.PuntoEnElMapa;
import Modelo.Dominio.localizacion.Ubicacion;
import Modelo.seguridad.SesionActiva.Usuario;
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
import java.util.List;

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
    @Autowired
    DonacionDeViandasRepository donacionDeViandasRepository;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(){
        return args -> {
//Insert de clase con otras clases que sean sus atributos
            /*
            ¿Qué necesito para ver todo bien en la pantalla?
            - 2 heladeras
            - 2 colaboradores (uno juridico otro humano)
            - 2 viandas
            - 1 donacion
            - 1 usuario humano (hardcodeado en SQL: mariana123 con password soymariana)
        Las heladeras requieren min
        - 1 modelo
        - 2 ubicacion
            - 1 direccion
            - 2 puntoEnElMapa
        - 1 colaborador juridico
            - 1 personaJuridica
                - 1 direccion
        El colaborador humano requiere min
        - 1 personaHumana
            - 1 direccion (2 dirs en total)
            - 1 documento
        Las viandas requiere min
        - 1 colaboradorHumano*
        - 1 heladera*
        - 1 donacion
        La donacion requiere min
        - 1 heladera*
        - 1 colaboradorHumano*

            * */
            /*
            PuntoEnElMapa puntoEnElMapa = new PuntoEnElMapa();
            puntoEnElMapa.setLatitud(1234567);
            puntoEnElMapa.setLongitud(7654321);

            PuntoEnElMapa puntoEnElMapa2 = new PuntoEnElMapa();
            puntoEnElMapa2.setLatitud(1234561);
            puntoEnElMapa2.setLongitud(7654329);

            Direccion direccion = new Direccion();
            direccion.setAltura("9999");
            direccion.setCalle("Medrano");
            direccion.setCodPostal("567");

            Direccion direccion2 = new Direccion();
            direccion2.setAltura("2300");
            direccion2.setCalle("Mozart");
            direccion2.setCodPostal("2020");

            Direccion direccion3 = new Direccion();
            direccion3.setAltura("1010");
            direccion3.setCalle("Mexico");
            direccion3.setCodPostal("1020");

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

            Ubicacion ubicacion2 = new Ubicacion();
            ubicacion2.setPunto(puntoEnElMapa2);
            ubicacion2.setNombreDelPunto("Recibidor de Viandas");
            ubicacion2.setCiudad("CABA");
            ubicacion2.setDireccion(direccion2);

            Heladera heladera2 = new Heladera();
            heladera2.setEstado(EstadoHeladera.ACTIVA);
            heladera2.setCapacidadDeViandas(2000);
            heladera2.setPuestaEnFuncionamiento(LocalDate.now());
            heladera2.setUbicacion(ubicacion2);
            heladeraRepository.save(heladera2);
            heladera2.setModelo(modelo);
            heladeraRepository.save(heladera2);

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

            heladera2.setColaboradorACargo(colaboradorJuridico);
            heladeraRepository.save(heladera2);

            PersonaHumana personaHumana = new PersonaHumana();
            Documento documento = new Documento();
            documento.setNumero("40300200");
            documento.setSexo(FEMENINO);
            documento.setTipo(DNI);

            personaHumana.setApellido("Días");
            personaHumana.setNombre("Mariana");
            personaHumana.setFechaDeNacimiento(LocalDate.now().minusYears(24));
            personaHumana.setDocumento(documento);
            personaHumana.setDireccion(direccion3);

            Colaborador colaboradorHumano = new Colaborador();
            colaboradorHumano.setHistorialDeContribuciones(new ArrayList<>());
            colaboradorHumano.setPersona(personaHumana);
            colaboradorRepository.save(colaboradorHumano);

            Vianda vianda = new Vianda();
            vianda.setEstadoVianda(ENTREGADA);
            vianda.setColaborador(colaboradorHumano);
            vianda.setFechaDeDonacion(LocalDate.now());
            vianda.setFechaDeCaducidad(LocalDate.now().plusDays(5));
            vianda.setTipoDeComida("Pollo con verduras");

            Vianda vianda2 = new Vianda();
            vianda2.setEstadoVianda(ENTREGADA);
            vianda2.setColaborador(colaboradorHumano);
            vianda2.setFechaDeDonacion(LocalDate.now());
            vianda2.setFechaDeCaducidad(LocalDate.now().plusDays(7));
            vianda2.setTipoDeComida("Fideos");

            DonacionDeViandas donacionDeViandas = new DonacionDeViandas();
            donacionDeViandas.setViandas(List.of(vianda, vianda2));
            donacionDeViandas.setHeladeraDestino(heladera);
            donacionDeViandas.setColaborador(colaboradorHumano);
            donacionDeViandas.setFechaDeContribucion(LocalDate.now());

            vianda.setHeladera(heladera);
            vianda2.setHeladera(heladera);

            donacionDeViandasRepository.save(donacionDeViandas);

            heladera.recibirVianda(vianda);
            heladera.recibirVianda(vianda2);
            heladeraRepository.save(heladera);
            */
        };
    }
}