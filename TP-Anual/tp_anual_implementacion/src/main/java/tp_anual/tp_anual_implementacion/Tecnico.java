package tp_anual.tp_anual_implementacion;

public class Tecnico {
    String nombre;
    String apellido;
    Documento documento;
    String cuil;
    MedioDeContacto medioDeContacto;
    String areaCobertura;

    public Tecnico(String nombre, String apellido, Documento documento, String cuil, String areaCobertura) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.cuil = cuil;
        this.areaCobertura = areaCobertura;
        this.medioDeContacto = null;
    }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public void setApellido(String apellido) { this.apellido = apellido; }

    public void setMedioDeContacto(MedioDeContacto medio) { this.medioDeContacto = medio; }
}
