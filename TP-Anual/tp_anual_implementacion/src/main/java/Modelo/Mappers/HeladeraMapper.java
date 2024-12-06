package Modelo.Mappers;

import DTOs.HeladeraDTO;
import DTOs.HeladeraSeleccionDTO;
import Modelo.Dominio.heladera.Heladera;

public class HeladeraMapper {
    public static HeladeraDTO convertirEnHeladeraDTO(Heladera heladera) {
        return new HeladeraDTO(heladera.getColaboradorACargo(),
                heladera.getCapacidadDeViandas(),
                heladera.getModelo().getNombreModelo(),
                heladera.getModelo().getTemperaturaMaxima(),
                heladera.getModelo().getTemperaturaMinima(),
                heladera.getUbicacion().getDireccion().getCalle(),
                heladera.getUbicacion().getDireccion().getAltura(),
                heladera.getUbicacion().getCiudad(),
                heladera.getUbicacion().getNombreDelPunto(),
                heladera.getPuestaEnFuncionamiento());
    }
}
