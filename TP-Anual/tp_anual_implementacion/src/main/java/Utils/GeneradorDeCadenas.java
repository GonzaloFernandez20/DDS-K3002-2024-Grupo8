package Utils;

import Modelo.seguridad.Validador;

import java.security.SecureRandom;

public final class GeneradorDeCadenas {
    public static String generarCadena(int largo, String caracteresASerUtilizados) {
        String characters = caracteresASerUtilizados;
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder sb = new StringBuilder(largo);

        for (int i = 0; i < largo; i++) {
            int index = secureRandom.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }

    public static String generarContraseniaSegura() {
        String contrasenia;
        do{
            contrasenia = generarCadena(9, "ABCDEFGHIJKLMNÑOPQRSTUVWXYZabcdefghijklmnñopqrstuvwxyz0123456789 ¡!#$%&'()*+,-./:;<=>¿?@[]^_`{|}~");
        } while(!Validador.getInstancia().validarConstrasenia(contrasenia));
        return contrasenia;
    }
}
