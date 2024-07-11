package contribucion;

import colaborador.Colaborador;
import heladera.Heladera;
import heladera.Vianda;
import persona.PersonaHumana;
import nuestras_excepciones.ColaboracionInvalida;

import java.util.List;
import java.time.LocalDate;

public class DistribucionDeVianda extends Contribucion {
    private Heladera heladeraDeOrigen;
    private Heladera heladeraDestino;
    private int cantDeViandas;
    private MotivoDeDistribucion motivo;
    double coeficiente;

    public DistribucionDeVianda(Colaborador colaborador, LocalDate fechaDeDonacion, Heladera heladeraDeOrigen, Heladera heladeraDestino, int cantDeViandas, MotivoDeDistribucion motivo) {
        super(colaborador, fechaDeDonacion);
        this.heladeraDestino = heladeraDestino;
        this.heladeraDeOrigen = heladeraDeOrigen;
        this.cantDeViandas = cantDeViandas;
        this.motivo = motivo;
        this.coeficiente = 1;
    }

    @Override
    public void procesarContribucion() throws ColaboracionInvalida{
        if(colaborador.getPersona() instanceof PersonaHumana) {
            List<Vianda> viandas = heladeraDeOrigen.retirarViandas(cantDeViandas);
            viandas.forEach(vianda -> vianda.trasladar(heladeraDestino));
            heladeraDestino.recibirViandas(viandas);
        }
        else{
            //colaboracion inválida no es persona humana
            throw new ColaboracionInvalida("Para distribuir viandas debés ser una persona HUMANA");
        }
    }

    public double puntosQueSumaColaborador() {
        return cantDeViandas * coeficiente;
    }
}