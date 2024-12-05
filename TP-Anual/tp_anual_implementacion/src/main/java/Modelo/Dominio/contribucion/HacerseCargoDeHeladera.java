package Modelo.Dominio.contribucion;

import java.io.IOException;
import java.time.LocalDate;

import Modelo.Dominio.sistema.RegistroDeHeladeras;
import Repositorios.RepositorioHeladeras;
import Servicios_Externos_APIs.API.APIRequester;
import Servicios_Externos_APIs.API.ResponseRecomendacion;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.localizacion.PuntoEnElMapa;

import Modelo.Dominio.sistema.RegistroDeHeladeras;
import Servicios_Externos_APIs.API.APIRequester;
import Servicios_Externos_APIs.API.ResponseRecomendacion;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.LocalDate;

@Entity
@Table(name = "HacerseCargoDeHeladera")
public class HacerseCargoDeHeladera extends Contribucion{
    @OneToOne( cascade = CascadeType.PERSIST )
    @JoinColumn(name = "heladera_a_cargo", referencedColumnName = "id_heladera")
    private Heladera heladeraACargo;

    public HacerseCargoDeHeladera(Colaborador colaborador, Heladera heladeraACargo) {
        this.colaborador = colaborador;
        this.fechaDeContribucion = LocalDate.now();
        this.heladeraACargo = heladeraACargo;
    }

    public HacerseCargoDeHeladera() {
    }

    @Override
    public void procesarLaContribucion() {
        colaborador.registrarContribucion(this);
    }

    @Override
    public double puntosQueSumaColaborador() {
        return 0;
    }

    // VALORES DE EJEMPLO DE LA MOCK API:
    //double latitud = 39.7749;
	//double longitud = -120.4194;
	//int radio = 7000;

	//double latitud = 37.7749;
	//double longitud = -122.4194;
	//int radio = 5000;

    public Heladera getHeladeraACargo() {
        return heladeraACargo;
    }

    public void setHeladeraACargo(Heladera heladeraACargo) {
        this.heladeraACargo = heladeraACargo;
    }

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
