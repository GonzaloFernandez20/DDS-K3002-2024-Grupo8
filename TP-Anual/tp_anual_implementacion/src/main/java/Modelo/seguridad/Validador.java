package Modelo.seguridad;

import java.util.Arrays;
import java.util.List;

public class Validador { // -> Deberia ser una clase singleton y laburar con 1 en todo el sistema
    private static Validador instancia = null;
    private static List<Criterio> LISTA_CRITERIOS; // -> Lista estática y constante de objetos Criterio

    private Validador(){ // Constructor
        instanciarCriteriosDeValidacion();
    }

    public static Validador getInstancia() {
        if (instancia == null) {
            instancia = new Validador();
        }
        return instancia;
    }
    // ----------------------------------------------------------------
    public boolean validarConstrasenia (String contrasenia) {
        return LISTA_CRITERIOS.stream().allMatch(criterio -> criterio.criterioSeguridad(contrasenia));
    }

    // <--------------- FUNCIONES AUXILIARES --------------->
    private void instanciarCriteriosDeValidacion(){
        LISTA_CRITERIOS = Arrays.asList(
            new CumpleConElLargo(),
            new EsContraseniaFuerte()
        );
    }
}