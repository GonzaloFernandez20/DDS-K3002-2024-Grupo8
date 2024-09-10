package Repositorios;

import Modelo.Dominio.Accesos_a_heladeras.Apertura;
import Modelo.Dominio.heladera.Heladera;

import java.time.LocalDate;
import java.util.List;

import static Modelo.Dominio.Accesos_a_heladeras.MotivoApertura.INGRESAR_VIANDAS_DONADAS;
import static Modelo.Dominio.Accesos_a_heladeras.MotivoApertura.RETIRAR_VIANDA;


public class RepositorioAperturas {
    private List<Apertura> aperturas = null;
    private static RepositorioAperturas instancia;

    public static RepositorioAperturas getInstancia() {
        if(instancia == null) {
            instancia = new RepositorioAperturas();
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
