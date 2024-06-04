package tp_anual.tp_anual_implementacion;

interface MedioDeContacto {
    public void notificar();

    public String getIdentificacion();
}

class WhatsApp implements MedioDeContacto{
    private String identificacion;
    //@Override
    public void notificar() {}

    public String getIdentificacion() { return identificacion; }
}
class Telefono implements MedioDeContacto{
    private String identificacion;
    //@Override
    public void notificar() {}

    public String getIdentificacion() { return identificacion; }
}
class CorreoElectronico implements MedioDeContacto{
    private String identificacion;
    //@Override
    public void notificar() {}

    public CorreoElectronico(String identificacion) { this.identificacion = identificacion; }

    public String getIdentificacion() { return identificacion; }
}