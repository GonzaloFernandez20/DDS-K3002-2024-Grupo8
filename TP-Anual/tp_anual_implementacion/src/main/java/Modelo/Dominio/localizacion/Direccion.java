package Modelo.Dominio.localizacion;

public class Direccion {
    String calle;
    String altura;
    String codPostal;
    String unidadFuncional;

    public Direccion(String calle, String altura, String codPostal, String unidadFuncional) {
        this.calle = calle;
        this.altura = altura;
        this.codPostal = codPostal;
        this.unidadFuncional = unidadFuncional;
    }

    // ----------> Getters y Setters
    public String getCalle() {
        return calle;
    }
    public void setCalle(String calle) {
        this.calle = calle;
    }
    public String getAltura() {
        return altura;
    }
    public void setAltura(String altura) {
        this.altura = altura;
    }
    public String getCodPostal() {
        return codPostal;
    }
    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }
    public String getUnidadFuncional() {
        return unidadFuncional;
    }
    public void setUnidadFuncional(String unidadFuncional) {
        this.unidadFuncional = unidadFuncional;
    }
}