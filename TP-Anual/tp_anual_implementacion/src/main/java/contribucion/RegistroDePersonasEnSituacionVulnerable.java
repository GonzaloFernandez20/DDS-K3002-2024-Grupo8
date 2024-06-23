package contribucion;

import colaborador.Colaborador;
import persona_vulnerable.PersonaSituacionVulnerable;
import persona_vulnerable.Vinculacion;

import java.util.List;
import java.time.LocalDate;

public class RegistroDePersonasEnSituacionVulnerable extends Contribucion {
    private List<Vinculacion> tarjetasRepartidas;

    public RegistroDePersonasEnSituacionVulnerable(Colaborador colaborador, LocalDate fechaDeDonacion, List<Vinculacion> tarjetasRepartidas) {
        super(colaborador, fechaDeDonacion);
        this.tarjetasRepartidas = tarjetasRepartidas;
    }

    @Override
    public void contribuir() {

    }

    public void darDeAlta(PersonaSituacionVulnerable personaSituacionVulnerable) {
        Vinculacion vinculacion = new Vinculacion(colaborador, personaSituacionVulnerable, LocalDate.now());
        entregarTarjeta(vinculacion);
    }

    public void entregarTarjeta(Vinculacion nuevaVinculacion) {
        tarjetasRepartidas.add(nuevaVinculacion);
    }
}
