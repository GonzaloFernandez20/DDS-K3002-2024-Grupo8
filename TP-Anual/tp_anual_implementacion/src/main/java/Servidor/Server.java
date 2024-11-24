package Servidor;

import Modelo.Dominio.Repositories.DireccionRepository;
import Modelo.Dominio.Repositories.PuntoEnElMapaRepository;
import Modelo.Dominio.Repositories.UbicacionRepository;
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
}