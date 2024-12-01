package Modelo.Dominio.Accesos_a_heladeras;

import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.Persona.Persona;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AccesoAHeladeras {
    @Id
    @GeneratedValue
    private Integer id_acceso_a_heladeras;
    @Column(name = "codigo_tarjeta")
    protected String codigoTarjeta;

    //Métodos ---------------------------------------------------------------------------------
    public abstract boolean estaAutorizadaLaApertura(Heladera heladera);


    //Getters y Setters ----------------------------------------------------------------------
    public abstract Persona getPersonaHumana();

    public String getCodigoTarjeta(){ return codigoTarjeta; }
    public void setCodigoTarjeta(String codigoTarjeta) {this.codigoTarjeta = codigoTarjeta;}
}
