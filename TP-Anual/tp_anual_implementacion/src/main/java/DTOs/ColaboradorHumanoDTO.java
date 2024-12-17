package DTOs;

import Modelo.Dominio.documentacion.Sexo;
import Modelo.Dominio.documentacion.TipoDeDocumento;
import Modelo.Dominio.medios_de_contacto.MedioDeContacto;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class ColaboradorHumanoDTO {

    private final String usuario;
    private final String constrasenia;

    private final String nombre;
    private final String apellido;
    private final LocalDate fechaDeNacimiento;
    private final TipoDeDocumento tipo;
    private final String numero;
    private final Sexo sexo;
    private final String calle;
    private final String altura;

    //private final String email;
    //private final String telefono;

    //private final boolean tieneWp;
    //private final boolean tieneTg;
    List<MedioDeContactoDTO> medioDeContactos;

    private final boolean tieneTarjeta;


    public ColaboradorHumanoDTO(String usuario, String contrasenia,
                                String nombre, String apellido,
                                LocalDate fechaDeNacimiento,
                                TipoDeDocumento tipo, String numero,
                                Sexo sexo, String calle, String altura,
                                List<MedioDeContactoDTO> medioDeContactos,
                                //String email, String telefono, boolean tieneWp, boolean tieneTg,
                                boolean tieneTarjeta) {
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
        this.medioDeContactos = medioDeContactos;
        //this.email = email;
        //this.telefono = telefono;
        //this.tieneWp = tieneWp;
        //this.tieneTg = tieneTg;
        this.tieneTarjeta = tieneTarjeta;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getConstrasenia() {
        return constrasenia;
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

    public List<MedioDeContactoDTO> getMedioDeContactos() {
        return medioDeContactos;
    }

    public void setMedioDeContactos(List<MedioDeContactoDTO> medioDeContactos) {
        this.medioDeContactos = medioDeContactos;
    }

    public boolean isTieneTarjeta() {
        return tieneTarjeta;
    }
}
