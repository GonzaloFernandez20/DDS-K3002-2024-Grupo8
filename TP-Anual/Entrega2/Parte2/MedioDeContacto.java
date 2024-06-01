package TPAnual;

interface MedioDeContacto {
    public void notificar();
}

class WhatsApp implements MedioDeContacto{
    //@Override
    public void notificar() {}
}
class Telefono implements MedioDeContacto{
    //@Override
    public void notificar() {}
}
class CorreoElectronico implements MedioDeContacto{
    //@Override
    public void notificar() {}
}