package Modelo.Dominio.medios_de_contacto;

public class Mail implements MedioDeContacto{

    private String correo;

    public Mail(String correo) {
        this.correo = correo;
    }


    @Override
    public void notificar(String mensaje) {}
}