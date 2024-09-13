package Modelo.Dominio.contribucion;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;

import java.time.LocalDate;
import java.util.List;

public class DonacionDeVianda extends ContribucionConApertura {

    public DonacionDeVianda(Colaborador colaborador, Heladera heladera, List <Vianda> viandas, LocalDate fechaDeContribucion) {
        this.colaborador = colaborador;
        this.fechaDeContribucion = fechaDeContribucion;
        this.heladeraDestino = heladera;
        this.viandas = viandas;
    }
    public void agregarViandaADonacion(Vianda vianda) {
        viandas.add(vianda);
    }

    @Override
    public double puntosQueSumaColaborador() {
        double coeficiente = 1.5;
        return viandas.size() * coeficiente;
    }

    @Override
    public void manejarViandasQueNoEntraron() {
        /*En la donacion de viandas el listado de viandas donadas va a reflejar solo aquellas que explicitamente entraron en la
        heladera es decir, aquellas que se donaron en esa heladera. Aunque el colaborador haya declarado que llevaba más, solo pudo ingresar cierta cantidad y esa es
        la cantidad donada. Queda a criterio del colaborador generar otra donacion de viandas en otra heladera con aquellas que no pudo ingresar.
        La diferencia con la distribucion, es que cuando el colaborador retira viandas es responsable de volver a colocarlas en otra heladera porque él no las donó
        y por eso la distribucion mantiene las viandas que no entraron, en cambio, cuando él las dona, elegir cuantas donar y que hacer si al final no entran no
        recae en un problema de responsabilidad y no es necesario llevar la trazabailidad de viandas que "fueron prometidas".
        */
        for (Vianda vianda : viandas) {
            if (vianda.getEstado() == EstadoVianda.NO_ENTREGADA) {
                viandas.remove(vianda);
            }
        }
    }


    // ---- Getters y Setters
    public Colaborador getColaborador() {return colaborador;}

}