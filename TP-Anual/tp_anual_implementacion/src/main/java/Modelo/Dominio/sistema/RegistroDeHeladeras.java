package Modelo.Dominio.sistema;

import Modelo.Dominio.heladera.Heladera;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

@Getter
public class RegistroDeHeladeras {
    private static RegistroDeHeladeras instancia;
    private final List<Heladera> heladeras;

    private RegistroDeHeladeras() {
        heladeras = new ArrayList<>();
    }

    public static RegistroDeHeladeras getInstancia() {
        if (instancia == null) {
            instancia = new RegistroDeHeladeras();
        }
        return instancia;
    }

    public List<Heladera> sugerirHeladerasDestino(Heladera heladera, int cantidadParaRedistribuir){
        List <Heladera> sugerencias = new ArrayList<>();
        int cantidadDeViandasARedistribuir = cantidadParaRedistribuir;
        Heladera heladeraDeReferencia = heladera;
        List <Heladera> todasLasHeladeras = new ArrayList<>(heladeras);

        while(cantidadDeViandasARedistribuir > 0){
            todasLasHeladeras.remove(heladeraDeReferencia);
            Heladera heladeraSugerida = obtenerHeladeraMasCercana(heladeraDeReferencia, todasLasHeladeras);
            cantidadDeViandasARedistribuir -= heladeraSugerida.espacioDisponible();
            sugerencias.add(heladeraSugerida);
            heladeraDeReferencia = heladeraSugerida;
        }
        return sugerencias;
    }

    private Heladera obtenerHeladeraMasCercana(Heladera heladera, List<Heladera> heladeras){
        Heladera heladeraMasCercana = null;
        double menorDistancia = Double.MAX_VALUE;

        for (Heladera heladeraActual : heladeras) {
            double distancia = calcularDistanciaEuclediana(heladera, heladeraActual);
            if (distancia < menorDistancia) {
                menorDistancia = distancia;
                heladeraMasCercana = heladeraActual;
            }
        }
        return heladeraMasCercana;
    }

    private double calcularDistanciaEuclediana(Heladera heladera1, Heladera heladera2){
        double diferenciaX = heladera1.getLongitud() - heladera2.getLongitud();
        double diferenciaY = heladera1.getLatitud() - heladera2.getLatitud();
        return sqrt( pow(diferenciaX, 2) + pow(diferenciaY, 2) );
    }

}
