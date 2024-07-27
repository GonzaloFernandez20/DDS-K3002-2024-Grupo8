package contribucion;

import java.io.IOException;
import java.time.LocalDate;

import API.APIRequester;
import API.ResponseRecomendacion;
import colaborador.Colaborador;
import heladera.Heladera;
import localizacion.PuntoEnElMapa;
import sistema.Sistema;

public class HacerseCargoDeHeladera extends Contribucion{

    private final Heladera heladeraACargo;
    double coeficiente; // Necesitas esto?

    public HacerseCargoDeHeladera(Colaborador colaborador, LocalDate fechaDeDonacion, Heladera heladeraACargo) {
        super(colaborador, fechaDeDonacion);
        this.heladeraACargo = heladeraACargo;
        this.coeficiente = 0.5;
    }

    public boolean requieroAcceso() { return false;}

    @Override
    public void procesarContribucion(){
            heladeraACargo.setColaborador((Colaborador) colaborador);
            Sistema.getInstancia().darDeAltaHeladera(heladeraACargo);
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

    @Override
    public Object getHeladera() { return heladeraACargo; }
}
