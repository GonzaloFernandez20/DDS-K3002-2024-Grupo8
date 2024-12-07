package Servicios_Externos_APIs.API;

import java.util.List;

import Modelo.Dominio.localizacion.PuntoEnElMapa;

public class ResponseRecomendacion {
   
    public List<PuntoEnElMapa> puntosRecomendados;

    public List<PuntoEnElMapa> getPosiblesPuntosDeColocacion(){ return this.puntosRecomendados; }
}

