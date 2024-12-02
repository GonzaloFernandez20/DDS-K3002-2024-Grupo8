package Modelo.Dominio.Accesos_a_heladeras;

import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.Persona.Persona;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AccesoAHeladeras {
    @Id
    @Column(name = "codigo_tarjeta", nullable = false, unique = true)
    protected String codigoTarjeta;

    //Métodos ---------------------------------------------------------------------------------
    public abstract boolean estaAutorizadaLaApertura(Heladera heladera);


    //Getters y Setters ----------------------------------------------------------------------
    public abstract Persona getPersonaHumana();

    public String getCodigoTarjeta(){ return codigoTarjeta; }
    public void setCodigoTarjeta(String codigoTarjeta) {this.codigoTarjeta = codigoTarjeta;}
}
