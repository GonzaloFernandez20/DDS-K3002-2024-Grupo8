package DTOs;

import Modelo.Dominio.Persona.TipoOrganizacion;
import lombok.Getter;

import java.util.List;

@Getter
public class ColaboradorJuridicoDTO {

    private final String usuario;
    private final String constrasenia;

    private final String razonSocial;
    private final TipoOrganizacion tipoDeOrganizacion;
    private final String rubro;
    private final String calle;
    private final String altura;
    List<MedioDeContactoDTO> medioDeContactos;


    // Constructor
    public ColaboradorJuridicoDTO(String usuario, String contrasenia, String razonSocial,
                                  TipoOrganizacion tipoDeOrganizacion, String rubro, String calle,
                                  String altura, List<MedioDeContactoDTO> medioDeContactos) {
        this.usuario = usuario;
        this.constrasenia = contrasenia;
        this.razonSocial = razonSocial;
        this.tipoDeOrganizacion = tipoDeOrganizacion;
        this.rubro = rubro;
        this.calle = calle;
        this.altura = altura;
        this.medioDeContactos = medioDeContactos;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getConstrasenia() {
        return constrasenia;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public TipoOrganizacion getTipoDeOrganizacion() {
        return tipoDeOrganizacion;
    }

    public String getRubro() {
        return rubro;
    }

    public String getCalle() {
        return calle;
    }

    public String getAltura() {
        return altura;
    }

    public List<MedioDeContactoDTO> getMedioDeContactos() {
        return medioDeContactos;
    }

    public void setMedioDeContactos(List<MedioDeContactoDTO> medioDeContactos) {
        this.medioDeContactos = medioDeContactos;
    }
}

