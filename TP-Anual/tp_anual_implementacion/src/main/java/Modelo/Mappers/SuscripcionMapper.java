package Modelo.Mappers;

import DTOs.SuscripcionDTO;
import Modelo.Dominio.suscripcion.Suscripcion;

public class SuscripcionMapper {
    public static SuscripcionDTO convertirEnSuscripcionDTO(Suscripcion suscripcion) {
        return new SuscripcionDTO(suscripcion.getHeladera().getNombreDelPunto(),
                                  suscripcion.getEvento(),
                                  suscripcion.getHeladera(),
                                  suscripcion.getHeladera().getid_heladera());
    }
}
