package validadorDeColaboraciones;

import colaborador.Colaborador;

public abstract class CriterioDeAceptacion {
    public boolean validar(Colaborador colaborador){
        return false;
    }
}
