package Modelo.Dominio.GestionDeContribuciones;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.persona.PersonaHumana;
import Modelo.Dominio.persona.PersonaJuridica;


public class ValidadorRequisitosContribucion {
    public static EstadoDeCumplimiento validarRequisitos (Colaborador colaborador,
                                                          FormaDeContribuciones formaDeContribucion) {
        switch (formaDeContribucion){
            case CONTRIBUCION_JURIDICA -> {
                if (colaborador.getPersona() instanceof PersonaJuridica) {
                    return EstadoDeCumplimiento.HABILITADO;
                }else return EstadoDeCumplimiento.INHABILITADO_TIPO_PERSONA;
            }
            case CONTRIBUCION_HUMANA, CONTRIBUCION_CON_APERTURA -> {
                if (colaborador.getPersona() instanceof PersonaHumana){
                    if (formaDeContribucion.equals(FormaDeContribuciones.CONTRIBUCION_CON_APERTURA)){
                        if (colaborador.getTarjeta() == null){
                            return EstadoDeCumplimiento.INHABILITADO_FALTA_TARJETA;
                        }
                    }
                    return EstadoDeCumplimiento.HABILITADO;
                }else return EstadoDeCumplimiento.INHABILITADO_TIPO_PERSONA;
            }
            default -> {
                return EstadoDeCumplimiento.HABILITADO;
            }
        }
    }
}
// TODO: EL CONTROLADOR CHEQUEA QUE EL COLABORADOR NO SEA NULO Y DEVUELVE EL MENSAJE ADECUADO EN CASO DE QUE LO SEA.