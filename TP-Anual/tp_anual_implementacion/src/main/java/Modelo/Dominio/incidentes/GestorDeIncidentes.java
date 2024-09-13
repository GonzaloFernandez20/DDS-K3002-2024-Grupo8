package Modelo.Dominio.incidentes;

import DTOs.FallaTecnicaDTO;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.localizacion.PuntoEnElMapa;
import Modelo.Dominio.sistema.Sistema;
import Modelo.Dominio.tecnico.LocalizadorDeTecnicos;
import Modelo.Dominio.tecnico.Tecnico;
import Modelo.Factorys.FactoryFallaTecnica;

public class GestorDeIncidentes {
    private static GestorDeIncidentes instancia = null;

    public static GestorDeIncidentes getInstancia() {
        if (instancia == null) {
            instancia = new GestorDeIncidentes();
        }
        return instancia;
    }

    public static void reportarAlerta(Heladera heladera, TipoAlerta tipoAlerta){
        Alerta nuevoIncidente = new Alerta(tipoAlerta, heladera);
        reportar(nuevoIncidente);
    }

    public static void reportarFallaTecnica(FallaTecnicaDTO fallaTecnicaDTO){
        FallaTecnica nuevoIncidente = FactoryFallaTecnica.CrearFallaTecnicaAPartirDe(fallaTecnicaDTO);
        reportar(nuevoIncidente);
    }

    private static void reportar(Incidente nuevoIncidente) {
        nuevoIncidente.getHeladeraDondeOcurrio().huboIncidente();
        darAvisoATecnico(nuevoIncidente);
    }

    private static void darAvisoATecnico(Incidente nuevoIncidente) {
        PuntoEnElMapa ubicacionDeIncidente = nuevoIncidente.
                                                       getHeladeraDondeOcurrio().
                                                       getUbicacion().
                                                       getPunto();
        Tecnico tecnicoAavisar = LocalizadorDeTecnicos.getInstancia().obtenerTecnicoMasCercano(ubicacionDeIncidente);

        String aviso = String.format("En la heladera: %s (%s)" + nuevoIncidente.obtenerInformacion(),
                nuevoIncidente.getHeladeraDondeOcurrio().getUbicacion().getNombreDelPunto(),
                nuevoIncidente.getHeladeraDondeOcurrio().getUbicacion().getDireccion());

        tecnicoAavisar.notificar(aviso);
    }
}