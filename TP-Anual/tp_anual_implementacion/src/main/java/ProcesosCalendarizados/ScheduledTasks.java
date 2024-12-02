package ProcesosCalendarizados;

import Modelo.Dominio.Repositories.heladera.HeladeraRepository;
import Modelo.Dominio.reportes.GestorDeReportes;
import Modelo.Dominio.reportes.ReporteDeFallas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ScheduledTasks {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    HeladeraRepository heladeraRepository;

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    @Scheduled(fixedRate = 10000)
    public void genererReportesSemanales(){
        logger.info("Fixed Rate Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));

        ReporteDeFallas reporteDeFallas = new ReporteDeFallas();
        reporteDeFallas.setFechaDeCreacion(LocalDate.now());
        //Con una inyección automática de spring la línea de abajo no tendría que estar
        reporteDeFallas.setHeladeraRepository(heladeraRepository);
        //Método de prueba
        reporteDeFallas.metodoDeMELI();
    }
    @Bean
    GestorDeReportes generarGestorDeReportes(){
        return GestorDeReportes.getInstancia();
    }
}
