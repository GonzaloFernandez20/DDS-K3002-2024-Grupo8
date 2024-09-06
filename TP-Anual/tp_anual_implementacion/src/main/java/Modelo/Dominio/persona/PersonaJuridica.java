package Modelo.Dominio.persona;

import Modelo.Dominio.localizacion.Direccion;

public class PersonaJuridica extends Persona {
    String razonSocial;
    TipoOrganizacion tipoDeOrganizacion;
    String rubro;

    public PersonaJuridica(String razonSocial, TipoOrganizacion tipoDeOrganizacion, String rubro, Direccion direccion) {
        super(direccion);
        this.razonSocial = razonSocial;
        this.tipoDeOrganizacion = tipoDeOrganizacion;
        this.rubro = rubro;
    }

    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }

    public void setTipoDeOrganizacion(TipoOrganizacion tipoDeOrganizacion) { this.tipoDeOrganizacion = tipoDeOrganizacion; }

    public void setRubro(String rubro) { this.rubro = rubro; }
}

