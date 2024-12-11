package Modelo.Dominio.incidentes;

import Modelo.Dominio.heladera.Heladera;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
@Entity
@Table(name = "alerta")
public class Alerta extends Incidente{
    @Enumerated(EnumType.STRING)
    private TipoAlerta tipoAlerta;

    public Alerta(TipoAlerta tipoAlerta, Heladera heladera) {
        this.tipoAlerta = tipoAlerta;
        this.heladeraDondeOcurrio = heladera;
        this.momentoDelSuceso = LocalDateTime.now();
        this.visitas = new ArrayList<>();
        this.estado = EstadoDelIncidente.PENDIENTE;
    }

    public Alerta() {}

    @Override
    public String obtenerInformacion() {
        return "una Alerta de tipo: " + tipoAlerta.name();
    }

    public TipoAlerta getTipoAlerta() { return tipoAlerta; }
}
