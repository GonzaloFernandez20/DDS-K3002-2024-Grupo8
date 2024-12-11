package Modelo.Dominio.incidentes;

import DTOs.FallaTecnicaDTO;
import Modelo.Dominio.Repositories.heladera.HeladeraRepository;
import Modelo.Dominio.Repositories.incidentes.AlertaRepository;
import Modelo.Dominio.Repositories.incidentes.FallaTecnicaRepository;
import Modelo.Dominio.heladera.EstadoHeladera;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.localizacion.PuntoEnElMapa;
import Modelo.Dominio.tecnico.LocalizadorDeTecnicos;
import Modelo.Dominio.tecnico.Tecnico;
import Modelo.Mappers.FactoryFallaTecnica;
import Repositorios.RepositorioIncidentes;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GestorDeIncidentes {

    private static AlertaRepository alertaRepository;
    private static FallaTecnicaRepository fallaTecnicaRepository;
    private static  HeladeraRepository heladeraRepository;

    @Autowired
    public GestorDeIncidentes(AlertaRepository alertaRepository, FallaTecnicaRepository fallaTecnicaRepository, HeladeraRepository heladeraRepository) {
        GestorDeIncidentes.alertaRepository = alertaRepository;
        GestorDeIncidentes.fallaTecnicaRepository = fallaTecnicaRepository;
        GestorDeIncidentes.heladeraRepository = heladeraRepository;
    }

    public static void reportarAlerta(Heladera heladera, TipoAlerta tipoAlerta){
        if (heladera.getEstado().equals(EstadoHeladera.ACTIVA)){
            Alerta nuevoIncidente = new Alerta(tipoAlerta, heladera);
            reportar(nuevoIncidente);
            heladeraRepository.save((nuevoIncidente.getHeladeraDondeOcurrio()));
            alertaRepository.save(nuevoIncidente);
        }
    }

    public static void reportarFallaTecnica(FallaTecnicaDTO fallaTecnicaDTO){
        FallaTecnica nuevoIncidente = FactoryFallaTecnica.CrearFallaTecnicaAPartirDe(fallaTecnicaDTO);
        reportar(nuevoIncidente);
        heladeraRepository.save((nuevoIncidente.getHeladeraDondeOcurrio()));
        fallaTecnicaRepository.save(nuevoIncidente);
    }

    public static void reportar(Incidente nuevoIncidente) {
        nuevoIncidente.getHeladeraDondeOcurrio().huboIncidente();
        darAvisoATecnico(nuevoIncidente);
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