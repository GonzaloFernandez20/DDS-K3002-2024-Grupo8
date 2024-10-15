package DTOs;

import Modelo.Dominio.medios_de_contacto.MedioDeContacto;
import Modelo.Dominio.persona.TipoOrganizacion;

import java.util.List;

public class ColaboradorJuridicoDTO {
    private String usuario;
    private String constrasenia;
    private String razonSocial;
    private TipoOrganizacion tipoDeOrganizacion;
    private String rubro;
    private String calle;
    private String altura;
    private String codPostal;
    private List<MedioDeContacto> mediosDeContacto;

    // Constructor
    public ColaboradorJuridicoDTO(String usuario, String contrasenia, String razonSocial,
                                  TipoOrganizacion tipoDeOrganizacion, String rubro, String calle,
                                  String altura, String codPostal, List<MedioDeContacto> mediosDeContacto) {
        this.usuario = usuario;
        this.constrasenia = contrasenia;
        this.razonSocial = razonSocial;
        this.tipoDeOrganizacion = tipoDeOrganizacion;
        this.rubro = rubro;
        this.calle = calle;
        this.altura = altura;
        this.codPostal = codPostal;
        this.mediosDeContacto = mediosDeContacto;
    }

    // Getters y Setters
    public String getRazonSocial() { return razonSocial; }
    public TipoOrganizacion getTipoDeOrganizacion() { return tipoDeOrganizacion; }
    public String getRubro() { return rubro; }
    public List<MedioDeContacto> getMediosDeContacto() { return mediosDeContacto; }
    public String getCalle() { return calle; }
    public String getAltura() { return altura; }
    public String getCodPostal() { return codPostal; }
}

