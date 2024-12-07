package DTOs;


import java.time.LocalDate;

public class VinculacionPersonaVulnerableDTO {
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String tipoDeDocumento;
    private String numeroDocumento;
    private String sexo;
    private String direccionCalle;
    private String direccionAltura;
    private String estadoDeVivienda;
    private int cantidadMenores;
    private String codigoTarjeta;

    //Constructor ----------------------------------------------------------------------------------------------------
    public VinculacionPersonaVulnerableDTO() {
    }

    //Getters y Setters -----------------------------------------------------------------------------------------------
    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getApellido() {return apellido;}
    public void setApellido(String apellido) {this.apellido = apellido;}

    public LocalDate getFechaNacimiento() {return fechaNacimiento;}
    public void setFechaNacimiento(LocalDate fechaNacimiento) {this.fechaNacimiento = fechaNacimiento;}

    public String getTipoDeDocumento() {return tipoDeDocumento;}
    public void setTipoDeDocumento(String tipoDeDocumento) {this.tipoDeDocumento = tipoDeDocumento;}

    public String getNumeroDocumento() {return numeroDocumento;}
    public void setNumeroDocumento(String numeroDocumento) {this.numeroDocumento = numeroDocumento;}

    public String getSexo() {return sexo;}
    public void setSexo(String sexo) {this.sexo = sexo;}

    public String getDireccionCalle() {return direccionCalle;}
    public void setDireccionCalle(String direccionCalle) {this.direccionCalle = direccionCalle;}

    public String getDireccionAltura() {return direccionAltura;}
    public void setDireccionAltura(String direccionAltura) {this.direccionAltura = direccionAltura;}

    public String getEstadoDeVivienda() {return estadoDeVivienda;}
    public void setEstadoDeVivienda(String estadoDeVivienda) {this.estadoDeVivienda = estadoDeVivienda;}

    public int getCantidadMenores() {return cantidadMenores;}
    public void setCantidadMenores(int cantidadMenores) {this.cantidadMenores = cantidadMenores;}

    public String getCodigoTarjeta() {return codigoTarjeta;}
    public void setCodigoTarjeta(String codigoTarjeta) {this.codigoTarjeta = codigoTarjeta;}
}


