package API;

import java.util.List;

import localizacion.*;

public class ResponseRecomendacion {
   
    public List<PuntoEnElMapa> puntosRecomendados;

    public List<PuntoEnElMapa> getPosiblesPuntosDeColocacion(){ return this.puntosRecomendados; }
}

