package Modelo.Mappers;

import DTOs.HeladeraDTO;
import Modelo.Dominio.heladera.Heladera;

import java.util.List;
import java.util.stream.Collectors;

public class HeladeraMapper {
    public static HeladeraDTO convertirEnHeladeraDTO(Heladera heladera) {
        return new HeladeraDTO(heladera.getid_heladera(),
                heladera.getColaboradorACargo(),
                heladera.getCapacidadDeViandas(),
                heladera.getModelo().getNombreModelo(),
                heladera.getModelo().getTemperaturaMaxima(),
                heladera.getModelo().getTemperaturaMinima(),
                heladera.getUbicacion().getDireccion().getCalle(),
                heladera.getUbicacion().getDireccion().getAltura(),
                heladera.getUbicacion().getCiudad(),
                heladera.getUbicacion().getNombreDelPunto(),
                heladera.getLatitud(),
                heladera.getLongitud(),
                heladera.getPuestaEnFuncionamiento()
        );
    }

    public static List<HeladeraDTO> convertirListaDeHeladerasEnDTO(List<Heladera> heladeras) {
        return heladeras.stream().map(heladera -> convertirEnHeladeraDTO(heladera)).collect(Collectors.toList());
    }
}