package Modelo.Dominio.medios_de_contacto;

public class WhatsApp implements MedioDeContacto{

    private String nroDeTelefono;

    public WhatsApp(String nroDeTelefono) {
        this.nroDeTelefono = nroDeTelefono;
    }

    @Override
    public void notificar(String mensaje) {}
}