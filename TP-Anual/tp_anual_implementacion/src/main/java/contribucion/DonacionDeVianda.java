package contribucion;

import colaborador.Colaborador;
import heladera.Heladera;
import sistema.ReporteDeTodasLasHeladeras;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class DonacionDeVianda extends Contribucion {
    private final Heladera heladera;
    private final List<Vianda> viandasDonadas;

    public DonacionDeVianda(Colaborador colaborador, Heladera heladera) {
        this.colaborador = colaborador;
        this.fechaDeContribucion = LocalDate.now();
        this.heladera = heladera;
        this.viandasDonadas = new ArrayList<>();
    }

    @Override
    public void procesarLaContribucion() {
        heladera.recibirViandas(viandasDonadas);
        for (Vianda vianda : viandasDonadas){
            vianda.setEstadoVianda(EstadoVianda.ENTREGADA);
        }
        colaborador.registrarContribucion(this);
    }

    @Override
    public double puntosQueSumaColaborador() {
        double coeficiente = 1.5;
        return viandasDonadas.size() * coeficiente;
    }

    public void agregarViandaADonacion(Vianda vianda) {
        viandasDonadas.add(vianda);
    }

    // -----------------------------------
    public Heladera getHeladera() {return heladera;}
}