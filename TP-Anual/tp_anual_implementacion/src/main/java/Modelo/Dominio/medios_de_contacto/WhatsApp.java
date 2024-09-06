package Modelo.Dominio.medios_de_contacto;

class WhatsApp implements MedioDeContacto{

    private String nroDeTelefono;

    public WhatsApp(String nroDeTelefono) {
        this.nroDeTelefono = nroDeTelefono;
    }

    @Override
    public void notificar(String mensaje) {}
}