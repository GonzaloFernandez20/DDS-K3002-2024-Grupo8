package validadorDeColaboraciones;


import colaborador.Colaborador;
import persona.PersonaHumana;

public class EsPersonaHumana extends CriterioDeAceptacion {
    @Override
    public boolean validar(Colaborador colaborador){
        return colaborador.getPersona() instanceof PersonaHumana;
    }
}
