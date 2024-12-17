package Modelo.Dominio.Persona_vulnerable;

import Modelo.Dominio.Persona.PersonaHumana;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Persona_Situacion_Vulnerable")
public class PersonaSituacionVulnerable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_persona_en_situacion_vulnerable;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_vivienda")
    private EstadoDeVivienda estadoDeVivienda;
    @Column(name = "cantidad_de_menores")
    private int cantMenores;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "persona" ,referencedColumnName = "id_persona")
    private PersonaHumana persona;

    //Constructores ----------------------------------------------------------------------------------------------------
    public PersonaSituacionVulnerable(EstadoDeVivienda estadoDeVivienda, int cantMenores, PersonaHumana persona) {
        this.estadoDeVivienda = estadoDeVivienda;
        this.cantMenores = cantMenores;
        this.persona = persona;
    }

    public PersonaSituacionVulnerable() {

    }
}