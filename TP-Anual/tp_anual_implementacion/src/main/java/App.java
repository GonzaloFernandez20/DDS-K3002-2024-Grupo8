import Modelo.Dominio.Accesos_a_heladeras.AperturaConPermiso;
import Modelo.Dominio.Accesos_a_heladeras.MotivoApertura;
import Modelo.Dominio.contribucion.*;
import Modelo.Dominio.reportes.ReporteDeFallas;
import Repositories.AperturaConPermisoRepository;
import Repositories.colaborador.ColaboradorRepository;
import Repositories.contribucion.DonacionDeDineroRepository;
import Repositories.contribucion.HacerseCargoDeHeladeraRepository;
import Repositories.documentacion.DocumentoRepository;
import Repositories.heladera.HeladeraRepository;
import Repositories.heladera.ModeloRepository;
import Repositories.incidentes.FallaTecnicaRepository;
import Repositories.localizacion.DireccionRepository;
import Repositories.localizacion.PuntoEnElMapaRepository;
import Repositories.localizacion.UbicacionRepository;
import Repositories.medios_de_contacto.MedioDeContactoRepository;
import Repositories.persona.PersonaJuridicaRepository;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.EstadoHeladera;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.heladera.Modelo;
import Modelo.Dominio.incidentes.FallaTecnica;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.localizacion.PuntoEnElMapa;
import Modelo.Dominio.localizacion.Ubicacion;
import Modelo.Dominio.persona.PersonaJuridica;
import Modelo.Dominio.persona.TipoOrganizacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
@EnableScheduling
@ComponentScan(basePackages={"Controladores", "Modelo","ProcesosCalendarizados","Config", "ServiceImpl", "DAOs"})
@EntityScan(basePackages={"Modelo"})
@EnableJpaRepositories(basePackages={"Repositories", "Repositorios"})
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
    HacerseCargoDeHeladeraRepository hacerseCargoDeHeladeraRepository;
    @Autowired
    DonacionDeDineroRepository donacionDeDineroRepository;
    @Autowired
    FallaTecnicaRepository fallaTecnicaRepository;
    @Autowired
    AperturaConPermisoRepository aperturaConPermisoRepository;
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(
    ){
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

            DonacionDeDinero donacionDeDinero = new DonacionDeDinero();
            donacionDeDinero.setFechaDeContribucion(LocalDate.now());
            donacionDeDinero.setMonto(10000);
            donacionDeDinero.setFrecuencia(Frecuencia.UNICAMENTE);
            donacionDeDinero.setColaborador(colaboradorJuridico);

            Vianda vianda = new Vianda();
            vianda.setEstadoVianda(EstadoVianda.NO_ENTREGADA);
            vianda.setPeso("Un final de DDS");
            vianda.setCalorias("8000");
            vianda.setFechaDeCaducidad(LocalDate.now().plusMonths(2));
            vianda.setFechaDeDonacion(LocalDate.now());
            vianda.setColaborador(colaboradorJuridico);
            List<Vianda> viandas = new ArrayList<Vianda>();
            viandas.add(vianda);


            DonacionDeViandas donacionDeViandas = new DonacionDeViandas();
            donacionDeViandas.setColaborador(colaboradorJuridico);
            donacionDeViandas.setHeladeraDestino(heladera);
            donacionDeViandas.setViandas(viandas);
            donacionDeViandas.procesarLaContribucion();

            HacerseCargoDeHeladera hacerseCargoDeHeladera = new HacerseCargoDeHeladera();
            hacerseCargoDeHeladera.setColaborador(colaboradorJuridico);
            hacerseCargoDeHeladera.setHeladeraACargo(heladera);
            hacerseCargoDeHeladera.procesarLaContribucion();

            colaboradorJuridico.registrarContribucion(donacionDeDinero);
            colaboradorJuridico.registrarContribucion(hacerseCargoDeHeladera);
            colaboradorJuridico.registrarContribucion(donacionDeViandas);
            colaboradorJuridico.setPersona(personaJuridica);
            colaboradorRepository.save(colaboradorJuridico);

            AperturaConPermiso aperturaConPermiso = new AperturaConPermiso();
            aperturaConPermiso.setContribucion(donacionDeViandas);
            aperturaConPermiso.setHeladera(donacionDeViandas.getHeladeraDestino());
            aperturaConPermiso.setMotivo(MotivoApertura.RETIRAR_VIANDA);
            aperturaConPermiso.setFechaApertura(LocalDateTime.now());
            aperturaConPermiso.setCantidadViandasInvolucradas(1);
            aperturaConPermisoRepository.save(aperturaConPermiso);

            AperturaConPermiso aperturaConPermisoIngresar = new AperturaConPermiso();
            aperturaConPermisoIngresar.setContribucion(donacionDeViandas);
            aperturaConPermisoIngresar.setHeladera(donacionDeViandas.getHeladeraDestino());
            aperturaConPermisoIngresar.setMotivo(MotivoApertura.INGRESAR_VIANDAS_DONADAS);
            aperturaConPermisoIngresar.setFechaApertura(LocalDateTime.now());
            aperturaConPermisoIngresar.setCantidadViandasInvolucradas(1);
            aperturaConPermisoRepository.save(aperturaConPermisoIngresar);

            heladera.setColaboradorACargo(colaboradorJuridico);
            heladeraRepository.save(heladera);

            FallaTecnica fallaTecnica = new FallaTecnica();
            fallaTecnica.setMomentoDelSuceso(LocalDateTime.now());
            fallaTecnica.setColaboradorInformante(colaboradorJuridico);
            fallaTecnica.setHeladeraDondeOcurrio(heladera);
            fallaTecnicaRepository.save(fallaTecnica);
        };
    }
}
