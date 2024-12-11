package Modelo.Dominio.heladera;

import Modelo.Dominio.incidentes.GestorDeIncidentes;
import Modelo.Dominio.incidentes.TipoAlerta;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sensores_de_movimiento")
public class SensoreoDeMovimiento {

    @Id
    @GeneratedValue
    private Long id_Sensor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "heladera_asociada", referencedColumnName = "id_heladera")
    private Heladera heladera;

    public SensoreoDeMovimiento(Heladera heladera) {
        this.heladera = heladera;
    }

    public SensoreoDeMovimiento() {

    }

    public void enviarAlerta() {
        GestorDeIncidentes.reportarAlerta(heladera, TipoAlerta.FRAUDE);
    }
}
