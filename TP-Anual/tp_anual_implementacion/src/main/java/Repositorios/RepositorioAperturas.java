package Repositorios;

import Modelo.Dominio.Accesos_a_heladeras.PermisoDeApertura;
import Modelo.Dominio.Accesos_a_heladeras.PermisoDeAperturaParaColaborar;
import Modelo.Dominio.heladera.Heladera;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static Modelo.Dominio.Accesos_a_heladeras.MotivoApertura.INGRESAR_VIANDAS_DONADAS;
import static Modelo.Dominio.Accesos_a_heladeras.MotivoApertura.RETIRAR_VIANDA;


public class RepositorioAperturas {
    private List<PermisoDeAperturaParaColaborar> aperturas;
    private static RepositorioAperturas instancia;

    public static RepositorioAperturas getInstancia() {
        if(instancia == null) {
            instancia = new RepositorioAperturas();
        }
        return instancia;
    }
    public List<PermisoDeAperturaParaColaborar> aperturasEntreFechas(LocalDate fechaInicio, LocalDate fechaFin){
        return null;//el repositorio este vuela a la mierda con la BD andando
        /*verificarExistenciaAperturas();

        return aperturas.stream().filter(apertura -> apertura.aperturaParaEntregaDeDonacionEntre(fechaInicio, fechaFin)).toList();*/
    }
    public Integer cantidadDeDepositosDeHeladeraEntreFechas(Heladera heladera, LocalDate fechaInicio, LocalDate fechaFin){
        return this.aperturasEntreFechas(fechaInicio,fechaFin).stream().filter(apertura -> apertura.getMotivo().equals(INGRESAR_VIANDAS_DONADAS)).toList().size();
    }
    public Integer cantidadDeRetirosDeHeladeraEntreFechas(Heladera heladera, LocalDate fechaInicio, LocalDate fechaFin){
        return this.aperturasEntreFechas(fechaInicio,fechaFin).stream().filter(apertura -> apertura.getMotivo().equals(RETIRAR_VIANDA)).toList().size();
    }

    public void agregarApertura(PermisoDeAperturaParaColaborar apertura) {
        verificarExistenciaAperturas();

        aperturas.add(apertura);
        System.out.println("Agregue apertura " + apertura.getMotivo() + " " + apertura.getFechaDeVencimiento());
    }

    private void verificarExistenciaAperturas() {
        if(this.aperturas == null) {
            this.aperturas = new ArrayList<>();
        }
    }
}
