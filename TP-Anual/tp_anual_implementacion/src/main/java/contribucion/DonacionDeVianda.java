package contribucion;
import persona.PersonaHumana;
import validadorDeColaboraciones.CriterioDeAceptacion;
import validadorDeColaboraciones.ValidadorDeColaboraciones;
import validadorDeColaboraciones.EsPersonaHumana;
import colaborador.Colaborador;
import heladera.Heladera;
import heladera.Vianda;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class DonacionDeVianda extends Contribucion {
    private List<Vianda> viandasDonadas;
    private Heladera heladera;
    double coeficiente;

    public DonacionDeVianda(Colaborador colaborador, LocalDate fechaDeDonacion, List<Vianda> viandasDonadas, Heladera heladera) {
        super(colaborador, fechaDeDonacion);
        this.viandasDonadas = viandasDonadas;
        this.heladera = heladera;
        this.coeficiente = 1.5;
    }

    public void agregarViandaADonacion(Vianda vianda) {
        viandasDonadas.add(vianda);
    }

    @Override
    public void procesarContribucion() {
        if(colaborador.getPersona() instanceof PersonaHumana){
        heladera.recibirViandas(viandasDonadas);
        }
        else{
                //colaboracion inválida hacer algo
            //colaborador no es persona humana
        }
    }

    public double puntosQueSumaColaborador() {
        return viandasDonadas.size() * coeficiente;
    }
}