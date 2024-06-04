package medios_de_contacto;

public class MedioDeContacto {
    private String identificacion;

    public void notificar() {};

    public String getIdentificacion() { return identificacion; };

    public MedioDeContacto(String identificacion) { this.identificacion = identificacion; }
}

class WhatsApp extends MedioDeContacto{
    //@Override
    public WhatsApp(String identificacion) { super(identificacion); }

    public void notificar() {}
}

class Telefono extends MedioDeContacto{
    //@Override
    public Telefono(String identificacion) { super(identificacion); }

    public void notificar() {}
}