package Modelo.Mappers;

import DTOs.HeladeraSuscripcionDTO;
import DTOs.SuscripcionDTO;
import Modelo.Dominio.heladera.Heladera;

import java.util.List;
import java.util.Optional;

public class HeladeraSuscripcionMapper {

    public static HeladeraSuscripcionDTO convertirEnHeladeraSuscripcionDTO(Heladera heladera, List<SuscripcionDTO> suscripcionesColaborador){
        boolean estaSuscritoAFallas = suscripcionesColaborador.stream().
                                                               anyMatch(suscripcion ->suscripcion.getHeladera().getid_heladera().equals(heladera.getid_heladera()) && suscripcion.getEvento().equals("Se produjo una falla"));
        int quedanN = 0;
        Optional<SuscripcionDTO> suscripcionAQuedan = suscripcionesColaborador.stream().
                                                                               filter(suscripcion -> suscripcion.getHeladera().getid_heladera().equals(heladera.getid_heladera()) && suscripcion.getEvento().startsWith("Quedan")).
                                                                               findFirst();
        if(suscripcionAQuedan.isPresent()){quedanN = Integer.parseInt(suscripcionAQuedan.get().getEvento().split("\\s+")[1]);}

        int faltanN = 0;
        Optional<SuscripcionDTO> suscripcionAFaltan = suscripcionesColaborador.stream().
                                                                              filter(suscripcion -> suscripcion.getHeladera().getid_heladera().equals(heladera.getid_heladera()) && suscripcion.getEvento().startsWith("Faltan")).
                                                                              findFirst();
        if(suscripcionAFaltan.isPresent()){faltanN = Integer.parseInt(suscripcionAFaltan.get().getEvento().split("\\s+")[1]);}

        return new HeladeraSuscripcionDTO(heladera.getid_heladera(),
                                          heladera.getNombreDelPunto(),
                                          heladera.getUbicacion().getDireccion().getCalle(),
                                          heladera.getUbicacion().getDireccion().getAltura(),
                                          heladera.getUbicacion().getCiudad(),
                                          estaSuscritoAFallas,
                                          quedanN,
                                          faltanN);
    }
}
