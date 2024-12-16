package DTOs;

import Modelo.Dominio.heladera.Heladera;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuscripcionDTO {
    private final String nombreDelPunto;
    private final String evento;
    private final Heladera heladera;
    private final int idHeladera;

    public SuscripcionDTO(String nombreDelPunto, String evento, Heladera heladera, int idHeladera) {
        this.nombreDelPunto = nombreDelPunto;
        this.evento = evento;
        this.heladera = heladera;
        this.idHeladera = idHeladera;
    }
}
