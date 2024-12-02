package Modelo.seguridad.SesionActiva;

import Modelo.Dominio.colaborador.Colaborador;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_usuario;
    @Column(name = "contrasenia")
    private String contrasenia;

    @JsonProperty("nombreDeUsuario")
    @Column(name = "usuario")
    private String usuario;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "colaborador", referencedColumnName = "id_colaborador")
    private Colaborador colaborador;


    public Usuario() {}

    public Usuario(String usuario, String contrasenia, Colaborador colaborador){
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.colaborador = colaborador;
    }

}
