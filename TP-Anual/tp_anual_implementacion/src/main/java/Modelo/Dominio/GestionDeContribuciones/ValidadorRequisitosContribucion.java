package Modelo.Dominio.GestionDeContribuciones;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.persona.PersonaHumana;
import Modelo.Dominio.persona.PersonaJuridica;
import Modelo.Excepciones.ExcepcionFaltaTarjeta;
import Modelo.Excepciones.ExcepcionNoEsPersonaHumana;
import Modelo.Excepciones.ExcepcionNoEsPersonaJuridica;

public class ValidadorRequisitosContribucion {
    public static void validarRequisitos (Colaborador colaborador,
                                          FormaDeContribuciones formaDeContribucion) throws Exception{
        switch (formaDeContribucion){
            case CONTRIBUCION_JURIDICA -> {
                if (!(colaborador.getPersona() instanceof PersonaJuridica)) {
                    throw new ExcepcionNoEsPersonaJuridica("Solo colaboradores juridicos pueden realizar esta contribucion");
                }
            }
            case CONTRIBUCION_HUMANA, CONTRIBUCION_CON_APERTURA -> {
                if (!(colaborador.getPersona() instanceof PersonaHumana)) {
                    throw new ExcepcionNoEsPersonaHumana("Solo colaboradores humanos pueden realizar esta contribucion");
                }
                if (formaDeContribucion.equals(FormaDeContribuciones.CONTRIBUCION_CON_APERTURA)) {
                    if (colaborador.getTarjeta() == null) {
                        throw new ExcepcionFaltaTarjeta("Necesitas una tarjeta para acceder a una heladera");
                    }
                }
            }
        }
    }
}
// TODO: EL CONTROLADOR CHEQUEA QUE EL COLABORADOR NO SEA NULO Y DEVUELVE EL MENSAJE ADECUADO EN CASO DE QUE LO SEA. A SU VEZ, CATCHEA LA EXCEPCION QUE LE LLEGUE
