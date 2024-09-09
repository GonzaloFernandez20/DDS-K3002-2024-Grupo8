package Modelo.Dominio.tecnico;

import Modelo.Dominio.incidentes.EstadoDelIncidente;
import Modelo.Dominio.incidentes.Incidente;
import Modelo.Dominio.localizacion.AreaDeCobertura;
import localizacion.Area;
import Modelo.Dominio.localizacion.Ubicacion;
import Modelo.Dominio.medios_de_contacto.MedioDeContacto;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.persona.PersonaHumana;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Tecnico {
    private PersonaHumana persona;
    private String CUIL;
    private MedioDeContacto medioDeContacto;
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
