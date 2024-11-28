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
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.localizacion.PuntoEnElMapa;
import Modelo.Dominio.localizacion.Ubicacion;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"Controladores"})
public class Server {
    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(DireccionRepository direccionRepository,
                                        PuntoEnElMapaRepository puntoEnElMapaRepository,
                                        UbicacionRepository ubicacionRepository
    ){
        return args -> {
//Insert de clase con otras clases que sean sus atributos
            PuntoEnElMapa puntoEnElMapa = new PuntoEnElMapa(111,222);
            Direccion direccion = new Direccion("Medrano","1234","5678");
            Ubicacion ubicacion = new Ubicacion(direccion, "CABA","XFCE");
            ubicacion.setPunto(puntoEnElMapa);
            direccionRepository.save(direccion);
            puntoEnElMapaRepository.save(puntoEnElMapa);
            ubicacionRepository.save(ubicacion);
//Ir a buscar las clases a la BD y imprimirlas
            /*
            List<Ubicacion> ubicaciones = ubicacionRepository.findAll();
            // Create ObjectMapper for JSON processing
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                // Convert object to JSON string and save to file
                objectMapper.writeValue(new File("modeloDemoOutput.json"), ubicaciones.getFirst());
                System.out.println("JSON saved to file modeloDemoOutput.json");
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error writing JSON to file");
            }*/
        };
    }
}