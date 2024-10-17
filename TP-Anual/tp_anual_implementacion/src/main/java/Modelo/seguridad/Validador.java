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
    public boolean validarConstrasenia (String contrasenia) throws RuntimeException {
        return LISTA_CRITERIOS.stream().allMatch(criterio -> {
            try {
                return criterio.criterioSeguridad(contrasenia);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }

    public boolean validarNombreDeUsuario(String nombreDeUsuario) throws Exception {
        // Controlar que no exista otro usuario con ese nombre en la BD
        return true;
    }

    // <--------------- FUNCIONES AUXILIARES --------------->
    private void instanciarCriteriosDeValidacion(){
        LISTA_CRITERIOS = Arrays.asList(
            new CumpleConElLargo(),
            new EsContraseniaFuerte()
        );
    }
}