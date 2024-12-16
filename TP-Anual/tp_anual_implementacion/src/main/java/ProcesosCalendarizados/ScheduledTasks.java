package ProcesosCalendarizados;

import Modelo.Dominio.reportes.GestorDeReportes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ScheduledTasks {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    private final GestorDeReportes gestorDeReportes;

    @Autowired
    public ScheduledTasks(GestorDeReportes gestorDeReportes) {
        this.gestorDeReportes = gestorDeReportes;
    }

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    @Scheduled(fixedRate = 100000)
    public void genererReportesSemanales(){
        logger.info("Fixed Rate Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));

        gestorDeReportes.generarReportesSemanales();
    }
}
