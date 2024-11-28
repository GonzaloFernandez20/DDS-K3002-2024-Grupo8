package Modelo;


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
import Modelo.Dominio.contribucion.Vianda;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.heladera.EstadoHeladera;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.heladera.Modelo;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.localizacion.PuntoEnElMapa;
import Modelo.Dominio.localizacion.Ubicacion;
import Modelo.Dominio.medios_de_contacto.Mail;
import Modelo.Dominio.medios_de_contacto.MedioDeContacto;
import Modelo.Dominio.persona.PersonaJuridica;
import Modelo.Dominio.persona.TipoOrganizacion;
import Repositorios.RepositorioHeladeras;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class AppModelo {
    public static void main(String[] args) {
        SpringApplication.run(AppModelo.class, args);
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
            heladera.setCapacidadDeViandas(3333);
            heladera.setPuestaEnFuncionamiento(LocalDate.now());
            heladeraRepository.save(heladera);

            Colaborador colaboradorJuridico = new Colaborador();
//            colaboradorRepository.save(colaboradorJuridico);

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

//Ir a buscar las clases a la BD e imprimirlas
/*
            List<Ubicacion> ubicaciones = ubicacionRepository.findAll();
            // Create ObjectMapper for JSON processing
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // Convert object to JSON string and save to file
                objectMapper.writeValue(new File("modeloDemoOutput.json"), ubicaciones);
                System.out.println("JSON saved to file modeloDemoOutput.json");
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error writing JSON to file");
            }
*/
        };
    }
}
class GeneradorDeClases{
    //INSTANCIAR A TODOS
    PuntoEnElMapa puntoEnElMapa(){ return new PuntoEnElMapa(111,222);}

    Direccion direccion(){ return new Direccion("Lugano","1234","5678");}

    Ubicacion ubicacion(){
        Ubicacion ubicacion = new Ubicacion(this.direccion(), "CABA","XFCE");
        ubicacion.setPunto(this.puntoEnElMapa());
        return ubicacion;
    }

    PersonaJuridica personaJuridica(){return new PersonaJuridica(
            "Textiles ecológicos S.A.",
            TipoOrganizacion.EMPRESA,
            "Textil",
            this.direccion());
    }

    MedioDeContacto medio(){return new Mail("textilesecologicos@gmail.com");}

    List<MedioDeContacto> medios(){return List.of(this.medio());}

    HacerseCargoDeHeladera hacerseCargoDeHeladera(Colaborador colaborador){
        HacerseCargoDeHeladera hacerseCargoDeHeladera = new HacerseCargoDeHeladera(this.heladera().getColaboradorACargo(),this.heladera());
        hacerseCargoDeHeladera.getColaborador().registrarContribucion(hacerseCargoDeHeladera);
        return hacerseCargoDeHeladera;
    }

    Colaborador colaboradorJuridico(){
        Colaborador colaborador = new Colaborador(
            this.personaJuridica(),
            this.medios());
        colaborador.setTarjeta(null);
 //       HacerseCargoDeHeladera hacerseCargoDeHeladera = new HacerseCargoDeHeladera(null,null);
 //       colaborador.registrarContribucion(hacerseCargoDeHeladera);
        colaborador.registrarContribucion(new DonacionDeDinero(null,1000, Frecuencia.ANUALMENTE, LocalDate.now()));
        return colaborador;
    }

    Modelo modelo(){return new Modelo(5f,1f);}

    Heladera heladera(){Heladera heladera = new Heladera(
            null,//this.colaboradorJuridico(),
            this.ubicacion(),
            3,
            this.modelo(),
            LocalDate.now());
            heladera.setNotificadorDeSuscriptos(null);
            return heladera;
    }
}