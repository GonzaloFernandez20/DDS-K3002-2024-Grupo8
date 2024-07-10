package tecnico;

import medios_de_contacto.MedioDeContacto;
import documentacion.Documento;
import persona.PersonaHumana;
import persona.PersonaJuridica;

import java.util.Date;

public class Tecnico {
    String cuil;
    MedioDeContacto medioDeContacto;
    String areaCobertura;
    PersonaHumana persona;
    public Tecnico(PersonaHumana persona, String cuil, String areaCobertura) {
        this.persona = persona;
        this.cuil = cuil;
        this.areaCobertura = areaCobertura;
        this.medioDeContacto = null;
    }

    public void setNombre(String nombre) {persona.setNombre(nombre);}

    public void setApellido(String apellido) { persona.setApellido(apellido); }

    public void setMedioDeContacto(MedioDeContacto medio) { this.medioDeContacto = medio; }

    public Documento getDocumento() {
        return persona.getDocumento();
    }
    public String getNombre() {
        return persona.getNombre();
    }

    public String getApellido() {
        return persona.getApellido();
    }

    public Date getFechaDeNacimiento() {
        return persona.getFechaDeNacimiento();
    }

}
