package Modelo.Dominio.persona;

import Modelo.Dominio.localizacion.Direccion;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class PersonaJuridica extends Persona {
    @Column(name = "razon_social")
    private String razonSocial;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_de_organizacion")
    private TipoOrganizacion tipoDeOrganizacion;
    @Column(name = "rubro")
    private String rubro;

    public PersonaJuridica(String razonSocial, TipoOrganizacion tipoDeOrganizacion, String rubro, Direccion direccion) {
        this.razonSocial = razonSocial;
        this.tipoDeOrganizacion = tipoDeOrganizacion;
        this.rubro = rubro;
        this.direccion = direccion;
    }
    public PersonaJuridica(){}

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public TipoOrganizacion getTipoDeOrganizacion() {
        return tipoDeOrganizacion;
    }

    public void setTipoDeOrganizacion(TipoOrganizacion tipoDeOrganizacion) {
        this.tipoDeOrganizacion = tipoDeOrganizacion;
    }

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }
}

