package Modelo.Dominio.Persona_vulnerable;

import Modelo.Dominio.Accesos_a_heladeras.Vinculacion;
import Modelo.Dominio.Persona.PersonaHumana;
import jakarta.persistence.*;

@Entity
@Table(name = "Persona_Situacion_Vulnerable")
public class PersonaSituacionVulnerable {
    @Id
    @GeneratedValue
    private Integer id_persona_en_situacion_vulnerable;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_vivienda")
    private EstadoDeVivienda estadoDeVivienda;
    @Column(name = "cantidad_de_menores")
    private int cantMenores;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "persona", referencedColumnName = "id_persona")
    private PersonaHumana persona;

    //Constructores ----------------------------------------------------------------------------------------------------
    public PersonaSituacionVulnerable(EstadoDeVivienda estadoDeVivienda, int cantMenores, PersonaHumana persona) {
        this.estadoDeVivienda = estadoDeVivienda;
        this.cantMenores = cantMenores;
        this.persona = persona;
    }

    public PersonaSituacionVulnerable() {

    }

    // Getters y Setters -----------------------------------------------------------------------------------------------
    public int getCantMenores() {return cantMenores;}
    public void setCantMenores(int cantMenores) {this.cantMenores = cantMenores;}

    public EstadoDeVivienda getEstadoDeVivienda() {return estadoDeVivienda;}
    public void setEstadoDeVivienda(EstadoDeVivienda estadoDeVivienda) {this.estadoDeVivienda = estadoDeVivienda;}

    public PersonaHumana getPersona() {return persona;}
    public void setPersona(PersonaHumana persona) {this.persona = persona;}
}