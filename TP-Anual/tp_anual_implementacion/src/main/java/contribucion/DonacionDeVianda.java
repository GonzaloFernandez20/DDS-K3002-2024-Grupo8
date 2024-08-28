package contribucion;
import nuestras_excepciones.ViandaRechazada;

import colaborador.Colaborador;
import heladera.Heladera;
import heladera.Vianda;
import sistema.ReporteDeTodasLasHeladeras;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class DonacionDeVianda extends Contribucion {

    private final Heladera heladera;
    private final List<Vianda> viandasDonadas;

    private ReporteDeTodasLasHeladeras reporteDeTodasLasHeladeras;

    @Override
    public void procesarContribucion() {
            try {
                heladera.recibirViandas(viandasDonadas);
                reporteDeTodasLasHeladeras.recibirReporteHeladeras(heladera, viandasDonadas.size());
            } catch (ViandaRechazada e) {
                //QUE HACEMOS ACA
                throw new RuntimeException(e);
            }

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

    private void setReporteDeTodasLasHeladeras(ReporteDeTodasLasHeladeras reporteDeTodasLasHeladeras) {
        this.reporteDeTodasLasHeladeras = reporteDeTodasLasHeladeras;
    }

    public DonacionDeVianda(Colaborador colaborador, LocalDate fechaDeDonacion, List<Vianda> viandasDonadas, Heladera heladera) {
        super(colaborador, fechaDeDonacion);
        if(viandasDonadas != null) {
            this.viandasDonadas = viandasDonadas;
        } else {
            this.viandasDonadas = new ArrayList<>();
        }
        this.heladera = heladera;
    }

    public boolean requieroAcceso() { return true;}
    public Heladera getHeladera() {return heladera;}
}