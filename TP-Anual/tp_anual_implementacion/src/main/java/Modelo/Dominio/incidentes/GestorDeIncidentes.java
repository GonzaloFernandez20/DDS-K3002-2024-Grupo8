package Modelo.Dominio.incidentes;

import DTOs.FallaTecnicaDTO;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.localizacion.PuntoEnElMapa;
import Modelo.Dominio.tecnico.LocalizadorDeTecnicos;
import Modelo.Dominio.tecnico.Tecnico;
import Modelo.Mappers.FactoryFallaTecnica;

public class GestorDeIncidentes {

    public static void reportarAlerta(Heladera heladera, TipoAlerta tipoAlerta){
        Alerta nuevoIncidente = new Alerta(tipoAlerta, heladera);
        reportar(nuevoIncidente);
    }

    public static void reportarFallaTecnica(FallaTecnicaDTO fallaTecnicaDTO){
        FallaTecnica nuevoIncidente = FactoryFallaTecnica.CrearFallaTecnicaAPartirDe(fallaTecnicaDTO);
        reportar(nuevoIncidente);
    }

    public static void reportar(Incidente nuevoIncidente) {
        nuevoIncidente.getHeladeraDondeOcurrio().huboIncidente();
        darAvisoATecnico(nuevoIncidente);
        // TODO: Reemplazar por el repositorio real
        //RepositorioIncidentes.getInstancia().sumarIncidente(nuevoIncidente);
    }

    private static void darAvisoATecnico(Incidente nuevoIncidente) {
        PuntoEnElMapa ubicacionDeIncidente = nuevoIncidente.
                                                            getHeladeraDondeOcurrio().
                                                            getUbicacion().
                                                            getPunto();

        Tecnico tecnicoAavisar = LocalizadorDeTecnicos.getInstancia().obtenerTecnicoMasCercano(ubicacionDeIncidente);

        if (tecnicoAavisar != null){
            String aviso = String.format("En la heladera: %s (%s) " + nuevoIncidente.obtenerInformacion(),
                    nuevoIncidente.getHeladeraDondeOcurrio().getUbicacion().getNombreDelPunto(),
                    nuevoIncidente.getHeladeraDondeOcurrio().getUbicacion().getDireccion());

            tecnicoAavisar.notificar(aviso);
        }
    }
}