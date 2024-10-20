package Modelo.Dominio.incidentes;

import Modelo.Dominio.heladera.Heladera;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
@Entity
@Table(name = "Alerta")
public class Alerta extends Incidente{
    @Id
    @GeneratedValue
    private  Integer id_alerta;
    @Enumerated(EnumType.STRING)
    private TipoAlerta tipoAlerta;

    public Alerta(TipoAlerta tipoAlerta, Heladera heladera) {
        this.tipoAlerta = tipoAlerta;
        this.heladeraDondeOcurrio = heladera;
        this.momentoDelSuceso = LocalDateTime.now();
        this.visitas = new ArrayList<>();
        this.estado = EstadoDelIncidente.PENDIENTE;
    }

    @Override
    public String obtenerInformacion() {
        return "una Alerta de tipo: " + tipoAlerta.name();
    }
}
