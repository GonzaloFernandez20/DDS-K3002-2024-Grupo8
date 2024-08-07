package contribucion;

import java.io.IOException;
import java.time.LocalDate;

import API.APIRequester;
import API.ResponseRecomendacion;
import colaborador.Colaborador;
import colaborador.PersonaJuridica;
import heladera.Heladera;
import heladera.PuntoEnElMapa;
import sistema.Sistema;

public class HacerseCargoDeHeladera extends Contribucion{

    private final Heladera heladeraACargo;

    public HacerseCargoDeHeladera(Colaborador colaborador, LocalDate fechaDeDonacion, Heladera heladeraACargo) {
        super(colaborador, fechaDeDonacion);
        this.heladeraACargo = heladeraACargo;
    }

    @Override
    public void procesarLaContribucion() {
        heladeraACargo.setColaborador((PersonaJuridica) colaborador);
        Sistema.getInstancia().darDeAltaHeladera(heladeraACargo);
    }
    @Override
    public double puntosQueSumaColaborador() {
        return 5;
    }

    // VALORES DE EJEMPLO DE LA MOCK API:
    //double latitud = 39.7749;
	//double longitud = -120.4194;
	//int radio = 7000;

	//double latitud = 37.7749;
	//double longitud = -122.4194;
	//int radio = 5000;

    public void consultarRecomendaciones(double latitud, double longitud, int radio) throws IOException{
        ResponseRecomendacion puntos = APIRequester.getInstancia().obtenerPuntosRecomendados(latitud, longitud, radio);
        
        // TE DEVUELVE LOS PUNTOS RECOMENDADOS PARA QUE VOS ELIJAS...
        if (puntos != null && puntos.getPosiblesPuntosDeColocacion() != null) {
			for (PuntoEnElMapa punto : puntos.getPosiblesPuntosDeColocacion()) {
				System.out.println(" " + punto.getLatitud());
				System.out.println(punto.getLongitud());
			}
		} else {
			System.out.println("La lista de puntos recomendados es nula o vacía.");
		}
    }
}
