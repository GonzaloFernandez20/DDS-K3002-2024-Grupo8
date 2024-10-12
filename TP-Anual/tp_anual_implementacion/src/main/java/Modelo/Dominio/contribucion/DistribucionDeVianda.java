package Modelo.Dominio.contribucion;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.suscripcion.CreadorDeMensajes;


import java.util.ArrayList;
import java.time.LocalDate;

public class DistribucionDeVianda extends ContribucionConApertura {

    private final Heladera heladeraDeOrigen;
    private final MotivoDeDistribucion motivoDeDistribucion;
    private final Integer cantidadDeViandas;
    private final Heladera heladeraDestino;

    public DistribucionDeVianda(Colaborador colaborador,
                                Heladera heladeraDeOrigen,
                                Heladera heladeraDestino,
                                MotivoDeDistribucion motivoDeDistribucion,
                                Integer cantidadDeViandas,
                                LocalDate fechaDeContribucion) {
        this.colaborador = colaborador;
        this.heladeraDestino = heladeraDestino;
        this.heladeraDeOrigen = heladeraDeOrigen;
        this.motivoDeDistribucion = motivoDeDistribucion;
        this.cantidadDeViandas = cantidadDeViandas;
        this.viandas = new ArrayList<>();
        this.fechaDeContribucion = fechaDeContribucion;
    }

    @Override
    public void procesarLaContribucion() {
        if (viandas.isEmpty()){
            this.viandas = heladeraDeOrigen.retirarViandas(cantidadDeViandas);

            for (Vianda vianda : viandas){
                vianda.trasladar(heladeraDestino);
            }
        }else{
            super.procesarLaContribucion();
        }
    }

    @Override
    public void manejarViandasQueNoEntraron() {
        /*La distribucion se ocupa de que no se pierda referecia a cuales fueron las viandas que el colaborador sacó de la heladera origen y ataja la excepcion
        en caso de que no entren en la heladera destino todas las viandas que saco de origen. El modo de manejarlo es que el colaborador va a ingresar solo aquellas
        viandas que entren y se va a quedar con las que no entraron por lo que estas viandas continuan en estaddo "EN_TRASLADO" para reflejar que estan en manos de
        quien las saco para distribuir. Lo importante es mantener la trazabilidad de donde estan las viandas cuando alguien se ofrece a distribuirlas
        */
        int viandasQueNoEntraron = 0;
        for (Vianda vianda : viandas) {
            if (vianda.getEstado() == EstadoVianda.EN_TRASLADO) {
                viandasQueNoEntraron++;
            }
        }
        colaborador.notificar(CreadorDeMensajes.sugerirHeladeras(heladeraDestino,viandasQueNoEntraron));
    }

    @Override
    public double puntosQueSumaColaborador() {
        double coeficiente = 1;
        return viandas.size() * coeficiente;
    }

    // ---- Getters y Setters
    public Heladera getHeladeraOrigen() { return heladeraDeOrigen; }
    public Colaborador getColaborador() { return colaborador; }
    public MotivoDeDistribucion getMotivoDeDistribucion() {return motivoDeDistribucion;}
}
