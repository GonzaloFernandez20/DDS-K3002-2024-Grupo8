package seguridad;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java. lang. Boolean;
import java.lang.reflect.Constructor;
import java.util.List;


public class Validador {
    String contrasenia;
    static List<Criterio> criterios;

    public Validador(String contrasenia, List<Criterio> criterios) {
        this.contrasenia = contrasenia;
        Validador.criterios = criterios;
    }

    public boolean validarRegistro(List<Criterio> criterios, String contrasenia) {
        return criterios.stream().allMatch(criterio -> criterio.generarCriterio(contrasenia));
    }
}
