package Modelo.Dominio.medios_de_contacto;

public class Telegram implements MedioDeContacto{

    private String nroDeTelefono;

    public Telegram(String nroDeTelefono) {
        this.nroDeTelefono = nroDeTelefono;
    }

    @Override
    public void notificar(String mensaje) {}
}
