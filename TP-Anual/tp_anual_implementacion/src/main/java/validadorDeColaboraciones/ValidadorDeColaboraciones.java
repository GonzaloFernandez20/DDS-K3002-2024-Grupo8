package validadorDeColaboraciones;

import java.util.List;
import colaborador.Colaborador;
import contribucion.Contribucion;

public class ValidadorDeColaboraciones {
    private Colaborador colaborador;
    private Contribucion contribucion;
    private List<CriterioDeAceptacion> criteriosDeAceptacion;

    public ValidadorDeColaboraciones(Colaborador colaborador, Contribucion contribucion, List<CriterioDeAceptacion> criteriosDeAceptacion) {
        this.colaborador = colaborador;
        this.contribucion = contribucion;
        this.criteriosDeAceptacion = criteriosDeAceptacion;
    }
    public boolean validarProcesamientoDeContribucion(List<CriterioDeAceptacion> criterios){
        return criterios.stream().allMatch(criterio -> criterio.validar(colaborador));
    }
}