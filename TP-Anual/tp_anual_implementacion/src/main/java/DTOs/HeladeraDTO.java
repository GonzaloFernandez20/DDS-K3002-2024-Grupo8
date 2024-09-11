package DTOs;

import Modelo.Dominio.colaborador.Colaborador;

import java.time.LocalDate;

public class HeladeraDTO {
    private Colaborador colaboradorACargo;
    private int capacidadViandas;
    private float tempMAXmodelo;
    private float tempMINmodelo;
    private String calle;
    private String altura;
    private String codPostal;
    private String ciudad;
    private String nombreDelPunto;
    private LocalDate puestaEnFuncionamiento;

    public HeladeraDTO(Colaborador colaboradorACargo, int capacidadViandas, float tempMAXmodelo, float tempMINmodelo, String calle, String altura, String codPostal, String ciudad, String nombreDelPunto, LocalDate puestaEnFuncionamiento) {
        this.colaboradorACargo = colaboradorACargo;
        this.capacidadViandas = capacidadViandas;
        this.tempMAXmodelo = tempMAXmodelo;
        this.tempMINmodelo = tempMINmodelo;
        this.calle = calle;
        this.altura = altura;
        this.codPostal = codPostal;
        this.ciudad = ciudad;
        this.nombreDelPunto = nombreDelPunto;
        this.puestaEnFuncionamiento = puestaEnFuncionamiento;
    }

    public Colaborador getColaboradorACargo() {
        return colaboradorACargo;
    }

    public void setColaboradorACargo(Colaborador colaboradorACargo) {
        this.colaboradorACargo = colaboradorACargo;
    }

    public int getCapacidadViandas() {
        return capacidadViandas;
    }

    public void setCapacidadViandas(int capacidadViandas) {
        this.capacidadViandas = capacidadViandas;
    }

    public float getTempMAXmodelo() {
        return tempMAXmodelo;
    }

    public void setTempMAXmodelo(float tempMAXmodelo) {
        this.tempMAXmodelo = tempMAXmodelo;
    }

    public float getTempMINmodelo() {
        return tempMINmodelo;
    }

    public void setTempMINmodelo(float tempMINmodelo) {
        this.tempMINmodelo = tempMINmodelo;
    }

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

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getNombreDelPunto() {
        return nombreDelPunto;
    }

    public void setNombreDelPunto(String nombreDelPunto) {
        this.nombreDelPunto = nombreDelPunto;
    }

    public LocalDate getPuestaEnFuncionamiento() { return puestaEnFuncionamiento; }

    public void setPuestaEnFuncionamiento(LocalDate puestaEnFuncionamiento) { this.puestaEnFuncionamiento = puestaEnFuncionamiento; }

}
