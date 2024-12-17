package Modelo.Dominio.Accesos_a_heladeras;

import Modelo.Dominio.heladera.Heladera;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AccesoAHeladeras {
    @Id
    @Column(name = "codigo_tarjeta", nullable = false, unique = true)
    protected String codigoTarjeta;

    //Métodos ---------------------------------------------------------------------------------
    public abstract boolean estaAutorizadaLaApertura(Heladera heladera);
}
