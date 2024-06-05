package API;

import java.util.List;

import heladera.PuntoEnElMapa;

public class ResponseRecomendacion {
   
    public List<PuntoEnElMapa> puntosRecomendados;

    public List<PuntoEnElMapa> getPosiblesPuntosDeColocacion(){ return this.puntosRecomendados; }
}

