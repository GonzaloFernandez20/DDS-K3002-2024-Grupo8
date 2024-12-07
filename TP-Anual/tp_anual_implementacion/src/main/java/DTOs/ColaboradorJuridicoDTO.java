package DTOs;

import Modelo.Dominio.Persona.TipoOrganizacion;
import lombok.Getter;

@Getter
public class ColaboradorJuridicoDTO {

    private final String usuario;
    private final String constrasenia;

    private final String razonSocial;
    private final String tipoDeOrganizacion;
    private final String rubro;
    private final String calle;
    private final String altura;
    private final String email;
    private final String telefono;

    private final boolean tieneWp;
    private final boolean tieneTg;


    // Constructor
    public ColaboradorJuridicoDTO(String usuario, String contrasenia, String razonSocial,
                                  String tipoDeOrganizacion, String rubro, String calle,
                                  String altura, String telefono, String email, boolean tieneWp, boolean tieneTg) {
        this.usuario = usuario;
        this.constrasenia = contrasenia;
        this.razonSocial = razonSocial;
        this.tipoDeOrganizacion = tipoDeOrganizacion;
        this.rubro = rubro;
        this.calle = calle;
        this.altura = altura;
        this.telefono = telefono;
        this.email = email;
        this.tieneWp = tieneWp;
        this.tieneTg = tieneTg;
    }

}

