package contribucion;
import nuestras_excepciones.ViandaRechazada;
import persona.PersonaHumana;

import colaborador.Colaborador;
import heladera.Heladera;
import heladera.Vianda;
import nuestras_excepciones.ColaboracionInvalida;
import sistema.ReporteDeTodasLasHeladeras;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class DonacionDeVianda extends Contribucion {
    private List<Vianda> viandasDonadas;
    private Heladera heladera;
    double coeficiente;
    private ReporteDeTodasLasHeladeras reporteDeTodasLasHeladeras;

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
        this.coeficiente = 1.5;
    }

    public Heladera getHeladera() {return heladera;}

    @Override
    public boolean requieroAcceso() { return true;}

    public void agregarViandaADonacion(Vianda vianda) {
        viandasDonadas.add(vianda);
    }

    @Override
    public void procesarContribucion(){
            try {
                heladera.recibirViandas(viandasDonadas);
                reporteDeTodasLasHeladeras.recibirReporteHeladeras(heladera, viandasDonadas.size());
            } catch (ViandaRechazada e) {
                //QUE HACEMOS ACA
                throw new RuntimeException(e);
            }

    }

    public double puntosQueSumaColaborador() {
        return viandasDonadas.size() * coeficiente;
    }
}