package Modelo.Dominio.tecnico;

import Modelo.Dominio.incidentes.EstadoDelIncidente;
import Modelo.Dominio.incidentes.Incidente;
import Modelo.Dominio.localizacion.AreaDeCobertura;
import Modelo.Dominio.localizacion.Ubicacion;
import Modelo.Dominio.medios_de_contacto.MedioDeContacto;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.persona.PersonaHumana;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "Tecnico")
public class Tecnico {
    @Id
    @GeneratedValue
    private Integer id_tecnico;
    @OneToOne
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona")
    private PersonaHumana persona;
    @Column(name = "CUIL")
    private String CUIL;
    @OneToOne
    @JoinColumn(name = "id_medio_de_contacto", referencedColumnName = "id_medio_de_contacto")
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
