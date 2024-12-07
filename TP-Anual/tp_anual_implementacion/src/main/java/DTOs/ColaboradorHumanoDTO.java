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

    private final String email;
    private final String telefono;

    private final boolean tieneWp;
    private final boolean tieneTg;


    public ColaboradorHumanoDTO(String usuario, String contrasenia,
                                String nombre, String apellido,
                                LocalDate fechaDeNacimiento,
                                TipoDeDocumento tipo, String numero,
                                Sexo sexo, String calle, String altura, String email, String telefono, boolean tieneWp, boolean tieneTg) {
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
        this.email = email;
        this.telefono = telefono;
        this.tieneWp = tieneWp;
        this.tieneTg = tieneTg;
    }
}
