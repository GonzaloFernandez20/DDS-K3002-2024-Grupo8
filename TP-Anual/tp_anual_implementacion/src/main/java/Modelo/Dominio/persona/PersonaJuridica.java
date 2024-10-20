package Modelo.Dominio.persona;

import Modelo.Dominio.localizacion.Direccion;
import jakarta.persistence.*;

@Entity
@Table(name = "PersonaJuridica")
public class PersonaJuridica extends Persona {
    @Column(name = "razon_social")
    private String razonSocial;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_de_organizacion")
    private TipoOrganizacion tipoDeOrganizacion;
    @Column(name = "rubro")
    private String rubro;

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

