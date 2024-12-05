package ProcesosCalendarizados;

import Config.AppConfig;
import Repositories.heladera.HeladeraRepository;
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
    AppConfig config = new AppConfig();

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    @Scheduled(fixedRate = 100000)
    public void genererReportesSemanales(){
        logger.info("Fixed Rate Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));

        config.gestorDeReportes().generarReportesSemanales();
    }
}
