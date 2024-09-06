package Modelo.Dominio.suscripcion;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;

public class GestorDeSuscripciones {
        private static GestorDeSuscripciones instancia;

        private GestorDeSuscripciones() {
        }

        public static GestorDeSuscripciones getInstancia() {
            if (instancia == null) {
                instancia = new GestorDeSuscripciones();
            }
            return instancia;
        }

        public void registrarSuscripcion(Heladera heladera,
                                         Colaborador colaborador,
                                         String evento) {

        }

}
