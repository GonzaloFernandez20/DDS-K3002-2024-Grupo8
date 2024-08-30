package contribucion;

import colaborador.Colaborador;
import heladera.Heladera;
import nuestras_excepciones.FallaHeladera;
import nuestras_excepciones.ViandaRechazada;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class DistribucionDeVianda extends ContribucionConApertura {

    private final Heladera heladeraDeOrigen;
    private final MotivoDeDistribucion motivo;

    public DistribucionDeVianda(Colaborador colaborador,
                                Heladera heladeraDeOrigen,
                                Heladera heladeraDestino,
                                MotivoDeDistribucion motivo) {
        this.colaborador = colaborador;
        this.heladeraDestino = heladeraDestino;
        this.heladeraDeOrigen = heladeraDeOrigen;
        this.motivo = motivo;
        this.viandasIngresadas = new ArrayList<>();
        this.fechaDeContribucion = LocalDate.now();
    }

    @Override
    public void procesarLaContribucion() {
        if (viandasIngresadas.isEmpty()){
            // TODO: RETIRAR VIANDAS DE LA HELADERA ORIGEN
            // DECIRLE A LA HELADERA QUE RETIRE LAS VIANDAS
            // ASIGNAR ESAS VIANDAS A LA LISTA DE LA CONTRIBUCION
            // CON CADA VIANDA:
                // TRASLADAR
                // SETEAR ESTADO A "EN TRASLADO"
        }else{
            super.procesarLaContribucion();
        }
    }

    public double puntosQueSumaColaborador() {
        double coeficiente = 1;
        return viandasIngresadas.size() * coeficiente;
    }

    public Heladera getHeladera() {return heladeraDeOrigen;}
}