package contribucion;

import colaborador.Colaborador;
import heladera.Heladera;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class DonacionDeVianda extends ContribucionConApertura {

    public DonacionDeVianda(Colaborador colaborador, Heladera heladera) {
        this.colaborador = colaborador;
        this.fechaDeContribucion = LocalDate.now();
        this.heladeraDestino = heladera;
        this.viandasIngresadas = new ArrayList<>();
    }

    @Override
    public double puntosQueSumaColaborador() {
        double coeficiente = 1.5;
        return viandasIngresadas.size() * coeficiente;
    }

    public void agregarViandaADonacion(Vianda vianda) {
        viandasIngresadas.add(vianda);
    }

    // -----------------------------------
    public Heladera getHeladera() {return heladeraDestino;}
}