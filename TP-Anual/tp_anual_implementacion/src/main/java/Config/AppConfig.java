package Config;

import Modelo.Dominio.reportes.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig{
    @Bean
    public GestorDeReportes gestorDeReportes(){
        return new GestorDeReportes();
    }
    @Bean
    public ReporteDeFallas reporteDeFallas(){return new ReporteDeFallas();}
    @Bean
    public ReporteDeViandasPorColaborador reporteDeViandasPorColaborador(){return new ReporteDeViandasPorColaborador();}
    @Bean
    public ReporteDeViandasPorHeladera reporteDeViandasPorHeladera(){return new ReporteDeViandasPorHeladera();}

}
