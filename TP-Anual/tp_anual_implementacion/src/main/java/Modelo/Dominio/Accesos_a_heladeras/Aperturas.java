package Accesos_a_heladeras;

import heladera.Heladera;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

import static Accesos_a_heladeras.MotivoApertura.INGRESAR_VIANDAS_DONADAS;
import static Accesos_a_heladeras.MotivoApertura.RETIRAR_VIANDA;

public class Aperturas {
    private List<Apertura> aperturas = null;
    private static Aperturas instancia;

    public static Aperturas getInstancia() {
        if(instancia == null) {
            instancia = new Aperturas();
        }
        return instancia;
    }
    public List<Apertura> aperturasEntreFechas(LocalDate fechaInicio, LocalDate fechaFin){
        return aperturas.stream().filter(apertura -> apertura.getFecha().isAfter(fechaInicio.atStartOfDay()) && apertura.getFecha().isBefore(fechaFin.atStartOfDay())).toList();
    }
    public Integer cantidadDeDepositosDeHeladeraEntreFechas(Heladera heladera, LocalDate fechaInicio, LocalDate fechaFin){
        return this.aperturasEntreFechas(fechaInicio,fechaFin).stream().filter(apertura -> apertura.getMotivo().equals(INGRESAR_VIANDAS_DONADAS)).toList().size();
    }
    public Integer cantidadDeRetirosDeHeladeraEntreFechas(Heladera heladera, LocalDate fechaInicio, LocalDate fechaFin){
        return this.aperturasEntreFechas(fechaInicio,fechaFin).stream().filter(apertura -> apertura.getMotivo().equals(RETIRAR_VIANDA)).toList().size();
    }
}
