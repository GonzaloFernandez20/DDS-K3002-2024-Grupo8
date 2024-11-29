package Modelo.Dominio;

import Modelo.Dominio.colaborador.Colaborador;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_usuario;
    @Column(name = "contrasenia")
    String contrasenia;

    @JsonProperty("nombreDeUsuario")
    @Column(name = "usuario")
    String usuario;

    @Column(name = "id_colaborador")
    Integer id_colaborador;


    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public Usuario() {}

    public Integer getId_colaborador() { return id_colaborador; }

    public void setId_colaborador(Integer id_colaborador) { this.id_colaborador = id_colaborador; }
}
