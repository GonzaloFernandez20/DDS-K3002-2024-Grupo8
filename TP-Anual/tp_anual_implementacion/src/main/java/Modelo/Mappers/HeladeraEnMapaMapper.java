package Modelo.Mappers;

import DTOs.HeladeraEnMapaDTO;
import Modelo.Dominio.heladera.Heladera;

public class HeladeraEnMapaMapper {
    public static HeladeraEnMapaDTO convertirEnHeladeraEnMapaDTO(Heladera heladera) {
        System.out.println(heladera.getUbicacion().getNombreCompletoDeUbicacion() + " " + heladera.getEstado());
        return new HeladeraEnMapaDTO(
                heladera.getUbicacion().getDireccion().getCalle(),
                heladera.getUbicacion().getDireccion().getAltura(),
                heladera.getUbicacion().getCiudad(),
                heladera.getUbicacion().getNombreDelPunto(),
                heladera.getLatitud(),
                heladera.getLongitud(),
                heladera.getCantViandasEnStock(),
                heladera.getCapacidadDeViandas(),
                heladera.getEstado()
        );
    }
}