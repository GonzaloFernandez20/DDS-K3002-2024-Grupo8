package Modelo.Dominio.persona_vulnerable;

import Modelo.Dominio.Accesos_a_heladeras.Vinculacion;
import Modelo.Dominio.persona.PersonaHumana;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.localizacion.Direccion;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "PersonaEnSituacionVulnerable")
public class PersonaSituacionVulnerable {
    @Id
    @GeneratedValue
    private Integer id_persona_en_situacion_vulnerble;
    @Enumerated(EnumType.STRING)
    private EstadoDeVivienda estadoDeVivienda;
    @Column(name = "cantidad_de_menores")
    private int cantMenores;
    @OneToOne
    @JoinColumn(name = "id_vinculacion", referencedColumnName = "id_vinculacion")
    private Vinculacion vinculacion;
    @OneToOne
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona")
    private final PersonaHumana persona;

    public PersonaSituacionVulnerable(/*String nombre, String apellido,
                                      LocalDate fechaDeNacimiento,
                                      Documento documento,
                                      Direccion direccion,*/
                                      EstadoDeVivienda estadoDeVivienda,
                                      int cantMenores,
                                      Vinculacion vinculacion,
                                      PersonaHumana persona) {
       // super(nombre, apellido, fechaDeNacimiento, documento, direccion);
        this.estadoDeVivienda = estadoDeVivienda;
        this.cantMenores = cantMenores;
        this.vinculacion = vinculacion;
        this.persona = persona;
    }
    /*
    Aca tenemos una redundancia: Si PersonaSituacionVulnerable hereda de PersonaHumana y mediante constructor
    rellena los datos, entonces es una sola instancia y el atributo PersonaHumana no tiene sentido.
    Si decidimos no usar herencia y mantener la composicion, entonces si tiene sentido recibir una
    PersonaHumana pero no necesitariamos los datos inherentes de la persona en el constructor.
    De momento me quedo con la composicion que es como esta en el DC.
    */

    // ---- Getters y Setters
    public void setCantMenores(int cantMenores) {
        this.cantMenores = cantMenores;
    }
    public void setVinculacion(Vinculacion unaVinculacion){
        this.vinculacion = unaVinculacion;
    }
    public EstadoDeVivienda getEstadoDeVivienda() {
        return estadoDeVivienda;
    }
    public int getCantMenores() {
        return cantMenores;
    }
    public Vinculacion getVinculacion() {
        return vinculacion;
    }
}