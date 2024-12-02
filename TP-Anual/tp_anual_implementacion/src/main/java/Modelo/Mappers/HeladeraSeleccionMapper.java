package Modelo.Mappers;

import DTOs.HeladeraSeleccionDTO;
import Modelo.Dominio.heladera.Heladera;

public class HeladeraSeleccionMapper {

    public static HeladeraSeleccionDTO convertirEnHeladeraSeleccionDTO(Heladera heladera) {
        System.out.println(heladera.getUbicacion().getNombreCompletoDeUbicacion());
        return new HeladeraSeleccionDTO(heladera.getIdHeladera(),
                                        heladera.getUbicacion().getNombreDelPunto(),
                                        heladera.getUbicacion().getDireccion().getCalle(),
                                        heladera.getUbicacion().getDireccion().getAltura(),
                                        heladera.getUbicacion().getCiudad(),
                                        heladera.getViandasEnStock().size(),
                                        heladera.capacidadRestante(),
                                        heladera.getEstado());
    }
}
