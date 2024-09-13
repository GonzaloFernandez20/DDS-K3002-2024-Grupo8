package Modelo.Dominio.persona;

import Modelo.Dominio.localizacion.Direccion;

public class PersonaJuridica extends Persona {
    private final String razonSocial;
    private final TipoOrganizacion tipoDeOrganizacion;
    private final String rubro;

    public PersonaJuridica(String razonSocial, TipoOrganizacion tipoDeOrganizacion, String rubro, Direccion direccion) {
        if(razonSocial ==null){throw new IllegalArgumentException("La razon social es obligatorio");}
        if(tipoDeOrganizacion ==null){throw new IllegalArgumentException("El tipo de organizacion es obligatorio");}
        if(rubro ==null){throw new IllegalArgumentException("El rubro es obligatorio");}
        this.razonSocial = razonSocial;
        this.tipoDeOrganizacion = tipoDeOrganizacion;
        this.rubro = rubro;
        this.direccion = direccion;
    }

}

