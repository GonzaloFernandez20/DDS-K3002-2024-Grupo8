package Servidor;

import Modelo.Dominio.Repositories.colaborador.ColaboradorRepository;
import Modelo.Dominio.Repositories.contribucion.DonacionDeDineroRepository;
import Modelo.Dominio.Repositories.contribucion.HacerseCargoDeHeladeraRepository;
import Modelo.Dominio.Repositories.documentacion.DocumentoRepository;
import Modelo.Dominio.Repositories.heladera.HeladeraRepository;
import Modelo.Dominio.Repositories.heladera.ModeloRepository;
import Modelo.Dominio.Repositories.localizacion.DireccionRepository;
import Modelo.Dominio.Repositories.localizacion.PuntoEnElMapaRepository;
import Modelo.Dominio.Repositories.localizacion.UbicacionRepository;
import Modelo.Dominio.Repositories.medios_de_contacto.MedioDeContactoRepository;
import Modelo.Dominio.Repositories.persona.PersonaJuridicaRepository;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.DonacionDeDinero;
import Modelo.Dominio.contribucion.Frecuencia;
import Modelo.Dominio.contribucion.HacerseCargoDeHeladera;
import Modelo.Dominio.heladera.EstadoHeladera;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.persona.PersonaJuridica;
import Modelo.Dominio.persona.TipoOrganizacion;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDate;

@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
@ComponentScan(basePackages={"Controladores", "Modelo"})
@EntityScan(basePackages={"Modelo"})
@EnableJpaRepositories(basePackages={"Modelo.Dominio.Repositories", "Repositorios"})
public class Server {
    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(DireccionRepository direccionRepository,
                                        PuntoEnElMapaRepository puntoEnElMapaRepository,
                                        UbicacionRepository ubicacionRepository,
                                        PersonaJuridicaRepository personaJuridicaRepository,
                                        DocumentoRepository documentoRepository,
                                        HeladeraRepository heladeraRepository,
                                        ColaboradorRepository colaboradorRepository,
                                        ModeloRepository modeloRepository,
                                        MedioDeContactoRepository medioDeContactoRepository,
                                        HacerseCargoDeHeladeraRepository hacerseCargoDeHeladeraRepository,
                                        DonacionDeDineroRepository donacionDeDineroRepository
    ){
        return args -> {
//Insert de clase con otras clases que sean sus atributos
            Heladera heladera = new Heladera();
            heladera.setEstado(EstadoHeladera.ACTIVA);
            heladera.setCapacidadDeViandas(55555);
            heladera.setPuestaEnFuncionamiento(LocalDate.now());
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

            HacerseCargoDeHeladera hacerseCargoDeHeladera = new HacerseCargoDeHeladera();
            hacerseCargoDeHeladera.setColaborador(colaboradorJuridico);
            hacerseCargoDeHeladera.setHeladeraACargo(heladera);

            colaboradorJuridico.registrarContribucion(donacionDeDinero);
            colaboradorJuridico.registrarContribucion(hacerseCargoDeHeladera);
            colaboradorJuridico.setPersona(personaJuridica);
            colaboradorRepository.save(colaboradorJuridico);

            heladera.setColaboradorACargo(colaboradorJuridico);
            heladeraRepository.save(heladera);
        };
    }
}