package Servidor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages={"Controladores", "Modelo"})
@EntityScan(basePackages={"Modelo"})
@EnableJpaRepositories(basePackages={"Modelo.Dominio.Repositories", "Repositorios"})
public class Server {
    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }
}