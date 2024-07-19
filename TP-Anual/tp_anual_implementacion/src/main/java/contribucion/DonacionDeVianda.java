package contribucion;
import nuestras_excepciones.ViandaRechazada;
import persona.PersonaHumana;

import colaborador.Colaborador;
import heladera.Heladera;
import heladera.Vianda;
import nuestras_excepciones.ColaboracionInvalida;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class DonacionDeVianda extends Contribucion {
    private List<Vianda> viandasDonadas;
    private Heladera heladera;
    double coeficiente;

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

    public void agregarViandaADonacion(Vianda vianda) {
        viandasDonadas.add(vianda);
    }

    @Override
    public void procesarContribucion(){
            try {
                heladera.recibirViandas(viandasDonadas);
            } catch (ViandaRechazada e) {
                //QUE HACEMOS ACA
                throw new RuntimeException(e);
            }

    }

    public double puntosQueSumaColaborador() {
        return viandasDonadas.size() * coeficiente;
    }
}