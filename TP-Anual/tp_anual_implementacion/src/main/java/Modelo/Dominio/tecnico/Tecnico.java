package Modelo.Dominio.tecnico;

import Modelo.Dominio.localizacion.AreaDeCobertura;
import Modelo.Dominio.medios_de_contacto.MedioDeContacto;
import Modelo.Dominio.persona.PersonaHumana;
import jakarta.persistence.*;

@Entity
@Table(name = "Tecnico")
public class Tecnico {
    @Id
    @GeneratedValue
    private Integer id_tecnico;
    @OneToOne
    @JoinColumn(name = "persona", referencedColumnName = "id_persona")
    private PersonaHumana persona;
    @Column(name = "CUIL")
    private String CUIL;
    @OneToOne
    @JoinColumn(name = "medio_de_contacto", referencedColumnName = "id_medio_de_contacto")
    private MedioDeContacto medioDeContacto;
    @Transient//No sé bien qué hacer con el área de cobertura
    private AreaDeCobertura areaCobertura;

    public Tecnico(PersonaHumana persona,
                   String CUIL,
                   MedioDeContacto medioDeContacto,
                   AreaDeCobertura areaCobertura) {
        this.persona = persona;
        this.CUIL = CUIL;
        this.medioDeContacto = medioDeContacto;
        this.areaCobertura = areaCobertura;
    }

    public void notificar(String mensaje) {
        medioDeContacto.notificar(mensaje);
    }
}
