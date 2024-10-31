package DTOs;

import Modelo.Dominio.documentacion.Sexo;
import Modelo.Dominio.documentacion.TipoDeDocumento;
import Modelo.Dominio.medios_de_contacto.MedioDeContacto;

import java.time.LocalDate;
import java.util.List;

public class ColaboradorHumanoDTO {
    private String usuario;
    private String constrasenia;
    private String nombre;
    private String apellido;
    private LocalDate fechaDeNacimiento;
    private TipoDeDocumento tipo;
    private String numero;
    private Sexo sexo;
    private String calle;
    private String altura;
    private String codPostal;
    private List<MedioDeContacto> mediosDeContacto;


    public ColaboradorHumanoDTO(String usuario, String contrasenia,
                                String nombre, String apellido,
                                LocalDate fechaDeNacimiento,
                                TipoDeDocumento tipo, String numero,
                                Sexo sexo, String calle, String altura,
                                String codPostal,
                                List<MedioDeContacto> mediosDeContacto) {
        this.usuario = usuario;
        this.constrasenia = contrasenia;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.tipo = tipo;
        this.numero = numero;
        this.sexo = sexo;
        this.calle = calle;
        this.altura = altura;
        this.codPostal = codPostal;
        this.mediosDeContacto = mediosDeContacto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public LocalDate getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public TipoDeDocumento getTipo() {
        return tipo;
    }

    public String getNumero() {
        return numero;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public String getCalle() {
        return calle;
    }

    public String getAltura() {
        return altura;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public List<MedioDeContacto> getMediosDeContacto() {
        return mediosDeContacto;
    }
}
