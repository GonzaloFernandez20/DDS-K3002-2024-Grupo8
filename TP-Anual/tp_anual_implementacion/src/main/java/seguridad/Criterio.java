package seguridad;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public abstract class Criterio {
    public boolean generarCriterio(String contrasenia) {
        return true;
    }
}