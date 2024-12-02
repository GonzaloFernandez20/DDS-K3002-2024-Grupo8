package Config;

import Modelo.Dominio.Repositories.heladera.HeladeraRepository;
import Modelo.Dominio.reportes.GestorDeReportes;
import Modelo.Dominio.reportes.ReporteDeFallas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig{
    @Bean
    public GestorDeReportes gestorDeReportes(){
        return new GestorDeReportes();
    }
    @Bean
    public ReporteDeFallas reporteDeFallas(){
        return new ReporteDeFallas();
    }
}
