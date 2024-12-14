package Servidor;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
@ComponentScan(basePackages={"Controladores", "Modelo", "ServiceImpl", "Servicios_Externos_APIs" })
@EntityScan(basePackages={"Modelo"})
@EnableJpaRepositories(basePackages={"Repositories"})
public class Server {
    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }
}