package Modelo.Dominio.tecnico;

import Modelo.Dominio.localizacion.PuntoEnElMapa;

import java.util.ArrayList;
import java.util.List;

public class LocalizadorDeTecnicos {
    private static LocalizadorDeTecnicos instancia;
    private List <Tecnico> tecnicos;

    private LocalizadorDeTecnicos() {
        tecnicos = new ArrayList<>();
    }

    public static LocalizadorDeTecnicos getInstancia() {
        if (instancia == null) {
            instancia = new LocalizadorDeTecnicos();
        }
        return instancia;
    }

    public Tecnico obtenerTecnicoMasCercano(PuntoEnElMapa punto){
        //TODO
        return null;
    }

}
